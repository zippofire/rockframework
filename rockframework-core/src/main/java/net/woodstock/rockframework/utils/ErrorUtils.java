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

import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedHashSet;

public abstract class ErrorUtils {

	private static final String	NEW_LINE	= "\n";

	private ErrorUtils() {
		//
	}

	public static Collection<String> getStackTrace(final Throwable t) {
		StackTraceElement[] s = t.getStackTrace();
		Collection<String> list = new LinkedHashSet<String>();
		for (StackTraceElement e : s) {
			list.add(e.toString());
		}

		return list;
	}

	public static String getStackTraceString(final Throwable t) {
		StackTraceElement[] s = t.getStackTrace();
		StringBuilder buffer = new StringBuilder();
		for (StackTraceElement e : s) {
			buffer.append(e);
			buffer.append(ErrorUtils.NEW_LINE);
		}

		return buffer.toString();
	}

	public static void printStackTrace(final Throwable t, final PrintStream out) {
		String stack = ErrorUtils.getStackTraceString(t);
		out.println(stack);
	}

}
