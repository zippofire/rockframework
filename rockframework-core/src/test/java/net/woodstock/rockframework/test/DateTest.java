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

import java.util.Date;
import java.util.Locale;

import junit.framework.TestCase;
import net.woodstock.rockframework.util.Calendar;
import net.woodstock.rockframework.utils.DateUtils;
import net.woodstock.rockframework.utils.TimeUtils;

public class DateTest extends TestCase {

	public static void add() {
		Calendar d = Calendar.getInstance();
		System.out.println(d);
		// d.addYears(5);
		// System.out.println(d);
		// d.addMonths(30);
		// System.out.println(d);
		// d.addDays(1000);
		// System.out.println(d);
		d.addHours(1000);
		System.out.println(d);
	}

	public static void remove() {
		Calendar d = Calendar.getInstance();
		System.out.println(d);
		// d.removeYears(5);
		// System.out.println(d);
		// d.removeMonths(30);
		// System.out.println(d);
		d.removeDays(1000);
		System.out.println(d);
		// d.removeHours(1000);
		// System.out.println(d);
	}

	public static void diff() {
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d2.set(java.util.Calendar.YEAR, 2007);
		d2.set(java.util.Calendar.MONTH, 0);
		d2.set(java.util.Calendar.DAY_OF_MONTH, 5);
		d2.set(java.util.Calendar.HOUR_OF_DAY, 13);
		d2.set(java.util.Calendar.MINUTE, 25);

		System.out.println(d1);
		System.out.println(d2);

		System.out.println("Anos : " + d1.diffYears(d2));
		System.out.println("Meses: " + d1.diffMonths(d2));
		System.out.println("Dias : " + d1.diffDays(d2));
		System.out.println("Horas: " + d1.diffHours(d2));
	}

	public static void format() {
		Calendar d1 = Calendar.getInstance(new Locale("pt", "BR"));
		Calendar d2 = Calendar.getInstance(new Locale("es", "AR"));

		System.out.println(d1.getFormated("Hoje e " + Calendar.DAY_NAME_FORMAT));
		System.out.println(d2.getFormated("Hoje e " + Calendar.DAY_NAME_FORMAT));
	}

	public void test1() throws Exception {
		Date date = new Date();
		System.out.println(DateUtils.format(date));
		System.out.println(TimeUtils.format(date));
	}
}
