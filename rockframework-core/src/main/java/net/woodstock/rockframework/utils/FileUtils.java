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
package net.woodstock.rockframework.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

public abstract class FileUtils {

	public static final char	UNIX_FILE_SEPARATOR		= '/';

	public static final char	WINDOWS_FILE_SEPARATOR	= '\\';

	public static final char	EXTENSION_SEPARATOR		= '.';

	private FileUtils() {
		//
	}

	public static int getContentLength(final File file) throws IOException {
		URLConnection con = file.toURI().toURL().openConnection();
		return con.getContentLength();
	}

	public static String getContentType(final File file) throws IOException {
		URLConnection con = file.toURI().toURL().openConnection();
		return con.getContentType();
	}

	public static String getFileName(final String src) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		if (src.indexOf(FileUtils.UNIX_FILE_SEPARATOR) != -1) {
			int index = src.lastIndexOf(FileUtils.UNIX_FILE_SEPARATOR);
			String fileName = src.substring(index + 1);
			return fileName;
		} else if (src.indexOf(FileUtils.WINDOWS_FILE_SEPARATOR) != -1) {
			int index = src.lastIndexOf(FileUtils.WINDOWS_FILE_SEPARATOR);
			String fileName = src.substring(index + 1);
			return fileName;
		}
		return src;
	}

	public static String getFileName(final File file) {
		return file.getName();
	}

	public static String getFileExtension(final String src) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		String fileName = getFileName(src);
		if (fileName != null) {
			if (fileName.indexOf('.') != -1) {
				return fileName.substring(fileName.lastIndexOf('.') + 1);
			}
		}
		return null;
	}

	public static String getFileExtension(final File file) {
		String fileName = file.getName();
		if (fileName.indexOf('.') != -1) {
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		return null;
	}

}
