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
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import br.net.woodstock.rockframework.security.crypt.KeyPairHolder;
import br.net.woodstock.rockframework.security.sign.SignType;
import br.net.woodstock.rockframework.security.sign.Signer;
import br.net.woodstock.rockframework.security.sign.SignerException;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;

public class KeyPairHolderSigner implements Signer {

	private KeyPair		keyPair;

	private SignType	signType;

	public KeyPairHolderSigner(final KeyPairHolder keyPairHolder, final SignType signType) {
		super();
		Assert.notNull(keyPairHolder, "keyPairHolder");
		Assert.notNull(signType, "signType");

		try {
			KeyFactory factory = KeyFactory.getInstance(keyPairHolder.getAlgorithm());
			PrivateKey privateKey = null;
			PublicKey publicKey = null;
			if (keyPairHolder.getPrivateKey() != null) {
				byte[] bytes = Base64Utils.fromBase64(keyPairHolder.getPrivateKey());
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
				privateKey = factory.generatePrivate(keySpec);
			}
			if (keyPairHolder.getPublicKey() != null) {
				byte[] bytes = Base64Utils.fromBase64(keyPairHolder.getPublicKey());
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
				publicKey = factory.generatePublic(keySpec);
			}
			this.keyPair = new KeyPair(publicKey, privateKey);
			this.signType = signType;
		} catch (Exception e) {
			throw new SignerException(e);
		}

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
