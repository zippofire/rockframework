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
package br.net.woodstock.rockframework.text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import br.net.woodstock.rockframework.InitializationException;
import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.utils.ClassLoaderUtils;

public final class StringFormatTemplate {

	private static final String			TEMPLATE_FILE	= "string-format.properties";

	private static final String			VALUE_SEPARATOR	= ";";

	private static StringFormatTemplate	instance		= new StringFormatTemplate();

	private Map<String, StringFormat>	map;

	private StringFormatTemplate() {
		super();
		this.map = new HashMap<String, StringFormat>();
		try {
			this.init();
		} catch (Exception e) {
			throw new InitializationException(e);
		}
	}

	private void init() throws IOException, URISyntaxException {
		Properties properties = new Properties();
		InputStream inputStream = ClassLoaderUtils.getResourceAsStream(StringFormatTemplate.TEMPLATE_FILE);
		properties.load(inputStream);
		for (java.util.Map.Entry<Object, Object> entry : properties.entrySet()) {
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			String[] array = value.split(StringFormatTemplate.VALUE_SEPARATOR);

			char character = StringFormat.DEFAULT_CHARACTER;
			String pattern = null;
			boolean ok = false;

			if (array.length == 1) {
				pattern = array[0].trim();
				ok = true;
			} else if (array.length == 2) {
				character = array[0].trim().charAt(0);
				pattern = array[1].trim();
				ok = true;
			}

			if (ok) {
				CoreLog.getInstance().getLogger().info("Inserting template " + key + " = " + value);
				StringFormat format = new StringFormat(pattern, character);
				this.map.put(key, format);
			} else {
				CoreLog.getInstance().getLogger().debug("Invalid template " + key + " = " + value);
			}

		}
	}

	public StringFormat getFormat(final String template) {
		return this.map.get(template);
	}

	public static StringFormatTemplate getInstance() {
		return instance;
	}

}
