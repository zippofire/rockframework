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
import java.security.cert.Certificate;

public class Signatory implements Serializable {

	private static final long	serialVersionUID	= -2357276627134223465L;

	private String				subject;

	private String				issuer;

	private Certificate			certificate;

	public Signatory() {
		super();
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getIssuer() {
		return this.issuer;
	}

	public void setIssuer(final String issuer) {
		this.issuer = issuer;
	}

	public Certificate getCertificate() {
		return this.certificate;
	}

	public void setCertificate(final Certificate certificate) {
		this.certificate = certificate;
	}

}
