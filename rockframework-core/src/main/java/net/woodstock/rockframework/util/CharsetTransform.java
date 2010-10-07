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

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import net.woodstock.rockframework.utils.LocaleUtils;

public class CharsetTransform implements StringTransform {

	private Charset	from;

	private Charset	to;

	public CharsetTransform(final Charset from) {
		this(from, LocaleUtils.getCharset());
	}

	public CharsetTransform(final Charset from, final Charset to) {
		super();
		Assert.notNull(from, "from");
		Assert.notNull(to, "to");
		this.from = from;
		this.to = to;
	}

	@Override
	public String transform(final String str) {
		try {
			CharsetDecoder decoderFrom = this.from.newDecoder();
			CharsetEncoder encoderTo = this.to.newEncoder();

			CharBuffer charBufferFrom = decoderFrom.decode(ByteBuffer.wrap(str.getBytes(this.from)));
			String tmp = charBufferFrom.toString();

			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < tmp.length(); i++) {
				char c = tmp.charAt(i);
				if (encoderTo.canEncode(c)) {
					builder.append(c);
				}
			}

			return builder.toString();
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
	}
}
