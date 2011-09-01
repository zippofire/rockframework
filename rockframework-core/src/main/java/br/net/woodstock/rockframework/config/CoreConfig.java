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

public final class CoreConfig extends AbstractConfig {

	private static final String	CONFIG_FILE	= "rockframework-core.properties";

	private static CoreConfig	instance	= new CoreConfig();

	private CoreConfig() {
		super(CoreConfig.CONFIG_FILE);
	}

	public static CoreConfig getInstance() {
		return CoreConfig.instance;
	}
}
