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

import java.io.File;
import java.io.FileFilter;

public class FileTypeFilter implements FileFilter {

	private FileType	type;

	public FileTypeFilter(final FileType type) {
		this.type = type;
	}

	public FileType getType() {
		return this.type;
	}

	public void setType(final FileType type) {
		this.type = type;
	}

	@Override
	public boolean accept(final File pathname) {
		if ((this.type == FileType.FILE) && (pathname.isFile())) {
			return true;
		}
		if ((this.type == FileType.DIRECTORY) && (pathname.isDirectory())) {
			return true;
		}
		return false;
	}

	public static enum FileType {
		DIRECTORY, FILE;
	}

}
