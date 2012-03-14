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
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;

public class Base64Signer extends DelegateSigner {

	public Base64Signer(final Signer signer) {
		super(signer);
	}

	@Override
	public byte[] sign(final byte[] data) {
		Assert.notNull(data, "data");
		byte[] signature = super.sign(data);
		byte[] b64 = Base64Utils.toBase64(signature);
		return b64;
	}

	@Override
	public boolean verify(final byte[] data, final byte[] signature) {
		Assert.notNull(data, "data");
		Assert.notNull(signature, "signature");
		byte[] b64 = Base64Utils.fromBase64(signature);
		boolean b = super.verify(data, b64);
		return b;
	}

}
