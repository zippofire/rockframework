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
package net.woodstock.rockframework.util;

import org.apache.commons.codec.binary.Base64;

class ApacheBase64Encoder extends Base64Encoder {

	@Override
	public String decode(String s) {
		String str = new String(Base64.decodeBase64(s.getBytes()));
		return str;
	}

	@Override
	public byte[] decode(byte[] b) {
		byte[] bytes = Base64.encodeBase64Chunked(b);
		return bytes;
	}

	@Override
	public String encode(String s) {
		String str = new String(Base64.encodeBase64Chunked(s.getBytes())).trim();
		return str;
	}

	@Override
	public byte[] encode(byte[] b) {
		byte[] bytes = Base64.encodeBase64Chunked(b);
		return bytes;
	}

}
