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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.net.woodstock.rockframework.util.DateBuilder;
import br.net.woodstock.rockframework.utils.DateUtils;
import br.net.woodstock.rockframework.utils.TimeUtils;

import junit.framework.TestCase;

public class DateTest extends TestCase {

	private Date addDays(int prazo) {
		Calendar c = Calendar.getInstance();
		long time = new Date().getTime();

		while (prazo > 0) {
			time += (1000 * 60 * 60 * 24);
			c.setTimeInMillis(time);
			int day = c.get(Calendar.DAY_OF_WEEK);
			if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY)) {
				prazo--;
			}
		}

		Date d = c.getTime();
		return d;
	}

	public void xtest1() throws Exception {
		Date date = new Date();
		System.out.println(DateUtils.format(date));
		System.out.println(TimeUtils.format(date));
	}

	public void xtest2() throws Exception {
		DateBuilder builder = new DateBuilder(new Date());
		builder.addDays(-1);
		builder.addHours(1);
		System.out.println(builder.getDate());
	}

	public void xtest3() throws Exception {
		DateBuilder builder = new DateBuilder(new Date());
		// DateFormater formater = new DateFormater("Brasilia {dd} de {MMMMM} de {yyyy}");
		builder.addDays(-1);
		builder.addHours(1);
		SimpleDateFormat format = new SimpleDateFormat("'Brasilia' dd 'de' MMMMM 'de' yyyy");
		// System.out.println(formater.format(builder.getDate()));
		System.out.println(format.format(builder.getDate()));
	}

	public void xtest4() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = format.parse("01/12/2010");
		Date d2 = format.parse("01/11/2011");

		System.out.println(DateUtils.diffDays(d1, d2));
		System.out.println(DateUtils.diffDays(d2, d1));
		System.out.println(DateUtils.diffMonths(d1, d2));
		System.out.println(DateUtils.diffMonths(d2, d1));
		System.out.println(DateUtils.diffYears(d1, d2));
		System.out.println(DateUtils.diffYears(d2, d1));
	}
	
	public void test5() throws Exception {
		Date d = this.addDays(150);
		System.out.println(d);
	}
}
