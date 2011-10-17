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
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import br.net.woodstock.rockframework.utils.ClassLoaderUtils;

public final class StringFormatterTemplate {

	private static final String				TEMPLATE_FILE	= "string-formatter.properties";

	private static StringFormatterTemplate	instance		= new StringFormatterTemplate();

	private Map<String, StringFormater>		map;

	private StringFormatterTemplate() {
		super();
		this.map = new HashMap<String, StringFormater>();
	}

	private void init() throws IOException, URISyntaxException {
		Properties properties = new Properties();
		InputStream inputStream = ClassLoaderUtils.getResourceAsStream(StringFormatterTemplate.TEMPLATE_FILE);
		properties.load(inputStream);
		for (java.util.Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
		}
	}

	public StringFormater getFormatter(final String template) {
		return this.map.get(template);
	}

	public static StringFormatterTemplate getInstance() {
		return instance;
	}

}
