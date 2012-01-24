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

package br.net.woodstock.rockframework.security.cert;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class CertificateHolder implements Serializable {

	private static final long	serialVersionUID	= -8497194259688129655L;

	private Certificate			certificate;

	private PrivateKey			privateKey;

	private Certificate			rootCertificate;

	public CertificateHolder() {
		super();
	}

	public CertificateHolder(final Certificate certificate, final PrivateKey privateKey) {
		super();
		this.certificate = certificate;
		this.privateKey = privateKey;
	}

	public Certificate getCertificate() {
		return this.certificate;
	}

	public void setCertificate(final Certificate certificate) {
		this.certificate = certificate;
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public void setPrivateKey(final PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public Certificate getRootCertificate() {
		return this.rootCertificate;
	}

	public void setRootCertificate(final Certificate rootCertificate) {
		this.rootCertificate = rootCertificate;
	}

	// Aux
	public Certificate[] getChain() {
		if (this.rootCertificate != null) {
			return new Certificate[] { this.certificate, this.rootCertificate };
		}
		return new Certificate[] { this.certificate };
	}

}
