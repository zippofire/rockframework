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
package br.net.woodstock.rockframework.security.timestamp.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.office.pdf.PDFException;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.digest.Digester;
import br.net.woodstock.rockframework.security.digest.impl.BasicDigester;
import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.utils.StringUtils;
import br.net.woodstock.rockframework.utils.SystemUtils;

public abstract class BouncyCastleTimeStampClient implements TimeStampClient {

	public static final String	PROVIDER_NAME			= BouncyCastleProviderHelper.PROVIDER_NAME;

	public static final String	RSA_OID					= OIWObjectIdentifiers.idSHA1.getId();

	private static final String	FILE_PREFIX				= "tsa-client-";

	private static final String	REQUEST_FILE_SUFFIX		= "tsq";

	private static final String	RESPONSE_FILE_SUFFIX	= "tsr";

	private static final int	ID_SIZE					= 4;

	private boolean				debug;

	public BouncyCastleTimeStampClient() {
		super();
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return this.debug;
	}

	@Override
	public TimeStamp getTimeStamp(final byte[] data) {
		try {
			String id = System.currentTimeMillis() + StringUtils.random(BouncyCastleTimeStampClient.ID_SIZE);
			File dir = this.getLogDir();

			TimeStampRequest request = this.getTimeStampRequest(data);

			if (this.debug) {
				this.saveRequest(request, dir, id);
			}

			byte[] responseBytes = this.sendRequest(request);

			TimeStampResponse response = this.getTimeStampResponse(request, responseBytes);

			if (this.debug) {
				this.saveResponse(response, dir, id);
			}

			response.validate(request);

			TimeStampToken timeStampToken = response.getTimeStampToken();

			if (timeStampToken == null) {
				throw new IllegalStateException("TimeStampToken not found in response");
			}

			return BouncyCastleTimeStampHelper.toTimeStamp(timeStampToken);
		} catch (Exception e) {
			throw new PDFException(e);
		}
	}

	protected TimeStampRequest getTimeStampRequest(final byte[] imprint) {
		Digester digester = new BasicDigester(DigestType.valueOf(DigestType.SHA1.getAlgorithm()));
		byte[] digest = digester.digest(imprint);

		TimeStampRequestGenerator generator = new TimeStampRequestGenerator();
		generator.setCertReq(true);

		BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
		TimeStampRequest request = generator.generate(BouncyCastleTimeStampClient.RSA_OID, digest, nonce);

		return request;
	}

	protected byte[] getResponseBytes(final byte[] responseBytes) throws IOException {
		ASN1InputStream asn1InputStream = new ASN1InputStream(responseBytes);
		DERObject derObject = asn1InputStream.readObject();
		byte[] encoded = derObject.getEncoded();
		return encoded;
	}

	protected TimeStampResponse getTimeStampResponse(final TimeStampRequest request, final byte[] responseBytes) throws IOException, TSPException {
		TimeStampResponse response = new TimeStampResponse(responseBytes);

		response.validate(request);
		PKIFailureInfo failure = response.getFailInfo();

		if ((failure != null) && (failure.intValue() != 0)) {
			throw new IllegalStateException("Failure Status " + failure.intValue());
		}

		TimeStampToken timeStampToken = response.getTimeStampToken();
		if (timeStampToken == null) {
			throw new IllegalStateException("TimeStampToken not found in response");
		}
		return response;
	}

	protected void saveRequest(final TimeStampRequest request, final File dir, final String id) throws IOException {
		String fileName = BouncyCastleTimeStampClient.FILE_PREFIX + id + "." + BouncyCastleTimeStampClient.REQUEST_FILE_SUFFIX;
		File file = new File(dir, fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		byte[] data = request.getEncoded();
		fileOutputStream.write(data);
		fileOutputStream.close();
		CoreLog.getInstance().getLog().info("Request [" + id + "]: " + file.getAbsolutePath());
	}

	protected void saveResponse(final TimeStampResponse response, final File dir, final String id) throws IOException {
		String fileName = BouncyCastleTimeStampClient.FILE_PREFIX + id + "." + BouncyCastleTimeStampClient.RESPONSE_FILE_SUFFIX;
		File file = new File(dir, fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		byte[] data = response.getEncoded();
		fileOutputStream.write(data);
		fileOutputStream.close();
		CoreLog.getInstance().getLog().info("Response [" + id + "]: " + file.getAbsolutePath());
	}

	protected File getLogDir() {
		File tmpDir = new File(SystemUtils.getProperty(SystemUtils.JAVA_IO_TMPDIR_PROPERTY));
		return tmpDir;
	}

	protected abstract byte[] sendRequest(final TimeStampRequest request) throws IOException;

}
