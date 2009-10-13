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
package net.woodstock.rockframework.conversion.text;

import net.woodstock.rockframework.conversion.ConverterFactory;
import net.woodstock.rockframework.conversion.TextConverter;

public class TextConverterFactory implements ConverterFactory<String, Object> {

	private static TextConverterFactory	factory;

	private TextConverter<Object>		converter;

	public TextConverterFactory() {
		super();
		this.converter = new BeanConverter();
	}

	public TextConverter<Object> getConverter() {
		return this.converter;
	}

	public static TextConverterFactory getInstance() {
		if (TextConverterFactory.factory == null) {
			synchronized (TextConverterFactory.class) {
				if (TextConverterFactory.factory == null) {
					TextConverterFactory.factory = new TextConverterFactory();
				}
			}
		}
		return TextConverterFactory.factory;
	}

}
