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

import br.net.woodstock.rockframework.security.timestamp.TimeStamp;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;

import com.lowagie.text.pdf.PdfPKCS7;
import com.lowagie.text.pdf.TSAClient;

public class DelegateLowagieTSAClient implements TSAClient {

	private static final int	PADDING_SIZE	= 32;

	private TimeStampClient		client;

	private byte[]				timeStampToken;

	private int					tokenSizeEstimate;

	public DelegateLowagieTSAClient(final TimeStampClient client) {
		super();
		this.client = client;
	}

	@Override
	public byte[] getTimeStampToken(final PdfPKCS7 caller, final byte[] imprint) throws Exception {
		TimeStamp timeStamp = this.client.getTimeStamp(imprint);
		this.timeStampToken = timeStamp.getEncoded();
		this.tokenSizeEstimate = this.timeStampToken.length + DelegateLowagieTSAClient.PADDING_SIZE;
		return this.timeStampToken;
	}

	@Override
	public int getTokenSizeEstimate() {
		return this.tokenSizeEstimate;
	}

}
