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

import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;

public class PDFSignData extends CertificateHolder {

	private static final long	serialVersionUID	= 3825254674578069971L;

	private Certificate			rootCertificate;

	private String				reason;

	private String				location;

	private String				contactInfo;

	private TimeStampClient		timeStampClient;

	public PDFSignData(final Certificate certificate, final PrivateKey privateKey) {
		super(certificate, privateKey);
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

	public TimeStampClient getTimeStampClient() {
		return this.timeStampClient;
	}

	public void setTimeStampClient(final TimeStampClient timeStampClient) {
		this.timeStampClient = timeStampClient;
	}

	// Aux
	public Certificate[] getCertificateChain() {
		if (this.rootCertificate != null) {
			return new Certificate[] { this.getCertificate(), this.rootCertificate };
		}
		return new Certificate[] { this.getCertificate() };
	}

}
