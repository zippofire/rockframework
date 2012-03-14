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

public class PrivateKeyHolder implements Serializable {

	private static final long	serialVersionUID	= -4586160741947727987L;

	private PrivateKey			privateKey;

	private Certificate[]		chain;

	public PrivateKeyHolder(final PrivateKey privateKey, final Certificate[] chain) {
		super();
		this.privateKey = privateKey;
		this.chain = chain;
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public Certificate[] getChain() {
		return this.chain;
	}

}
