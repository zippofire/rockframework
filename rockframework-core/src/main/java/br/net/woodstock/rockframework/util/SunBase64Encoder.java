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
package br.net.woodstock.rockframework.util;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import br.net.woodstock.rockframework.ExecutionException;

@SuppressWarnings("restriction")
class SunBase64Encoder extends Base64Encoder {

	private BASE64Decoder	decoder;

	private BASE64Encoder	encoder;

	public SunBase64Encoder() {
		super();
		this.decoder = new BASE64Decoder();
		this.encoder = new BASE64Encoder();
	}

	@Override
	public String decode(final String s) {
		try {
			String str = new String(this.decoder.decodeBuffer(s));
			return str;
		} catch (IOException e) {
			throw new ExecutionException(e);
		}
	}

	@Override
	public byte[] decode(final byte[] b) {
		try {
			String s = new String(b);
			String str = new String(this.decoder.decodeBuffer(s));
			byte[] bytes = str.getBytes();
			return bytes;
		} catch (IOException e) {
			throw new ExecutionException(e);
		}
	}

	@Override
	public String encode(final String s) {
		String str = this.encoder.encode(s.getBytes());
		return str;
	}

	@Override
	public byte[] encode(final byte[] b) {
		String str = this.encoder.encode(b);
		byte[] bytes = str.getBytes();
		return bytes;
	}

}
