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
package br.net.woodstock.rockframework.security.sign.impl;

import java.io.ByteArrayInputStream;

import br.net.woodstock.rockframework.office.pdf.PDFSignatureRequestData;
import br.net.woodstock.rockframework.office.pdf.impl.ITextManager;
import br.net.woodstock.rockframework.security.sign.DocumentSigner;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.IOUtils;

public class PDFSigner implements DocumentSigner {

	private PDFSignData	signData;

	public PDFSigner(final PDFSignData signData) {
		super();
		Assert.notNull(signData, "signData");
		this.signData = signData;
	}

	@Override
	public byte[] sign(final byte[] data) {
		try {
			PDFSignatureRequestData requestData = new PDFSignatureRequestData(this.signData.getPrivateKey(), this.signData.getCertificate());
			requestData.setContactInfo(this.signData.getContactInfo());
			requestData.setLocation(this.signData.getLocation());
			requestData.setReason(this.signData.getReason());
			requestData.setRootCertificate(this.signData.getRootCertificate());
			requestData.setTsaClient(this.signData.getTsaClient());

			ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

			byte[] bytes = IOUtils.toByteArray(new ITextManager().sign(inputStream, requestData));

			return bytes;
		} catch (Exception e) {
			throw new SignerException(e);
		}
	}

}
