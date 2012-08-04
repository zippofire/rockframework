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

import java.io.InputStream;
import java.util.Properties;

public abstract class PropertiesReader {

	private static final PropertiesReader	PLAIN_TEXT_INSTANCE	= new PlainPropertiesReader();

	private static final PropertiesReader	XML_INSTANCE		= new XMLPropertiesReader();

	public abstract Properties getProperties(InputStream inputStream);

	public static PropertiesReader getPlainTextInstance() {
		return PropertiesReader.PLAIN_TEXT_INSTANCE;
	}

	public static PropertiesReader getXMLInstance() {
		return PropertiesReader.XML_INSTANCE;
	}

}
