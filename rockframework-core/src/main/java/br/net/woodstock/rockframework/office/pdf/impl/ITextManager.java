/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package br.net.woodstock.rockframework.office.pdf.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.bouncycastle.jce.X509Principal;

import br.net.woodstock.rockframework.io.InputOutputStream;
import br.net.woodstock.rockframework.office.pdf.PDFException;
import br.net.woodstock.rockframework.office.pdf.PDFSignature;
import br.net.woodstock.rockframework.office.pdf.PDFSignatureRequestData;
import br.net.woodstock.rockframework.office.pdf.PDFSigner;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.digest.Digester;
import br.net.woodstock.rockframework.security.digest.impl.BasicDigester;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.OcspClient;
import com.itextpdf.text.pdf.OcspClientBouncyCastle;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPKCS7;
import com.itextpdf.text.pdf.PdfPKCS7.X509Name;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignature;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.TSAClient;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class ITextManager extends AbstractITextManager {

	@Override
	public InputStream cut(final InputStream source, final int start, final int end) {
		try {
			Assert.notNull(source, "source");
			Assert.greaterOrEqual(start, 1, "start");

			PdfReader reader = new PdfReader(source);
			Document document = new Document(reader.getPageSizeWithRotation(1));
			InputOutputStream outputStream = new InputOutputStream();
			PdfCopy writer = new PdfCopy(document, outputStream);
			int pageCount = reader.getNumberOfPages();

			Assert.lessOrEqual(start, pageCount, "start");

			int endPage = end;
			if (endPage > pageCount) {
				endPage = pageCount;
			}

			document.open();

			for (int i = start; i <= endPage; i++) {
				PdfImportedPage page = writer.getImportedPage(reader, i);
				writer.addPage(page);
			}

			document.close();
			writer.close();
			reader.close();

			return outputStream.getInputStream();
		} catch (IOException e) {
			throw new PDFException(e);
		} catch (DocumentException e) {
			throw new PDFException(e);
		}
	}

	@Override
	public InputStream merge(final InputStream[] sources) {
		try {
			Assert.notNull(sources, "sources");
			Assert.notEmpty(sources, "sources");

			Document document = new Document();
			InputOutputStream outputStream = new InputOutputStream();
			PdfCopy writer = new PdfCopy(document, outputStream);

			document.open();

			for (InputStream source : sources) {
				PdfReader reader = new PdfReader(source);
				int pageCount = reader.getNumberOfPages();
				for (int i = 1; i <= pageCount; i++) {
					PdfImportedPage page = writer.getImportedPage(reader, i);
					writer.addPage(page);
				}
				reader.close();
			}

			document.close();
			writer.close();

			return outputStream.getInputStream();
		} catch (IOException e) {
			throw new PDFException(e);
		} catch (DocumentException e) {
			throw new PDFException(e);
		}
	}

	@Override
	public InputStream[] split(final InputStream source, final int size) {
		try {
			Assert.notNull(source, "source");
			Assert.greaterThan(size, 0, "size");

			PdfReader reader = new PdfReader(source);
			int pageCount = reader.getNumberOfPages();
			List<InputStream> list = new LinkedList<InputStream>();

			Document document = null;
			InputOutputStream outputStream = null;
			PdfCopy writer = null;
			for (int i = 1; i <= pageCount; i++) {
				if ((document == null) || ((i % size) == 0)) {
					if (document != null) {
						document.close();
						writer.close();
						list.add(outputStream.getInputStream());
					}
					document = new Document(reader.getPageSizeWithRotation(1));
					outputStream = new InputOutputStream();
					writer = new PdfCopy(document, outputStream);
				}
				PdfImportedPage page = writer.getImportedPage(reader, i);
				writer.addPage(page);
			}

			if (document != null) {
				document.close();
				writer.close();
				list.add(outputStream.getInputStream());
			}

			reader.close();

			return list.toArray(new InputStream[list.size()]);
		} catch (IOException e) {
			throw new PDFException(e);
		} catch (DocumentException e) {
			throw new PDFException(e);
		}
	}

	@Override
	public String getText(final InputStream source) {
		try {
			Assert.notNull(source, "source");

			PdfReader reader = new PdfReader(source);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
			int pageCount = reader.getNumberOfPages();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			PrintWriter writer = new PrintWriter(outputStream);

			for (int i = 1; i <= pageCount; i++) {
				TextExtractionStrategy result = parser.processContent(i, strategy);
				String pageText = result.getResultantText();
				writer.println(pageText);
			}

			reader.close();
			writer.close();

			String text = new String(outputStream.toByteArray());
			return text;
		} catch (IOException e) {
			throw new PDFException(e);
		}
	}

	@Override
	public InputStream sign(final InputStream source, final PDFSignatureRequestData data) {
		Assert.notNull(source, "source");
		Assert.notNull(data, "data");
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			X509Certificate certificate = (X509Certificate) data.getCertificate();
			X509Certificate rootCertificate = (X509Certificate) data.getRootCertificate();
			Certificate[] chain = data.getCertificateChain();
			PrivateKey privateKey = data.getPrivateKey();
			DigestType digestType = this.getDigestTypeFromSignature(certificate.getSigAlgName());
			Calendar calendar = Calendar.getInstance();

			PdfReader reader = new PdfReader(source);
			PdfStamper stamper = PdfStamper.createSignature(reader, outputStream, AbstractITextManager.PDF_SIGNATURE_VERSION, null, true);

			PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
			appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
			appearance.setCrypto(privateKey, chain, null, PdfSignatureAppearance.SELF_SIGNED);
			appearance.setContact(data.getContactInfo());
			appearance.setLocation(data.getLocation());
			appearance.setReason(data.getReason());
			appearance.setSignDate(calendar);
			// appearance.preClose();

			PdfSignature signature = new PdfSignature(PdfName.ADOBE_PPKLITE, PdfName.ADBE_PKCS7_DETACHED);
			signature.setReason(appearance.getReason());
			signature.setLocation(appearance.getLocation());
			signature.setContact(appearance.getContact());
			signature.setDate(new PdfDate(appearance.getSignDate()));

			appearance.setCryptoDictionary(signature);

			int contentSize = 0x2502;
			HashMap<PdfName, Integer> exc = new HashMap<PdfName, Integer>();
			exc.put(PdfName.CONTENTS, new Integer(contentSize));
			appearance.preClose(exc);

			Digester digester = new BasicDigester(digestType);
			byte[] rangeStream = IOUtils.toByteArray(appearance.getRangeStream());
			byte[] hash = digester.digest(rangeStream);

			TSAClient tsc = (TSAClient) data.getTsaClient();

			byte[] oscp = null;
			if (rootCertificate != null) {
				String oscpUrl = PdfPKCS7.getOCSPURL(certificate);
				if (ConditionUtils.isNotEmpty(oscpUrl)) {
					OcspClient ocspClient = new OcspClientBouncyCastle(certificate, rootCertificate, oscpUrl);
					oscp = ocspClient.getEncoded();
				}
			}

			PdfPKCS7 pkcs7 = new PdfPKCS7(privateKey, chain, null, digestType.getAlgorithm(), null, false);
			byte[] authenticatedAttributes = pkcs7.getAuthenticatedAttributeBytes(hash, calendar, oscp);
			pkcs7.update(authenticatedAttributes, 0, authenticatedAttributes.length);
			pkcs7.setLocation(data.getLocation());
			pkcs7.setReason(data.getReason());

			if (tsc == null) {
				pkcs7.setSignDate(calendar);
			}

			byte[] encodedPkcs7 = pkcs7.getEncodedPKCS7(hash, calendar, tsc, oscp);

			byte[] output = new byte[(contentSize - 2) / 2];

			System.arraycopy(encodedPkcs7, 0, output, 0, encodedPkcs7.length);

			PdfDictionary newDictionary = new PdfDictionary();
			PdfString content = new PdfString(output);
			content.setHexWriting(true);
			newDictionary.put(PdfName.CONTENTS, content);
			appearance.close(newDictionary);

			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (IOException e) {
			throw new PDFException(e);
		} catch (GeneralSecurityException e) {
			throw new PDFException(e);
		} catch (DocumentException e) {
			throw new PDFException(e);
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public Collection<PDFSignature> getSignatures(final InputStream source) {
		try {
			PdfReader reader = new PdfReader(source);
			AcroFields fields = reader.getAcroFields();
			Collection<PDFSignature> pdfSignatures = new ArrayList<PDFSignature>();
			if (fields != null) {
				List<String> signatures = fields.getSignatureNames();
				if ((signatures != null) && (!signatures.isEmpty())) {
					for (String signature : signatures) {
						PdfPKCS7 pk = fields.verifySignature(signature);

						PDFSignature pdfSignature = new PDFSignature(pk.getLocation(), pk.getReason(), pk.getSignDate().getTime());
						Certificate[] certificates = pk.getCertificates();

						for (Certificate certificate : certificates) {
							X509Certificate x509Certificate = (X509Certificate) certificate;
							Principal principal = x509Certificate.getSubjectDN();
							X509Principal x509Principal = (X509Principal) principal;

							String subject = this.toString(x509Principal.getValues(X509Name.CN));
							String issuer = this.toString(x509Principal.getValues(X509Name.OU));

							PDFSigner pdfSigner = new PDFSigner(subject, issuer);
							pdfSignature.getSigners().add(pdfSigner);
						}
						pdfSignatures.add(pdfSignature);
					}
				}
			}

			return pdfSignatures;
		} catch (IOException e) {
			throw new PDFException(e);
		}
	}
}
