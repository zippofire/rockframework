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
package net.woodstock.rockframework.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public final class StringComparator implements Comparator<String> {

	private static StringComparator	instance	= new StringComparator(Locale.getDefault());

	private Collator				collator;

	private StringComparator(final Locale locale) {
		super();
		this.collator = Collator.getInstance(locale);
	}

	public int compare(final String o1, final String o2) {
		return this.collator.compare(o1, o2);
	}

	public static StringComparator getInstance() {
		return StringComparator.instance;
	}

	public static StringComparator getInstance(final Locale locale) {
		return new StringComparator(locale);
	}

}
