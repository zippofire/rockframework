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

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.ClassLoaderUtils;
import net.woodstock.rockframework.utils.StringUtils;

import org.apache.commons.logging.Log;

public abstract class AbstractConfig {

	private Properties	properties;

	public AbstractConfig(String propertiesName) throws URISyntaxException, IOException {
		super();
		if (StringUtils.isEmpty(propertiesName)) {
			throw new IllegalArgumentException("Properties name must be not empty");
		}
		this.properties = new Properties();
		Collection<URL> urls = ClassLoaderUtils.getResources(propertiesName);
		if (urls != null) {
			for (URL url : urls) {
				String s = url.toString();
				if (s.startsWith(ClassLoaderUtils.JAR_PREFIX)) {
					InputStream inputStream = ClassLoaderUtils.getInputStream(url, propertiesName);
					if (inputStream != null) {
						this.getLogger().info("Load properties " + propertiesName + " from JAR " + url);
						this.properties.load(inputStream);
					}
				}
			}
			for (URL url : urls) {
				String s = url.toString();
				if (s.startsWith(ClassLoaderUtils.FILE_PREFIX)) {
					InputStream inputStream = ClassLoaderUtils.getInputStream(url, propertiesName);
					if (inputStream != null) {
						this.getLogger().info("Load properties " + propertiesName + " from FILE " + url);
						this.properties.load(inputStream);
					}
				}
			}
		}
	}

	public String getValue(String key) {
		return this.properties.getProperty(key);
	}

	public String getValue(String key, String defaultValue) {
		return this.properties.getProperty(key, defaultValue);
	}

	// Logger
	protected Log getLogger() {
		return SysLogger.getLogger();
	}

}
