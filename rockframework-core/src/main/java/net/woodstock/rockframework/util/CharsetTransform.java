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
import java.nio.charset.CodingErrorAction;

import net.woodstock.rockframework.io.IOException;
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
			CharsetDecoder decoder = this.from.newDecoder();
			CharsetEncoder encoder = this.to.newEncoder();
			CharsetDecoder decoderTo = this.to.newDecoder();

			decoder.onMalformedInput(CodingErrorAction.IGNORE);
			decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);

			encoder.onMalformedInput(CodingErrorAction.IGNORE);
			encoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
			decoderTo.onMalformedInput(CodingErrorAction.IGNORE);
			decoderTo.onUnmappableCharacter(CodingErrorAction.IGNORE);

			CharBuffer charBuffer = decoder.decode(ByteBuffer.wrap(str.getBytes(this.from)));
			ByteBuffer byteBuffer = encoder.encode(charBuffer);

			String s = decoderTo.decode(byteBuffer).toString();

			return s;
		} catch (CharacterCodingException e) {
			throw new IOException(e);
		}
	}
}
