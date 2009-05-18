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

public class StringComparator implements Comparator<String> {

	private static StringComparator	comparator;

	private Collator				collator;

	private StringComparator() {
		super();
		this.collator = Collator.getInstance();
	}

	public int compare(String o1, String o2) {
		return this.collator.compare(o1, o2);
	}

	public static StringComparator getInstance() {
		if (StringComparator.comparator == null) {
			synchronized (StringComparator.class) {
				if (StringComparator.comparator == null) {
					StringComparator.comparator = new StringComparator();
				}
			}
		}
		return StringComparator.comparator;
	}

}
