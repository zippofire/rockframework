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
import java.util.Scanner;

public abstract class FileUtils {

	private FileUtils() {
		//
	}

	public static int getContentLength(final String src) throws IOException {
		return FileUtils.getContentLength(new File(src));
	}

	public static int getContentLength(final File file) throws IOException {
		URLConnection con = file.toURI().toURL().openConnection();
		return con.getContentLength();
	}

	public static String getContentType(final String src) throws IOException {
		return FileUtils.getContentType(new File(src));
	}

	public static String getContentType(final File file) throws IOException {
		URLConnection con = file.toURI().toURL().openConnection();
		return con.getContentType();
	}

	public static String getFileName(final String src) {
		return FileUtils.getFileName(new File(src));
	}

	public static String getFileName(final File file) {
		return file.getName();
	}

	public static String getFileExtension(final String src) {
		return FileUtils.getFileExtension(new File(src));
	}

	public static String getFileExtension(final File file) {
		String fileName = file.getName();
		if (fileName.indexOf('.') != -1) {
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		return StringUtils.BLANK;
	}

	public static int lineCount(final String src) throws IOException {
		return FileUtils.lineCount(new File(src));
	}

	public static int lineCount(final File file) throws IOException {
		Scanner scanner = new Scanner(file);
		int lines = 0;
		while (scanner.hasNextLine()) {
			if (!((scanner.nextLine().trim().length() == 0) && (!scanner.hasNextLine()))) {
				lines++;
			}
		}
		scanner.close();
		return lines;
	}

	public static File mkdir(final String dir) throws IOException {
		File f = new File(dir);
		if (!f.exists()) {
			f.mkdir();
		} else if ((f.exists()) && (!f.isDirectory())) {
			throw new IOException("A file with name " + dir + " already exists");
		}
		return f;
	}

	public static File mv(final String src, final String dst) throws IOException {
		return FileUtils.mv(new File(src), new File(dst));
	}

	public static File mv(final File src, final File dst) throws IOException {
		if (!src.renameTo(dst)) {
			throw new IOException("File cannot be moved");
		}
		return dst;
	}

	public static String pwd() {
		return FileUtils.pwd(".");
	}

	public static String pwd(final String s) {
		File f = new File(s);
		return f.getAbsolutePath();
	}

	public static boolean rm(final String file) throws IOException {
		File f = new File(file);
		if ((f.exists()) && (f.isFile())) {
			if (!f.delete()) {
				throw new IOException("File cannot be deleted");
			}
			return true;
		} else if (f.exists()) {
			throw new IOException("Cannot delete directory");
		}
		return false;
	}

	public static boolean rmdir(final String dir) throws IOException {
		File f = new File(dir);
		if ((f.exists()) && (f.isDirectory())) {
			if (!f.delete()) {
				throw new IOException("Directory cannot be deleted");
			}
			return true;
		} else if (f.exists()) {
			throw new IOException("Cannot delete directory");
		}
		return false;
	}

	public static File touch(final String file) throws IOException {
		return FileUtils.touch(file, false);
	}

	public static File touch(final String file, final boolean override) throws IOException {
		File f = new File(file);
		if ((override) && (f.exists())) {
			f.delete();
			f.createNewFile();
		}
		if (!f.exists()) {
			f.createNewFile();
		}
		return f;
	}

}
