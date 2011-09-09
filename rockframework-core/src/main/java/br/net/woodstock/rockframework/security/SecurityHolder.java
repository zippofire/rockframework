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
package br.net.woodstock.rockframework.security;

import br.net.woodstock.rockframework.security.crypt.Crypter;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.crypt.KeyType;
import br.net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import br.net.woodstock.rockframework.security.crypt.impl.Base64Crypter;
import br.net.woodstock.rockframework.security.crypt.impl.CrypterEncoder;
import br.net.woodstock.rockframework.security.crypt.impl.SyncCrypter;
import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.digest.Digester;
import br.net.woodstock.rockframework.security.digest.impl.BasicDigester;
import br.net.woodstock.rockframework.security.digest.impl.DigesterEncoder;
import br.net.woodstock.rockframework.security.digest.impl.HexDigester;

public abstract class SecurityHolder {

	private static final Crypter	SYNC_CRYPTER	= new Base64Crypter(new SyncCrypter(KeyType.DESEDE));

	private static final Crypter	ASYNC_CRYPTER	= new Base64Crypter(new AsyncCrypter(KeyPairType.RSA));

	private static final Digester	MD5_DIGESTER	= new HexDigester(new BasicDigester(DigestType.MD5));

	private static final Digester	SHA1_DIGESTER	= new HexDigester(new BasicDigester(DigestType.SHA1));

	private static final Encoder	SYNC_ENCODER	= new CrypterEncoder(SecurityHolder.SYNC_CRYPTER);

	private static final Encoder	ASYNC_ENCODER	= new CrypterEncoder(SecurityHolder.ASYNC_CRYPTER);

	private static final Encoder	MD5_ENCODER		= new DigesterEncoder(SecurityHolder.MD5_DIGESTER);

	private static final Encoder	SHA1_ENCODER	= new DigesterEncoder(SecurityHolder.SHA1_DIGESTER);

	public static Crypter getSyncCrypter() {
		return SecurityHolder.SYNC_CRYPTER;
	}

	public static Crypter getAsyncCrypter() {
		return SecurityHolder.ASYNC_CRYPTER;
	}

	public static Digester getMd5Digester() {
		return SecurityHolder.MD5_DIGESTER;
	}

	public static Digester getSha1Digester() {
		return SecurityHolder.SHA1_DIGESTER;
	}

	public static Encoder getSyncEncoder() {
		return SecurityHolder.SYNC_ENCODER;
	}

	public static Encoder getAsyncEncoder() {
		return SecurityHolder.ASYNC_ENCODER;
	}

	public static Encoder getMd5Encoder() {
		return SecurityHolder.MD5_ENCODER;
	}

	public static Encoder getSha1Encoder() {
		return SecurityHolder.SHA1_ENCODER;
	}

}
