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
package br.net.woodstock.rockframework.test;

import junit.framework.TestCase;

public class TypeTest extends TestCase {

	static void print(final Integer i) {
		System.out.println("Integer " + i);
	}

	static void print(final String s) {
		System.out.println("String " + s);
	}

	static void print(final Object o) {
		System.out.println("Object " + o);
	}

	public void test1() throws Exception {
		Object o1 = new Integer(5);
		Object o2 = new String("Teste");

		TypeTest.print(o1);
		TypeTest.print(o2);
	}

}
