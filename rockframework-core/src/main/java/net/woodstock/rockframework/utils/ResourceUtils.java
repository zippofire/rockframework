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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.woodstock.rockframework.sys.SysLogger;

public abstract class ResourceUtils {

	private static Map<String, ResourceBundle>	m;

	private ResourceUtils() {
		//
	}

	public static boolean getBoolean(String file, String key) throws InstantiationException {
		return Boolean.parseBoolean(ResourceUtils.getString(file, key));
	}

	public static int getInt(String file, String key) throws InstantiationException {
		return Integer.parseInt(ResourceUtils.getString(file, key));
	}

	public static long getLong(String file, String key) throws InstantiationException {
		return Long.parseLong(ResourceUtils.getString(file, key));
	}

	public static float getFloat(String file, String key) throws InstantiationException {
		return Float.parseFloat(ResourceUtils.getString(file, key));
	}

	public static double geDouble(String file, String key) throws InstantiationException {
		return Double.parseDouble(ResourceUtils.getString(file, key));
	}

	public static String getString(String file, String key) throws InstantiationException {
		ResourceUtils.verify(file);
		String s = null;
		try {
			s = ResourceUtils.m.get(file).getString(key).trim();
		} catch (NullPointerException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		} catch (MissingResourceException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		} catch (ClassCastException e) {
			throw e;
		}
		return s;
	}

	public static Object getObject(String file, String key) throws InstantiationException {
		ResourceUtils.verify(file);
		Object o = null;
		try {
			o = ResourceUtils.m.get(file).getObject(key);
		} catch (NullPointerException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		} catch (MissingResourceException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		}
		return o;
	}

	public static String[] getArrayString(String file, String key) throws InstantiationException {
		ResourceUtils.verify(file);
		String[] s = null;
		try {
			s = ResourceUtils.m.get(file).getStringArray(key);
		} catch (NullPointerException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		} catch (MissingResourceException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		} catch (ClassCastException e) {
			throw e;
		}
		return s;
	}

	public static Enumeration<?> getKeys(String file) throws InstantiationException {
		ResourceUtils.verify(file);
		return ResourceUtils.m.get(file).getKeys();
	}

	public static Locale getLocale(String file) throws InstantiationException {
		ResourceUtils.verify(file);
		return ResourceUtils.m.get(file).getLocale();
	}

	public static boolean exists(String file) {
		boolean exist = false;
		try {
			ResourceBundle.getBundle(file);
			exist = true;
		} catch (MissingResourceException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		}
		return exist;
	}

	public static boolean hasObject(String file, String key) throws InstantiationException {
		ResourceUtils.verify(file);
		try {
			return ResourceUtils.m.get(file).getObject(key) != null;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean getBoolean(ResourceBundle rb, String key) {
		return Boolean.parseBoolean(rb.getString(key));
	}

	public static byte getByte(ResourceBundle rb, String key) {
		return Byte.parseByte(rb.getString(key));
	}

	public static double getDouble(ResourceBundle rb, String key) {
		return Double.parseDouble(rb.getString(key));
	}

	public static float getFloat(ResourceBundle rb, String key) {
		return Float.parseFloat(rb.getString(key));
	}

	public static int getInt(ResourceBundle rb, String key) {
		return Integer.parseInt(rb.getString(key));
	}

	public static long getLong(ResourceBundle rb, String key) {
		return Long.parseLong(rb.getString(key));
	}

	public static Object getObject(ResourceBundle rb, String key) {
		return rb.getObject(key);
	}

	public static short getShort(ResourceBundle rb, String key) {
		return Short.parseShort(rb.getString(key));
	}

	public static String getString(ResourceBundle rb, String key) {
		String s = rb.getString(key);
		if (s != null) {
			s = s.trim();
		}
		return s;
	}

	public static boolean hasObject(ResourceBundle rb, String key) {
		try {
			return rb.getObject(key) != null;
		} catch (Exception e) {
			return false;
		}
	}

	private static void verify(String file) throws InstantiationException {
		if (ResourceUtils.m == null) {
			// TODO
			throw new InstantiationException("utils.ResourceUtils.start-error");
		}
		if (!ResourceUtils.m.containsKey(file)) {
			ResourceUtils.addResource(file);
		}
	}

	private static void addResource(String file) {
		ResourceBundle r = ResourceBundle.getBundle(file);
		ResourceUtils.m.put(file, r);
	}

	static {
		ResourceUtils.m = new HashMap<String, ResourceBundle>();
	}

}
