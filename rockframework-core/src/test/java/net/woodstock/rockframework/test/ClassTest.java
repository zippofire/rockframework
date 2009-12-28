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

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.ClassUtils;

public class ClassTest extends TestCase {

	public void print(final CharSequence s) {
		System.out.println("CharSequence: " + s);
	}

	public void test1() throws Exception {
		ClassTest test = new ClassTest();
		Method m = ClassUtils.getMethod(ClassTest.class, "print", new Class[] { String.class });
		m.invoke(test, "Test");

		m = ClassUtils.getMethod(ClassTest.class, "toString", new Class[] {});
		System.out.println(m.invoke(test, new Object[] {}));
	}

}
