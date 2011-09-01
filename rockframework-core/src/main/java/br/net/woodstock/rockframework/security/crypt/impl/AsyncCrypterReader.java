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
package br.net.woodstock.rockframework.security.crypt.impl;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import br.net.woodstock.rockframework.security.crypt.CrypterException;
import br.net.woodstock.rockframework.security.crypt.CrypterReader;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.Base64Utils;


public class AsyncCrypterReader implements CrypterReader<AsyncCrypter> {

	private InputStream	privateKeyInputStream;

	private InputStream	publicKeyInputStream;

	private KeyPairType	type;

	public AsyncCrypterReader(final InputStream privateKeyInputStream, final InputStream publicKeyInputStream, final KeyPairType type) {
		super();
		Assert.notNull(type, "type");
		this.privateKeyInputStream = privateKeyInputStream;
		this.publicKeyInputStream = publicKeyInputStream;
		this.type = type;
	}

	@Override
	public AsyncCrypter read() {
		try {
			KeyFactory factory = KeyFactory.getInstance(this.type.getAlgorithm());
			PrivateKey privateKey = null;
			PublicKey publicKey = null;
			if (this.privateKeyInputStream != null) {
				byte[] base64 = new byte[this.privateKeyInputStream.available()];
				this.privateKeyInputStream.read(base64);
				
				byte[] bytes = Base64Utils.fromBase64(base64);
				PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
				privateKey = factory.generatePrivate(keySpec);
			}
			if (this.publicKeyInputStream != null) {
				byte[] base64 = new byte[this.publicKeyInputStream.available()];
				this.publicKeyInputStream.read(base64);
				
				byte[] bytes = Base64Utils.fromBase64(base64);
				X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
				publicKey = factory.generatePublic(keySpec);
			}
			KeyPair keyPair = new KeyPair(publicKey, privateKey);
			AsyncCrypter crypter = new AsyncCrypter(keyPair);
			return crypter;
		} catch (Exception e) {
			throw new CrypterException(e);
		}
	}

}
