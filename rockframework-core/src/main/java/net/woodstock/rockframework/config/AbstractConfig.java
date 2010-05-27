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
package net.woodstock.rockframework.config;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.ClassLoaderUtils;

public abstract class AbstractConfig {

	private Properties	properties;

	public AbstractConfig(final String propertiesName) {
		super();
		Assert.notEmpty(propertiesName, "propertiesName");

		try {
			this.properties = new Properties();
			Collection<URL> urls = ClassLoaderUtils.getResources(propertiesName);
			if (urls != null) {
				for (URL url : urls) {
					String s = url.toString();
					if (s.startsWith(ClassLoaderUtils.JAR_PREFIX)) {
						InputStream inputStream = ClassLoaderUtils.getInputStream(url, propertiesName);
						if (inputStream != null) {
							this.properties.load(inputStream);
						}
					}
				}
				for (URL url : urls) {
					String s = url.toString();
					if (s.startsWith(ClassLoaderUtils.FILE_PREFIX)) {
						InputStream inputStream = ClassLoaderUtils.getInputStream(url, propertiesName);
						if (inputStream != null) {
							this.properties.load(inputStream);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getValue(final String key) {
		return this.properties.getProperty(key);
	}

	public String getValue(final String key, final String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}

}
