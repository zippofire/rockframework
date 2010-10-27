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
package net.woodstock.rockframework.security.util;

import net.woodstock.rockframework.security.digest.DigestType;
import net.woodstock.rockframework.security.digest.Digester;
import net.woodstock.rockframework.security.digest.impl.AsStringDigester;
import net.woodstock.rockframework.security.digest.impl.Base64Digester;
import net.woodstock.rockframework.security.digest.impl.BasicDigester;

public class DigesterPasswordEncoder implements PasswordEncoder {

	private AsStringDigester	digester;

	public DigesterPasswordEncoder(final DigestType type) {
		super();
		Digester basic = new BasicDigester(type);
		Digester base64 = new Base64Digester(basic);
		this.digester = new AsStringDigester(base64);
	}

	@Override
	public String encode(final String password) {
		return this.digester.digestAsString(password);
	}
}
