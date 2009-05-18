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
package net.woodstock.rockframework.security.crypt.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import net.woodstock.rockframework.security.common.Charset;
import net.woodstock.rockframework.security.crypt.Algorithm;
import net.woodstock.rockframework.utils.Base64Utils;

public class SyncCrypter extends CrypterBase {

	private SecretKey	key;

	protected SyncCrypter(InputStream key, Algorithm algorithm, Charset charset) throws IOException,
			InstantiationException, ClassNotFoundException {
		super(algorithm, charset);
		if (key == null) {
			// TODO
			throw new InstantiationException("security.crypt.no-key");
		}
		this.key = (SecretKey) Base64Utils.unserializeFrom(key);
	}

	public SecretKey getKey() {
		return this.key;
	}

	@Override
	public String encrypt(String str) throws IOException, GeneralSecurityException {
		if (this.getEcipher() == null) {
			this.setEcipher(Cipher.getInstance(this.getAlgorithm().algorithm()));
			this.getEcipher().init(Cipher.ENCRYPT_MODE, this.key);
		}
		byte[] bytes = str.getBytes(this.getCharset().charset());
		byte[] enc = this.getEcipher().doFinal(bytes);
		return Base64Utils.toBase64String(enc);
	}

	@Override
	public String decrypt(String str) throws IOException, GeneralSecurityException {
		if (this.getDcipher() == null) {
			this.setDcipher(Cipher.getInstance(this.getAlgorithm().algorithm()));
			this.getDcipher().init(Cipher.DECRYPT_MODE, this.key);
		}
		byte[] dec = Base64Utils.fromBase64(str);
		byte[] bytes = this.getDcipher().doFinal(dec);
		return new String(bytes, this.getCharset().charset());
	}

	public static SyncCrypter newInstance(String keyFile) throws IOException, InstantiationException,
			ClassNotFoundException {
		return SyncCrypter.newInstance(new File(keyFile), Algorithm.DEFAULT_SYNC, Charset.DEFAULT);
	}

	public static SyncCrypter newInstance(File keyFile) throws IOException, InstantiationException,
			ClassNotFoundException {
		return SyncCrypter.newInstance(keyFile, Algorithm.DEFAULT_SYNC, Charset.DEFAULT);
	}

	public static SyncCrypter newInstance(InputStream key) throws IOException, InstantiationException,
			ClassNotFoundException {
		return SyncCrypter.newInstance(key, Algorithm.DEFAULT_SYNC, Charset.DEFAULT);
	}

	public static SyncCrypter newInstance(String keyFile, Algorithm algorithm) throws IOException,
			InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(new File(keyFile), algorithm, Charset.DEFAULT);
	}

	public static SyncCrypter newInstance(File keyFile, Algorithm algorithm) throws IOException,
			InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(keyFile, algorithm, Charset.DEFAULT);
	}

	public static SyncCrypter newInstance(InputStream key, Algorithm algorithm) throws IOException,
			InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(key, algorithm, Charset.DEFAULT);
	}

	public static SyncCrypter newInstance(String keyFile, Charset charset) throws IOException,
			InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(new File(keyFile), Algorithm.DEFAULT_SYNC, charset);
	}

	public static SyncCrypter newInstance(File keyFile, Charset charset) throws IOException,
			InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(keyFile, Algorithm.DEFAULT_SYNC, charset);
	}

	public static SyncCrypter newInstance(InputStream key, Charset charset) throws IOException,
			InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(key, Algorithm.DEFAULT_SYNC, charset);
	}

	public static SyncCrypter newInstance(String keyFile, Algorithm algorithm, Charset charset)
			throws IOException, InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(new File(keyFile), algorithm, charset);
	}

	public static SyncCrypter newInstance(File keyFile, Algorithm algorithm, Charset charset)
			throws IOException, InstantiationException, ClassNotFoundException {
		return SyncCrypter.newInstance(new FileInputStream(keyFile), algorithm, charset);
	}

	public static SyncCrypter newInstance(InputStream key, Algorithm algorithm, Charset charset)
			throws IOException, InstantiationException, ClassNotFoundException {
		return new SyncCrypter(key, algorithm, charset);

	}

	public static void generateKey(String file) throws IOException, GeneralSecurityException {
		SyncCrypter.generateKey(new File(file), Algorithm.DEFAULT_SYNC);
	}

	public static void generateKey(File file) throws IOException, GeneralSecurityException {
		SyncCrypter.generateKey(file, Algorithm.DEFAULT_SYNC);
	}

	public static void generateKey(String file, Algorithm algorithm) throws IOException,
			GeneralSecurityException {
		SyncCrypter.generateKey(new File(file), algorithm);
	}

	public static void generateKey(File file, Algorithm algorithm) throws IOException,
			GeneralSecurityException {
		SecretKey key = KeyGenerator.getInstance(algorithm.algorithm()).generateKey();
		Base64Utils.serializeTo(key, file);
	}
}
