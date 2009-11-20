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
package net.woodstock.rockframework.web.config;

import java.io.IOException;
import java.net.URISyntaxException;

import net.woodstock.rockframework.config.AbstractConfig;

public class WebConfig extends AbstractConfig {

	private static final String	CONFIG_FILE	= "rockframework-web.properties";

	private static WebConfig	config;

	private WebConfig() throws URISyntaxException, IOException {
		super(WebConfig.CONFIG_FILE);
	}

	public static WebConfig getInstance() {
		if (WebConfig.config == null) {
			synchronized (WebConfig.class) {
				if (WebConfig.config == null) {
					try {
						WebConfig.config = new WebConfig();
					} catch (URISyntaxException e) {
						throw new RuntimeException(e);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return WebConfig.config;
	}
}
