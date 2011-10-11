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
package br.net.woodstock.rockframework.config;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import br.net.woodstock.rockframework.collection.ImmutableMap;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ClassLoaderUtils;

public abstract class AbstractConfig {

	private Map<String, String>	config;

	public AbstractConfig(final String propertiesName) {
		super();
		Assert.notEmpty(propertiesName, "propertiesName");

		try {
			Map<String, String> tmpMap = new HashMap<String, String>();
			Collection<URL> urls = ClassLoaderUtils.getResources(propertiesName);
			if (urls != null) {
				for (URL url : urls) {
					String s = url.toString();
					if (s.startsWith(ClassLoaderUtils.JAR_PREFIX)) {
						InputStream inputStream = ClassLoaderUtils.getInputStream(url, propertiesName);
						if (inputStream != null) {
							Properties p = new Properties();
							p.load(inputStream);
							for (Object key : p.keySet()) {
								tmpMap.put(key.toString(), p.getProperty(key.toString()));
							}
						}
					}
				}
				for (URL url : urls) {
					String s = url.toString();
					if (s.startsWith(ClassLoaderUtils.FILE_PREFIX)) {
						InputStream inputStream = ClassLoaderUtils.getInputStream(url, propertiesName);
						if (inputStream != null) {
							Properties p = new Properties();
							p.load(inputStream);
							for (Object key : p.keySet()) {
								tmpMap.put(key.toString(), p.getProperty(key.toString()));
							}
						}
					}
				}
			}
			this.config = ImmutableMap.toImmutable(tmpMap);
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}

	public Collection<String> getKeys() {
		return this.config.keySet();
	}

	public Collection<String> getValues() {
		return this.config.values();
	}

	public Map<String, String> getMap() {
		return this.config;
	}

	public String getValue(final String key) {
		return this.config.get(key);
	}

	public String getValue(final String key, final String defaultValue) {
		if (this.config.containsKey(key)) {
			return this.config.get(key);
		}
		return defaultValue;
	}

}
