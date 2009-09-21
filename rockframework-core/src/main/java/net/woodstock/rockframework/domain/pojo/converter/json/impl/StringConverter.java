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
package net.woodstock.rockframework.domain.pojo.converter.json.impl;

import java.util.Collection;
import java.util.LinkedList;

import net.woodstock.rockframework.reflection.PropertyDescriptor;

class StringConverter extends net.woodstock.rockframework.domain.pojo.converter.common.impl.StringConverter {

	private Collection<String[]>	replacement;

	public StringConverter() {
		super();
		this.replacement = new LinkedList<String[]>();
		this.replacement.add(new String[] { "\n", "\\n" });
		this.replacement.add(new String[] { "\r", "\\r" });
		this.replacement.add(new String[] { "\t", "\\t" });
		this.replacement.add(new String[] { "'", "\\'" });
		this.replacement.add(new String[] { "\"", "\\\"" });
	}

	@Override
	public String toText(String s, PropertyDescriptor propertyDescriptor) {
		StringBuilder builder = new StringBuilder();
		String ss = super.toText(s, propertyDescriptor);
		if (ss != null) {
			builder.append('\'');
			builder.append(this.addEscape(ss));
			builder.append('\'');
		} else {
			builder.append("null");
		}
		return builder.toString();
	}

	private String addEscape(String str) {
		if (str == null) {
			return null;
		}
		for (String[] s : this.replacement) {
			str = str.replaceAll(s[0], s[1]);
		}

		return str;
	}

}
