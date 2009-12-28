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
package net.woodstock.rockframework.conversion.xml;

import net.woodstock.rockframework.conversion.Converter;
import net.woodstock.rockframework.conversion.ConverterFactory;
import net.woodstock.rockframework.xml.dom.XmlElement;

public final class XmlConverterFactory implements ConverterFactory<XmlElement, Object> {

	private static XmlConverterFactory		instance	= new XmlConverterFactory();

	private Converter<XmlElement, Object>	converter;

	private XmlConverterFactory() {
		super();
		this.converter = new BeanConverter();
	}

	public Converter<XmlElement, Object> getConverter() {
		return this.converter;
	}

	public static XmlConverterFactory getInstance() {
		return XmlConverterFactory.instance;
	}

}
