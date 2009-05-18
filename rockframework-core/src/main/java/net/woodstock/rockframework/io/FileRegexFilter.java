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
package net.woodstock.rockframework.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileRegexFilter implements FilenameFilter {

	private String	filter;

	public FileRegexFilter(String filter) {
		this.filter = filter;
	}

	public String getFilter() {
		return this.filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public boolean accept(File dir, String name) {
		File f = new File(dir.getAbsolutePath() + File.separator + name);
		if (f.isDirectory()) {
			return true;
		}

		Pattern pattern = Pattern.compile(this.filter);
		Matcher matcher = pattern.matcher(name);

		return matcher.find();
	}

	@Override
	public String toString() {
		return this.filter;
	}

	public static FileRegexFilter createExtensionFilter(String... items) {
		return FileRegexFilter.createExtensionFilter(Arrays.asList(items));
	}

	public static FileRegexFilter createExtensionFilter(Iterable<String> items) {
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
			first = false;
		}
		b.append(")");
		return new FileRegexFilter(b.toString());
	}

}
