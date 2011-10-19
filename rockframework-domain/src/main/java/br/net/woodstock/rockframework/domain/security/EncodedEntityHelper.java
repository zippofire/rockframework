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
package br.net.woodstock.rockframework.domain.security;

import br.net.woodstock.rockframework.security.crypt.KeyType;
import br.net.woodstock.rockframework.security.crypt.impl.AsStringCrypter;
import br.net.woodstock.rockframework.security.crypt.impl.Base64Crypter;
import br.net.woodstock.rockframework.security.crypt.impl.SyncCrypter;
import br.net.woodstock.rockframework.text.Generator;
import br.net.woodstock.rockframework.text.impl.RandomGenerator;

public abstract class EncodedEntityHelper {

	private static final AsStringCrypter	GLOBAL_CRYPTER	= new AsStringCrypter(new Base64Crypter(new SyncCrypter(KeyType.DESEDE)));

	private static final AsStringCrypter	KEY_CRYPTER		= new AsStringCrypter(new Base64Crypter(new SyncCrypter(KeyType.DESEDE)));

	private static final Generator			GENERATOR		= new RandomGenerator(8);

	private EncodedEntityHelper() {
		//
	}

	public static String encodeId(final String id) {
		String seed = EncodedEntityHelper.GENERATOR.generate();
		String encoded = EncodedEntityHelper.KEY_CRYPTER.encryptAsString(id, seed);
		String encodedId = EncodedEntityHelper.GLOBAL_CRYPTER.encryptAsString(seed + encoded);
		return encodedId;
	}

	public static String decodeId(final String id) {
		String decoded = EncodedEntityHelper.GLOBAL_CRYPTER.decryptAsString(id);
		String seed = decoded.substring(0, 8);
		String encodedId = decoded.substring(8);
		String decodedid = EncodedEntityHelper.KEY_CRYPTER.decryptAsString(encodedId, seed);
		return decodedid;
	}

}
