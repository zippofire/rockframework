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

import java.text.Normalizer;

import br.net.woodstock.rockframework.text.Transformer;

public final class NormalizerTransformer implements Transformer {

	private static final String	ACCENT_PATTERN	= "[^\\p{ASCII}]";

	private static Transformer	instance		= new NormalizerTransformer();

	private NormalizerTransformer() {
		super();
	}

	@Override
	public String transform(final String src) {
		if (src == null) {
			return null;
		}
		return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll(NormalizerTransformer.ACCENT_PATTERN, "");
	}

	public static Transformer getInstance() {
		return NormalizerTransformer.instance;
	}
}
