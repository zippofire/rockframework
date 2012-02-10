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
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.sign.Signer;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.util.Assert;

public class KeyPairSigner implements Signer {

	private KeyPair		keyPair;

	private SignatureType	signType;

	public KeyPairSigner(final KeyPair keyPair, final SignatureType signType) {
		super();
		Assert.notNull(keyPair, "keyPair");
		Assert.notNull(signType, "signType");
		this.keyPair = keyPair;
		this.signType = signType;
	}

	@Override
	public byte[] sign(final byte[] data) {
		try {
			Signature s = Signature.getInstance(this.signType.getAlgorithm());

			s.initSign(this.keyPair.getPrivate());
			s.update(data);

			byte[] bytes = s.sign();
			return bytes;
		} catch (NoSuchAlgorithmException e) {
			throw new SignerException(e);
		} catch (InvalidKeyException e) {
			throw new SignerException(e);
		} catch (SignatureException e) {
			throw new SignerException(e);
		}
	}

	@Override
	public boolean verify(final byte[] data, final byte[] signature) {
		try {
			Signature s = Signature.getInstance(this.signType.getAlgorithm());

			s.initVerify(this.keyPair.getPublic());
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
