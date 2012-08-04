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
package br.net.woodstock.rockframework.security.cert.impl;

import java.security.cert.Certificate;

import br.net.woodstock.rockframework.util.Assert;

public class OCSP {

	private String		url;

	private Certificate	certificate;

	public OCSP(final String url, final Certificate certificate) {
		super();
		Assert.notEmpty(url, url);
		Assert.notNull(certificate, "certificate");
		this.url = url;
		this.certificate = certificate;
	}

	public String getUrl() {
		return this.url;
	}

	public Certificate getCertificate() {
		return this.certificate;
	}

}
