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
package net.woodstock.rockframework.conversion.json.converters;

import java.util.Collection;
import java.util.LinkedList;

abstract class JsonConverterHelper {

	private static Collection<String[]>	replacement;

	private JsonConverterHelper() {
		//
	}

	public static String addEscape(String str) {
		if (JsonConverterHelper.replacement == null) {
			JsonConverterHelper.replacement = new LinkedList<String[]>();
			JsonConverterHelper.replacement.add(new String[] { "\n", "\\n" });
			JsonConverterHelper.replacement.add(new String[] { "\r", "\\r" });
			JsonConverterHelper.replacement.add(new String[] { "\t", "\\t" });
			JsonConverterHelper.replacement.add(new String[] { "'", "\\'" });
			JsonConverterHelper.replacement.add(new String[] { "\"", "\\\"" });
		}
		if (str == null) {
			return null;
		}
		for (String[] s : JsonConverterHelper.replacement) {
			str = str.replaceAll(s[0], s[1]);
		}

		return str;
	}

}
