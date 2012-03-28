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
package br.net.woodstock.rockframework.text.impl;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

import br.net.woodstock.rockframework.ExecutionException;
import br.net.woodstock.rockframework.text.Transformer;
import br.net.woodstock.rockframework.util.Assert;

public class CharsetTransformer implements Transformer {

	private Charset			from;

	private Charset			to;

	private CharsetDecoder	decoder;

	private CharsetEncoder	encoder;

	public CharsetTransformer(final Charset from) {
		this(from, Charset.defaultCharset());
	}

	public CharsetTransformer(final Charset from, final Charset to) {
		super();
		Assert.notNull(from, "from");
		Assert.notNull(to, "to");
		this.from = from;
		this.to = to;
		this.decoder = this.from.newDecoder();
		this.encoder = this.to.newEncoder();

		this.decoder.onMalformedInput(CodingErrorAction.IGNORE);
		this.decoder.onUnmappableCharacter(CodingErrorAction.IGNORE);

		this.encoder.onMalformedInput(CodingErrorAction.IGNORE);
		this.encoder.onUnmappableCharacter(CodingErrorAction.IGNORE);
	}

	@Override
	public String transform(final String str) {
		try {
			ByteBuffer byteBuffer = this.encoder.encode(CharBuffer.wrap(str));
			CharBuffer charBuffer = this.decoder.decode(byteBuffer);

			String s = charBuffer.toString();

			return s;
		} catch (CharacterCodingException e) {
			throw new ExecutionException(e);
		}
	}
}
