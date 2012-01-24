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

import java.io.Serializable;

import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;

public class SignRequest implements Serializable {

	private static final long	serialVersionUID	= -4388076526792546789L;

	private String				reason;

	private String				location;

	private String				contactInfo;

	private TimeStampClient		timeStampClient;

	private CertificateHolder[]	certificates;

	public SignRequest() {
		super();
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

	public CertificateHolder[] getCertificates() {
		return this.certificates;
	}

	public void setCertificates(final CertificateHolder[] certificates) {
		this.certificates = certificates;
	}

}
