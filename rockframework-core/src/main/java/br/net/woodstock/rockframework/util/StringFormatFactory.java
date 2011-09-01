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

public final class StringFormatFactory extends FormatFactory<StringFormater> {

	private static StringFormatFactory	instance	= new StringFormatFactory();

	private StringFormatFactory() {
		super();
	}

	@Override
	public StringFormater getFormat(final String pattern) {
		Assert.notEmpty(pattern, "pattern");

		if (this.containsOnCache(pattern)) {
			return this.getFromCache(pattern);
		}
		StringFormater format = new StringFormater(pattern);
		this.addToCache(pattern, format);
		return format;
	}

	// Instance
	public static StringFormatFactory getInstance() {
		return instance;
	}
}
