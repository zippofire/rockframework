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

import java.util.Locale;

import junit.framework.TestCase;
import net.woodstock.rockframework.utils.NumberUtils;

public class LocaleTest extends TestCase {

	public void test1() throws Exception {
		// en US 999,999,999
		// pt BR 999.999.999
		int n = 999999999;
		System.out.println(NumberUtils.format(n));
	}

	public void xtest2() throws Exception {
		System.out.println(Locale.getDefault());
	}

}
