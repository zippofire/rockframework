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

	public static int getContentLength(String src) throws IOException {
		return FileUtils.getContentLength(new File(src));
	}

	public static int getContentLength(File file) throws IOException {
		URLConnection con = file.toURI().toURL().openConnection();
		return con.getContentLength();
	}

	public static String getContentType(String src) throws IOException {
		return FileUtils.getContentType(new File(src));
	}

	public static String getContentType(File file) throws IOException {
		URLConnection con = file.toURI().toURL().openConnection();
		return con.getContentType();
	}

	public static String getFileName(String src) {
		return FileUtils.getFileName(new File(src));
	}

	public static String getFileName(File file) {
		return file.getName();
	}

	public static String getFileExtension(String src) {
		return FileUtils.getFileExtension(new File(src));
	}

	public static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.indexOf('.') != -1) {
			return fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		return "";
	}

	public static int lineCount(String src) throws IOException {
		return FileUtils.lineCount(new File(src));
	}

	public static int lineCount(File file) throws IOException {
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

	public static File mkdir(String dir) throws IOException {
		File f = new File(dir);
		if (!f.exists()) {
			f.mkdir();
		} else if ((f.exists()) && (!f.isDirectory())) {
			// TODO
			throw new IOException("utils.FileUtils.file-exists");
		}
		return f;
	}

	public static File mv(String src, String dst) throws IOException {
		return FileUtils.mv(new File(src), new File(dst));
	}

	public static File mv(File src, File dst) throws IOException {
		if (!src.renameTo(dst)) {
			// TODO
			throw new IOException("utils.FileUtils.rename-error");
		}
		return dst;
	}

	public static String pwd() {
		return FileUtils.pwd(".");
	}

	public static String pwd(String s) {
		File f = new File(s);
		return f.getAbsolutePath();
	}

	public static boolean rm(String file) throws IOException {
		File f = new File(file);
		if ((f.exists()) && (f.isFile())) {
			if (!f.delete()) {
				// TODO
				throw new IOException("utils.FileUtils.delete-error");
			}
			return true;
		} else if (f.exists()) {
			// TODO
			throw new IOException("utils.FileUtils.invalid-file");
		}
		return false;
	}

	public static boolean rmdir(String dir) throws IOException {
		File f = new File(dir);
		if ((f.exists()) && (f.isDirectory())) {
			if (!f.delete()) {
				// TODO
				throw new IOException("utils.FileUtils.rmdir-error");
			}
			return true;
		} else if (f.exists()) {
			// TODO
			throw new IOException("utils.FileUtils.invalid-dir");
		}
		return false;
	}

	public static File touch(String file) throws IOException {
		return FileUtils.touch(file, false);
	}

	public static File touch(String file, boolean override) throws IOException {
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
