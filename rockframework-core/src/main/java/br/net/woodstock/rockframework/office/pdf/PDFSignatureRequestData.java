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
package br.net.woodstock.rockframework.office.pdf;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.net.woodstock.rockframework.util.Assert;

public class PDFSignatureRequestData implements Serializable {

	private static final long	serialVersionUID	= 3825254674578069971L;

	private PrivateKey			privateKey;

	private Certificate			certificate;

	private String				reason;

	private String				location;

	private String				contactInfo;

	private PDFTSClientInfo		tsClientInfo;

	public PDFSignatureRequestData(final PrivateKey privateKey, final Certificate certificate) {
		this(privateKey, certificate, null, null, null, null);
	}

	public PDFSignatureRequestData(final PrivateKey privateKey, final Certificate certificate, final String reason, final String location, final String contactInfo, final PDFTSClientInfo tsClientInfo) {
		super();
		Assert.notNull(privateKey, "privateKey");
		Assert.notNull(certificate, "certificate");
		this.privateKey = privateKey;
		this.certificate = certificate;
		this.reason = reason;
		this.location = location;
		this.contactInfo = contactInfo;
		this.tsClientInfo = tsClientInfo;
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public Certificate getCertificate() {
		return this.certificate;
	}

	public String getReason() {
		return this.reason;
	}

	public String getLocation() {
		return this.location;
	}

	public String getContactInfo() {
		return this.contactInfo;
	}

	public PDFTSClientInfo getTsClientInfo() {
		return this.tsClientInfo;
	}

}
