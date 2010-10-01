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
package net.woodstock.rockframework.security.digest.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.woodstock.rockframework.security.digest.DigestType;
import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.security.digest.DigesterException;
import net.woodstock.rockframework.util.Assert;

public class BasicDigester implements Digester {

	private DigestType	type;

	public BasicDigester(final DigestType type) {
		super();
		Assert.notNull(type, "type");
		this.type = type;
	}

	@Override
	public byte[] digest(final byte[] data) {
		Assert.notEmpty(data, "data");
		try {
			MessageDigest digest = MessageDigest.getInstance(this.type.getAlgorithm());
			digest.update(data);
			byte[] digested = digest.digest();
			return digested;
		} catch (NoSuchAlgorithmException e) {
			throw new DigesterException(e);
		}
	}
}
