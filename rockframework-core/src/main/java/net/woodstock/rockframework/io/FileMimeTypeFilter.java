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
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import net.woodstock.rockframework.utils.FileUtils;

public class FileMimeTypeFilter implements FilenameFilter {

	private Collection<String>	types;

	public FileMimeTypeFilter(String... items) {
		super();
		this.types = Arrays.asList(items);
	}

	public FileMimeTypeFilter(Collection<String> types) {
		super();
		this.types = types;
	}

	@Override
	public boolean accept(File dir, String name) {
		if ((this.types == null) || (this.types.size() == 0)) {
			return true;
		}

		File f = new File(dir.getAbsolutePath() + File.separator + name);
		if (f.isDirectory()) {
			return true;
		}

		try {
			String mimeType = FileUtils.getContentType(f);
			return this.types.contains(mimeType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
