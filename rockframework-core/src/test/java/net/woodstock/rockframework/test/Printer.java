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
package net.woodstock.rockframework.test;

import java.lang.reflect.Method;

public class Printer {

	private static Printer	p;

	private Printer() {
		super();
	}

	@SuppressWarnings("unused")
	private void _print(Integer i) throws Exception {
		System.out.println("Integer " + i);
	}

	@SuppressWarnings("unused")
	private void _print(Long l) throws Exception {
		System.out.println("Long " + l);
	}

	@SuppressWarnings("unused")
	private void _print(Object o) throws Exception {
		System.out.println("Object " + o);
	}

	@SuppressWarnings("unused")
	private void _print(String s) throws Exception {
		System.out.println("String " + s);
	}

	public static void print(Integer i) throws Exception {
		System.out.println("Integer X " + i);
	}

	public static void print(Object o) throws Exception {
		if (Printer.p == null) {
			Printer.p = new Printer();
		}
		Method m = null;
		try {
			m = Printer.class.getDeclaredMethod("_print", o.getClass());
		}
		catch (NoSuchMethodException e) {
			m = Printer.class.getDeclaredMethod("_print", Object.class);
		}
		m.invoke(Printer.p, o);
	}

	public static void print(String s) throws Exception {
		System.out.println("String X " + s);
	}
}
