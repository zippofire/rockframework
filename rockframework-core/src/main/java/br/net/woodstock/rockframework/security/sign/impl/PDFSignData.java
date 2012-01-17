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

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.net.woodstock.rockframework.util.Assert;

public class PDFSignData implements Serializable {

	private static final long	serialVersionUID	= 3825254674578069971L;

	private PrivateKey			privateKey;

	private Certificate			certificate;

	private Certificate			rootCertificate;

	private String				reason;

	private String				location;

	private String				contactInfo;

	private Object				tsaClient;

	public PDFSignData(final PrivateKey privateKey, final Certificate certificate) {
		super();
		Assert.notNull(privateKey, "privateKey");
		Assert.notEmpty(certificate, "certificate");
		this.privateKey = privateKey;
		this.certificate = certificate;
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public Certificate getCertificate() {
		return this.certificate;
	}

	public Certificate getRootCertificate() {
		return this.rootCertificate;
	}

	public void setRootCertificate(final Certificate rootCertificate) {
		this.rootCertificate = rootCertificate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public String getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(final String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public Object getTsaClient() {
		return this.tsaClient;
	}

	public void setTsaClient(final Object tsaClient) {
		this.tsaClient = tsaClient;
	}

	// Aux
	public Certificate[] getCertificateChain() {
		if (this.rootCertificate != null) {
			return new Certificate[] { this.certificate, this.rootCertificate };
		}
		return new Certificate[] { this.certificate };
	}

}
