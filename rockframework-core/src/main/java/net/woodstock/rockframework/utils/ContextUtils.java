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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

import net.woodstock.rockframework.logging.SysLogger;

public abstract class ContextUtils {

	private static Context	c;

	private ContextUtils() {
		//
	}

	public static Object getContextObject(final String name) throws NamingException {
		return ContextUtils.getContextObject(ContextUtils.c, name);
	}

	public static Object getContextObject(final Context c, final String name) throws NamingException {
		Object o = null;
		if (c == null) {
			throw new IllegalArgumentException("Context must be not null");
		}
		try {
			o = c.lookup(name);
		} catch (NoInitialContextException e) {
			SysLogger.getLogger().warn(e.getMessage(), e);
		}
		return o;
	}

	public static String getContextString(final String name) throws NamingException {
		return (String) ContextUtils.getContextObject(ContextUtils.c, name);
	}

	public static String getContextString(final Context c, final String name) throws NamingException {
		return (String) ContextUtils.getContextObject(c, name);
	}

	public static void setContextObject(final String name, final Object o) throws NamingException {
		ContextUtils.setContextObject(ContextUtils.c, name, o);
	}

	public static void setContextObject(final Context c, final String name, final Object o) throws NamingException {
		c.addToEnvironment(name, o);
	}

	static {
		try {
			ContextUtils.c = new InitialContext();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
