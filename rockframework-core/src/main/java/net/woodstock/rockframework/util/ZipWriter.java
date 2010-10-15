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
package net.woodstock.rockframework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipWriter {

	public static final String	SEPARATOR	= "/";

	private Set<File>			files;

	public ZipWriter() {
		this.files = new LinkedHashSet<File>(0);
	}

	public void add(final File... files) {
		for (File f : files) {
			this.files.add(f);
		}
	}

	public void add(final String... files) {
		for (String s : files) {
			File f = new File(s);
			this.files.add(f);
		}
	}

	public void del(final File... files) {
		for (File f : files) {
			this.files.remove(f);
		}
	}

	public void del(final String... files) {
		for (String s : files) {
			for (File d : this.files) {
				if (d.getName().equals(s)) {
					this.files.remove(d);
				}
			}
		}
	}

	public Collection<File> list() {
		Collection<File> list = new LinkedHashSet<File>();
		list.addAll(this.files);
		return list;
	}

	public void save(final String fileName) throws IOException {
		this.save(new File(fileName));
	}

	public void save(final File file) throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
		for (File f : this.files) {
			if (f.isDirectory()) {
				ZipWriter.addDir(out, f);
			} else if (f.isFile()) {
				ZipWriter.addFile(out, f);
			}
		}
		out.close();
	}

	private static void addDir(final ZipOutputStream out, final File dir) throws IOException {
		ZipWriter.addDir(out, null, dir);
	}

	private static void addFile(final ZipOutputStream out, final File file) throws IOException {
		ZipWriter.addFile(out, null, file);
	}

	private static void addDir(final ZipOutputStream out, final File parent, final File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String name = (parent != null ? parent.getName() + ZipWriter.SEPARATOR : "") + dir.getName() + ZipWriter.SEPARATOR;
				ZipEntry entry = new ZipEntry(name);
				out.putNextEntry(entry);
				ZipWriter.addDir(out, dir, f);
			} else if (f.isFile()) {
				ZipWriter.addFile(out, dir, f);
			}
		}
	}

	private static void addFile(final ZipOutputStream out, final File parent, final File file) throws IOException {
		FileInputStream input = new FileInputStream(file);
		String name = (parent != null ? parent.getName() + ZipWriter.SEPARATOR : "") + file.getName();
		out.putNextEntry(new ZipEntry(name));
		byte[] buf = new byte[1024];
		int len = input.read(buf);
		while (len > 0) {
			out.write(buf, 0, len);
			len = input.read(buf);
		}
		out.closeEntry();
		input.close();
	}

}
