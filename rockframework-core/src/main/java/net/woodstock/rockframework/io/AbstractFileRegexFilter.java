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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.woodstock.rockframework.utils.StringUtils;

abstract class AbstractFileRegexFilter implements FilenameFilter {

	private String	filter;

	public AbstractFileRegexFilter() {
		super();
	}

	public String getFilter() {
		return this.filter;
	}

	public void setFilter(final String filter) {
		this.filter = filter;
	}

	@Override
	public boolean accept(final File dir, final String name) {
		if (StringUtils.isEmpty(this.filter)) {
			return true;
		}

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

}
