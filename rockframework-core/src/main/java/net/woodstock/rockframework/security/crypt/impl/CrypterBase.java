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

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

import net.woodstock.rockframework.security.common.Charset;
import net.woodstock.rockframework.security.crypt.Algorithm;
import net.woodstock.rockframework.security.crypt.Crypter;

abstract class CrypterBase implements Crypter {

	private Algorithm	algorithm;

	private Charset		charset;

	private Cipher		ecipher;

	private Cipher		dcipher;

	protected CrypterBase(Algorithm algorithm, Charset charset) {
		this.algorithm = algorithm;
		this.charset = charset;
	}

	public Algorithm getAlgorithm() {
		return this.algorithm;
	}

	protected void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Charset getCharset() {
		return this.charset;
	}

	protected void setCharset(Charset charset) {
		this.charset = charset;
	}

	protected Cipher getDcipher() {
		return this.dcipher;
	}

	protected void setDcipher(Cipher dcipher) {
		this.dcipher = dcipher;
	}

	protected Cipher getEcipher() {
		return this.ecipher;
	}

	protected void setEcipher(Cipher ecipher) {
		this.ecipher = ecipher;
	}

	public abstract String encrypt(String str) throws IOException, GeneralSecurityException;

	public abstract String decrypt(String str) throws IOException, GeneralSecurityException;

}
