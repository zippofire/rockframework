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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipReader {

	private Set<String>	files;

	private File		file;

	public ZipReader(final String fileName) throws IOException {
		this(new File(fileName));
	}

	public ZipReader(final File file) throws IOException {
		super();
		this.file = file;
		this.files = new LinkedHashSet<String>(0);
		this.read();
	}

	public byte[] getBytes(final String file) throws IOException {
		if (this.files.contains(file)) {
			ZipInputStream input = new ZipInputStream(new FileInputStream(this.file));
			ZipEntry entry = input.getNextEntry();
			while (entry != null) {
				entry = input.getNextEntry();
				if (entry.getName().equals(file)) {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					int b = -1;
					do {
						b = input.read();
						if (b != -1) {
							output.write(b);
						}
					} while (b != -1);
					byte[] bytes = output.toByteArray();
					output.close();
					input.close();
					return bytes;
				}
			}
			input.close();
		}
		return new byte[0];
	}

	public File getFile(final String file, final File outFile) throws IOException {
		if (this.files.contains(file)) {
			ZipInputStream input = new ZipInputStream(new FileInputStream(this.file));
			ZipEntry entry = input.getNextEntry();
			while (entry != null) {
				entry = input.getNextEntry();
				if (entry.getName().equals(file)) {
					FileOutputStream output = new FileOutputStream(outFile);
					int b = -1;
					do {
						b = input.read();
						if (b != -1) {
							output.write(b);
						}
					} while (b != -1);
					output.close();
					break;
				}
			}
			input.close();
		}
		return outFile;
	}

	public File getFile(final String file, final String outFile) throws IOException {
		return this.getFile(file, new File(outFile));
	}

	public void unzipFiles(final File outDir, final String... files) throws IOException {
		Collection<String> fileList = new LinkedHashSet<String>();
		for (String file : files) {
			fileList.add(file);
		}
		ZipInputStream input = new ZipInputStream(new FileInputStream(this.file));
		ZipEntry entry = input.getNextEntry();
		while (entry != null) {
			entry = input.getNextEntry();
			if (fileList.contains(entry.getName())) {
				FileOutputStream output = new FileOutputStream(outDir.getAbsolutePath() + File.separator + entry.getName());
				int b = -1;
				do {
					b = input.read();
					if (b != -1) {
						output.write(b);
					}
				} while (b != -1);
				output.close();
			}
		}
		input.close();
	}

	public void unzipFiles(final String outDir, final String... files) throws IOException {
		this.unzipFiles(new File(outDir), files);
	}

	public void unzipAll(final File outDir) throws IOException {
		ZipInputStream input = new ZipInputStream(new FileInputStream(this.file));
		ZipEntry entry = input.getNextEntry();
		while (entry != null) {
			entry = input.getNextEntry();
			FileOutputStream output = new FileOutputStream(outDir.getAbsolutePath() + File.separator + entry.getName());
			int b = -1;
			do {
				b = input.read();
				if (b != -1) {
					output.write(b);
				}
			} while (b != -1);
			output.close();
		}
		input.close();
	}

	public void unzipAll(final String outDir) throws IOException {
		this.unzipAll(new File(outDir));
	}

	public Set<String> list() {
		return this.files;
	}

	private void read() throws IOException {
		ZipInputStream input = new ZipInputStream(new FileInputStream(this.file));
		ZipEntry entry = input.getNextEntry();
		while (entry != null) {
			this.files.add(entry.getName());
			entry = input.getNextEntry();
		}
		input.close();
	}
}
