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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.sign.Signer;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.util.Assert;

public class PublicKeySigner implements Signer {

	private PublicKey	publicKey;

	private SignType	signType;

	public PublicKeySigner(final PublicKey publicKey, final SignType signType) {
		super();
		Assert.notNull(publicKey, "publicKey");
		Assert.notNull(signType, "signType");
		this.publicKey = publicKey;
		this.signType = signType;
	}

	@Override
	public byte[] sign(final byte[] data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean verify(final byte[] data, final byte[] signature) {
		try {
			Signature s = Signature.getInstance(this.signType.getAlgorithm());

			s.initVerify(this.publicKey);
			s.update(data);

			boolean ok = s.verify(signature);
			return ok;
		} catch (NoSuchAlgorithmException e) {
			throw new SignerException(e);
		} catch (InvalidKeyException e) {
			throw new SignerException(e);
		} catch (SignatureException e) {
			throw new SignerException(e);
		}
	}

}
