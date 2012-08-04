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
package br.net.woodstock.rockframework.io;

import java.util.Arrays;
import java.util.Collection;

public class FileExtensionFilter extends AbstractFileRegexFilter {

	public FileExtensionFilter(final String... items) {
		this(Arrays.asList(items));
	}

	public FileExtensionFilter(final Collection<String> items) {
		super();
		StringBuilder b = new StringBuilder();
		boolean first = true;
		b.append("\\.(");
		for (String s : items) {
			if (!first) {
				b.append("|");
			}
			for (char c : s.toCharArray()) {
				b.append("[");
				b.append(Character.toLowerCase(c));
				b.append(Character.toUpperCase(c));
				b.append("]");
			}
			if (first) {
				first = false;
			}
		}
		b.append(")");
		this.setFilter(b.toString());
	}

}
