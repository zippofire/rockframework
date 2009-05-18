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

import net.woodstock.rockframework.utils.StringUtils;

public class PrinterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(StringUtils.format("####-####", "12345678"));
		// System.out.println(StringUtils.format("###.###.###-##", "86216503120"));
		//
		// System.out.println(StringUtils.unformat("####-####", "1234-5678"));
		// System.out.println(StringUtils.unformat("###.###.###-##", "862.165.031-20"));

		System.out.println(StringUtils.format("(##)####-####", "6112345678"));
		System.out.println(StringUtils.unformat("(##)####-####", "(61)1234-5678"));

	}

}
