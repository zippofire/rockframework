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

import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.security.timestamp.TimeStampException;
import br.net.woodstock.rockframework.security.timestamp.TimeStampProcessor;
import br.net.woodstock.rockframework.utils.StringUtils;
import br.net.woodstock.rockframework.utils.SystemUtils;

public abstract class BouncyCastleTimeStampProcessor implements TimeStampProcessor {

	private static final String	FILE_PREFIX				= "tsa-client-";

	private static final String	REQUEST_FILE_SUFFIX		= "tsq";

	private static final String	RESPONSE_FILE_SUFFIX	= "tsr";

	private static final int	ID_SIZE					= 4;

	private boolean				debug;

	public BouncyCastleTimeStampProcessor() {
		super();
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	public boolean isDebug() {
		return this.debug;
	}

	@Override
	public byte[] getBinaryResponse(final byte[] request) {
		try {
			String id = System.currentTimeMillis() + StringUtils.random(BouncyCastleTimeStampProcessor.ID_SIZE);
			File dir = this.getLogDir();

			TimeStampRequest timeStampRequest = new TimeStampRequest(request);

			if (this.debug) {
				this.saveRequest(timeStampRequest, dir, id);
			}

			byte[] responseBytes = this.sendRequest(timeStampRequest);

			TimeStampResponse response = this.getTimeStampResponse(timeStampRequest, responseBytes);

			if (this.debug) {
				this.saveResponse(response, dir, id);
			}

			return response.getEncoded();
		} catch (Exception e) {
			throw new TimeStampException(e);
		}
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
		String fileName = BouncyCastleTimeStampProcessor.FILE_PREFIX + id + "." + BouncyCastleTimeStampProcessor.REQUEST_FILE_SUFFIX;
		File file = new File(dir, fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		byte[] data = request.getEncoded();
		fileOutputStream.write(data);
		fileOutputStream.close();
		CoreLog.getInstance().getLog().info("Request [" + id + "]: " + file.getAbsolutePath());
	}

	protected void saveResponse(final TimeStampResponse response, final File dir, final String id) throws IOException {
		String fileName = BouncyCastleTimeStampProcessor.FILE_PREFIX + id + "." + BouncyCastleTimeStampProcessor.RESPONSE_FILE_SUFFIX;
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
