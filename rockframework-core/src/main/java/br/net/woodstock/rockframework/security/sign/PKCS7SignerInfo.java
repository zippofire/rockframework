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
package br.net.woodstock.rockframework.security.sign;

import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;
import br.net.woodstock.rockframework.util.Assert;

public class PKCS7SignerInfo extends SignerInfo {

	private static final long	serialVersionUID	= -4388076526792546789L;

	private SignerInfo[]		signerInfos;

	private TimeStampClient		timeStampClient;

	public PKCS7SignerInfo(final SignerInfo signerInfo) {
		super();
		Assert.notNull(signerInfo, "signerInfo");
		this.signerInfos = new SignerInfo[] { signerInfo };
	}

	public PKCS7SignerInfo(final SignerInfo[] signerInfos) {
		super();
		Assert.notEmpty(signerInfos, "signerInfos");
		this.signerInfos = signerInfos;
	}

	public void setSignerInfos(final SignerInfo[] signerInfos) {
		this.signerInfos = signerInfos;
	}

	public SignerInfo[] getSignerInfos() {
		return this.signerInfos;
	}

	public TimeStampClient getTimeStampClient() {
		return this.timeStampClient;
	}

	public void setTimeStampClient(final TimeStampClient timeStampClient) {
		this.timeStampClient = timeStampClient;
	}

	// Aux
	@Override
	public Certificate getCertificate() {
		if ((this.signerInfos != null) && (this.signerInfos.length > 0)) {
			return this.signerInfos[0].getCertificate();
		}
		return null;
	}

	@Override
	public void setCertificate(final Certificate certificate) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PrivateKey getPrivateKey() {
		if ((this.signerInfos != null) && (this.signerInfos.length > 0)) {
			return this.signerInfos[0].getPrivateKey();
		}
		return null;
	}

	@Override
	public void setPrivateKey(final PrivateKey privateKey) {
		throw new UnsupportedOperationException();
	}

}
