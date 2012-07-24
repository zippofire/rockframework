package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import junit.framework.TestCase;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;

import br.net.woodstock.rockframework.security.cert.CertificateBuilderRequest;
import br.net.woodstock.rockframework.security.cert.KeyUsageType;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.cert.impl.BouncyCastleCertificateBuilder;
import br.net.woodstock.rockframework.security.sign.PKCS7SignatureParameters;
import br.net.woodstock.rockframework.security.sign.PKCS7Signer;
import br.net.woodstock.rockframework.security.sign.Signature;
import br.net.woodstock.rockframework.security.sign.impl.BouncyCastlePKCS7Signer;
import br.net.woodstock.rockframework.security.timestamp.impl.URLTimeStampClient;
import br.net.woodstock.rockframework.utils.Base64Utils;
import br.net.woodstock.rockframework.utils.HexUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

public class TimeStampInfoTest extends TestCase {

	public void xtest1() throws Exception {
		byte[] pdf = "Lourival Sabino da Silva Júnior".getBytes();

		CertificateBuilderRequest request = new CertificateBuilderRequest("Lourival Sabino");
		request.withEmail("junior@woodstock.net.br");
		request.withIssuer("Woodstock Tecnologia");
		request.withKeyUsage(KeyUsageType.DIGITAL_SIGNATURE, KeyUsageType.NON_REPUDIATION, KeyUsageType.KEY_AGREEMENT);

		PrivateKeyHolder holder1 = BouncyCastleCertificateBuilder.getInstance().build(request);

		URLTimeStampClient timeStampClient = new URLTimeStampClient("http://tsa.safelayer.com:8093");
		timeStampClient.setDebug(true);
		// TimeStampClient timeStampClient = new STFTimeStampClient("201.49.148.134", 318);
		PKCS7SignatureParameters signerInfo = new PKCS7SignatureParameters(holder1);
		signerInfo.setTimeStampClient(timeStampClient);

		PKCS7Signer signer = new BouncyCastlePKCS7Signer(signerInfo);

		byte[] signed = signer.sign(pdf);

		FileOutputStream fileOutputStream = new FileOutputStream("/tmp/sign.pdf.p7s");
		fileOutputStream.write(signed);
		fileOutputStream.close();
	}

	public void xtest2() throws Exception {
		// FileInputStream fileOutputStream = new FileInputStream("/tmp/sign.pdf.p7s");
		FileInputStream fileOutputStream = new FileInputStream("/tmp/carimboDeTempo2.p7s");
		byte[] data = IOUtils.toByteArray(fileOutputStream);
		CMSSignedData signedData = new CMSSignedData(data);
		System.out.println(signedData.getSignedContentTypeOID());
		byte[] content = (byte[]) signedData.getSignedContent().getContent();
		System.out.println(data.length);
		System.out.println(new String(content));
		SignerInformationStore signerInformationStore = signedData.getSignerInfos();
		SignerInformation signerInformation = (SignerInformation) signerInformationStore.getSigners().iterator().next();
		System.out.println("Signed");
		Hashtable signedTable = signerInformation.getSignedAttributes().toHashtable();
		for (Object o : signedTable.keySet()) {
			ASN1ObjectIdentifier identifier = (ASN1ObjectIdentifier) o;
			Attribute attribute = signerInformation.getSignedAttributes().get(identifier);
			System.out.println(identifier + " => " + attribute.getAttrType() + " => " + attribute.getAttrValues().getClass());
		}
		System.out.println("Unsigned");
		if (signerInformation.getUnsignedAttributes() != null) {
			Hashtable unsignedTable = signerInformation.getUnsignedAttributes().toHashtable();
			for (Object o : unsignedTable.keySet()) {
				ASN1ObjectIdentifier identifier = (ASN1ObjectIdentifier) o;
				Attribute attribute = (Attribute) signedTable.get(o);
				System.out.println(identifier + " => " + attribute);
				System.out.println(identifier + " => " + signerInformation.getUnsignedAttributes().get(identifier).getAttrType());
				System.out.println(identifier + " => " + signerInformation.getUnsignedAttributes().get(identifier).getDERObject().getClass());
			}
			if (unsignedTable.containsKey(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken)) {
				DERSequence derSequence = (DERSequence) signerInformation.getUnsignedAttributes().get(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken).getDERObject();
				System.out.println("TOKEN: " + derSequence.getEncoded().length);
				for (int i = 0; i < derSequence.size(); i++) {
					DERObject derObject = (DERObject) derSequence.getObjectAt(i);
					System.out.println(new String(Base64Utils.toBase64(derObject.getEncoded())));
				}

			}
		}

		DERSequence signTimeDerSet = (DERSequence) signerInformation.getSignedAttributes().get(PKCSObjectIdentifiers.pkcs_9_at_signingTime).getDERObject();
		DERObject derObject = (DERObject) signTimeDerSet.getObjectAt(1);
		DERSet asn1Object = (DERSet) derObject.toASN1Object();

		System.out.println("Time: " + signTimeDerSet);
		System.out.println("Time: " + asn1Object);
		System.out.println("Time: " + asn1Object.getObjectAt(0));
		System.out.println("Time: " + asn1Object.getObjectAt(0).getClass());

		// System.out.println("Token Original");
		// TimeStampResponse timeStampResponse = new TimeStampResponse(IOUtils.toByteArray(new FileInputStream("/tmp/tsa-client-1332419788210WnRO.tsr")));
		// TimeStampToken timeStampToken = timeStampResponse.getTimeStampToken();
		// System.out.println(new String(Base64Utils.toBase64(timeStampToken.getEncoded())));

		// signedData.get
	}

	public void xtest3() throws Exception {
		TimeStampResponse timeStampResponse = new TimeStampResponse(IOUtils.toByteArray(new FileInputStream("/tmp/tsa-client-1332419788210WnRO.tsr")));
		TimeStampToken timeStampToken1 = timeStampResponse.getTimeStampToken();
		TimeStampToken timeStampToken2 = new TimeStampToken(new CMSSignedData(timeStampToken1.getEncoded()));
		System.out.println(timeStampToken1.getEncoded().length);
		// 1838
		System.out.println("====================================== Esperado");
		System.out.println(new String(Base64Utils.toBase64(timeStampToken1.getEncoded())));
		System.out.println(new String(Base64Utils.toBase64(timeStampToken2.getEncoded())));
	}

	public void xtest4() throws Exception {
		FileInputStream fileOutputStream = new FileInputStream("/tmp/sign.pdf.p7s");
		byte[] data = IOUtils.toByteArray(fileOutputStream);
		CMSSignedData signedData = new CMSSignedData(data);
		SignerInformationStore signerInformationStore = signedData.getSignerInfos();
		SignerInformation signerInformation = (SignerInformation) signerInformationStore.getSigners().iterator().next();
		Hashtable unsignedTable = signerInformation.getUnsignedAttributes().toHashtable();
		if (unsignedTable.containsKey(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken)) {
			DERSequence derSequence = (DERSequence) signerInformation.getUnsignedAttributes().get(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken).getDERObject();
			System.out.println(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
			for (int i = 0; i < derSequence.size(); i++) {
				DERObject derObject = (DERObject) derSequence.getObjectAt(i);
				DERObject asn1Object = derObject.toASN1Object();

				if (asn1Object instanceof ASN1ObjectIdentifier) {
					ASN1ObjectIdentifier asn1ObjectIdentifier = (ASN1ObjectIdentifier) asn1Object;
					System.out.println(asn1ObjectIdentifier.getId());
				} else if (asn1Object instanceof DERSet) {
					DERSet set = (DERSet) asn1Object;
					System.out.println(set.size());
					for (int j = 0; j < set.size(); j++) {
						DEREncodable encodable = set.getObjectAt(j);
						System.out.println(encodable.getDERObject().getEncoded().length);
						System.out.println(encodable.getClass());

						TimeStampToken timeStampToken = new TimeStampToken(new CMSSignedData(encodable.getDERObject().getEncoded()));
						System.out.println(timeStampToken.getSID());
					}
				}

				// System.out.println(asn1Object.getClass().getCanonicalName());
				// System.out.println("Tamanho: " + derObject.getEncoded().length);
				// System.out.println(new String(Base64Utils.toBase64(derObject.getEncoded())));
			}
		}

		// System.out.println("Token Original");
		// TimeStampResponse timeStampResponse = new TimeStampResponse(IOUtils.toByteArray(new
		// FileInputStream("/tmp/tsa-client-1332419788210WnRO.tsr")));
		// TimeStampToken timeStampToken = timeStampResponse.getTimeStampToken();
		// System.out.println(new String(Base64Utils.toBase64(timeStampToken.getEncoded())));

		// signedData.get
	}

	public void xtest5() throws Exception {
		for (String s : new String[] { "/tmp/sign.pdf.p7s", "/tmp/carimboDeTempo.p7s", "/tmp/carimboDeTempo2.p7s" }) {
			System.out.println(s);
			FileInputStream fileOutputStream = new FileInputStream(s);
			byte[] data = IOUtils.toByteArray(fileOutputStream);
			PKCS7Signer signer = new BouncyCastlePKCS7Signer(null);
			Signature[] signatures = signer.getSignatures(data);
			for (Signature signature : signatures) {
				System.out.println(signature.getDate());
				System.out.println(signature.getValid());
				if (signature.getTimeStamp() != null) {
					System.out.println("\t" + ((X509Certificate) signature.getTimeStamp().getCertificates()[0]).getSubjectDN());
					System.out.println("\t" + signature.getTimeStamp().getDate());
					System.out.println("\t" + HexUtils.toHexString(signature.getTimeStamp().getEncoded()));
					System.out.println("\t" + HexUtils.toHexString(signature.getTimeStamp().getHash()));
					System.out.println("\t" + signature.getTimeStamp().getNonce());
					System.out.println("\t" + signature.getTimeStamp().getSerialNumber());
				}
			}
		}
	}

	public void xtest6() throws Exception {
		// FileInputStream fileOutputStream = new FileInputStream("/tmp/sign.pdf.p7s");
		FileInputStream fileOutputStream = new FileInputStream("/tmp/carimboDeTempo2.p7s");
		byte[] data = IOUtils.toByteArray(fileOutputStream);
		CMSSignedData signedData = new CMSSignedData(data);
		SignerInformationStore signerInformationStore = signedData.getSignerInfos();
		SignerInformation signerInformation = (SignerInformation) signerInformationStore.getSigners().iterator().next();
		Hashtable signedTable = signerInformation.getSignedAttributes().toHashtable();

		ASN1ObjectIdentifier id = new ASN1ObjectIdentifier("1.2.840.113549.1.9.16.2.12");

		DERSequence sequence = (DERSequence) signerInformation.getSignedAttributes().get(id).getDERObject();
		this.printSequence(sequence, "");
	}

	private void printSequence(final DERSequence sequence, final String prefix) throws Exception {
		for (int i = 0; i < sequence.size(); i++) {
			DERObject obj = (DERObject) sequence.getObjectAt(i);
			System.out.println(prefix + " " + obj.getClass() + " " + obj);
			if (obj instanceof DERSequence) {
				this.printSequence((DERSequence) obj, prefix + "  ");
			} else if (obj instanceof DERSet) {
				this.printSequence((DERSet) obj, prefix + "  ");
			} else if (obj instanceof DERTaggedObject) {
				DERTaggedObject dto = (DERTaggedObject) obj;
				System.out.println(dto.getLoadedObject().getClass());
				System.out.println(dto.getObject().getClass());
				System.out.println(new String(Base64Utils.toBase64(dto.getEncoded())));
			}
		}
	}

	private void printSequence(final DERSet set, final String prefix) throws Exception {
		for (int i = 0; i < set.size(); i++) {
			DERObject obj = (DERObject) set.getObjectAt(i);
			System.out.println(prefix + " " + obj.getClass() + " " + obj);
			if (obj instanceof DERSequence) {
				this.printSequence((DERSequence) obj, prefix + "  ");
			} else if (obj instanceof DERSet) {
				this.printSequence((DERSet) obj, prefix + "  ");
			} else if (obj instanceof DERTaggedObject) {
				DERTaggedObject dto = (DERTaggedObject) obj;
				System.out.println(dto.getLoadedObject().getClass());
				System.out.println(dto.getObject().getClass());
				System.out.println(new String(Base64Utils.toBase64(dto.getEncoded())));
			}
		}
	}

}
