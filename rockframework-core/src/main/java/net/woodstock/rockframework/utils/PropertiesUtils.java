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
package net.woodstock.rockframework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.woodstock.rockframework.sys.SysLogger;

public abstract class PropertiesUtils {

	private static String					path;

	private static Map<String, Properties>	m;

	private PropertiesUtils() {
		//
	}

	public static String getPropertie(String file, String key) throws InstantiationException, IOException {
		if (PropertiesUtils.path == null) {
			// TODO
			throw new InstantiationException("utils.PropertiesUtils.path-error");
		}
		if (!PropertiesUtils.m.containsKey(file)) {
			PropertiesUtils.addPropertie(file);
		}
		return PropertiesUtils.getLoadedPropertie(file, key);
	}

	private static void addPropertie(String file) throws IOException {
		File f = new File(PropertiesUtils.path + File.separator + file);
		Properties p = new Properties();
		p.load(new FileInputStream(f));
		PropertiesUtils.m.put(file, p);
	}

	private static String getLoadedPropertie(String file, String key) {
		return PropertiesUtils.m.get(file).getProperty(key);
	}

	public static void setPath(String s) {
		PropertiesUtils.path = s;
	}

	public static boolean getBoolean(Properties p, String key) {
		return Boolean.parseBoolean(p.getProperty(key));
	}

	public static byte getByte(Properties p, String key) {
		return Byte.parseByte(p.getProperty(key));
	}

	public static double getDouble(Properties p, String key) {
		return Double.parseDouble(p.getProperty(key));
	}

	public static float getFloat(Properties p, String key) {
		return Float.parseFloat(p.getProperty(key));
	}

	public static int getInt(Properties p, String key) {
		return Integer.parseInt(p.getProperty(key));
	}

	public static long getLong(Properties p, String key) {
		return Long.parseLong(p.getProperty(key));
	}

	public static Object getObject(Properties p, String key) {
		return p.get(key);
	}

	public static short getShort(Properties p, String key) {
		return Short.parseShort(p.getProperty(key));
	}

	public static String getString(Properties p, String key) {
		String s = p.getProperty(key);
		if (s != null) {
			s = s.trim();
		}
		return s;
	}

	public static boolean hasObject(Properties p, String key) {
		try {
			return p.get(key) != null;
		} catch (Exception e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
			return false;
		}
	}

	static {
		PropertiesUtils.m = new HashMap<String, Properties>();
	}

}
