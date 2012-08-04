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

import br.net.woodstock.rockframework.security.sign.Signer;

public class AsStringSigner extends DelegateSigner {

	public AsStringSigner(final Signer signer) {
		super(signer);
	}

	public String signAsString(final byte[] data) {
		byte[] digest = super.sign(data);
		return new String(digest);
	}

	public boolean verifyAsString(final byte[] data, final String signature) {
		return super.verify(data, signature.getBytes());
	}
}
