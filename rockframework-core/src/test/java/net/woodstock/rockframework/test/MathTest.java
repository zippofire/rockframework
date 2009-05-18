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

import net.woodstock.rockframework.utils.MathUtils;

public class MathTest {

	public static void main(String[] args) {
		/*
		 * float angle = 180; System.out.println(angle); System.out.println(Math.cos(angle));
		 * System.out.println(Math.sin(angle)); System.out.println(Math.toRadians(angle));
		 * System.out.println(Math.toDegrees(Math.toRadians(angle)));
		 * System.out.println(Math.cos(Math.toRadians(angle)));
		 */

		System.out.println(MathUtils.root(10000, 4));
		System.out.println(MathUtils.root(10000.0, 4.0));
		System.out.println(Math.pow(10.0, 4.0));

		System.out.println(MathUtils.root(10.0, 0));
	}

}
