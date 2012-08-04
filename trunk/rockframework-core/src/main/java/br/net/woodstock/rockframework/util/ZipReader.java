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
package br.net.woodstock.rockframework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import br.net.woodstock.rockframework.collection.ImmutableSet;
import br.net.woodstock.rockframework.utils.IOUtils;

public class ZipReader {

	private static final char	DIR_SEPARATOR	= '/';

	private Set<String>			files;

	private byte[]				bytes;

	public ZipReader(final String fileName) throws IOException {
		this(new File(fileName));
	}

	public ZipReader(final File file) throws IOException {
		this(new FileInputStream(file));
	}

	public ZipReader(final InputStream inputStream) throws IOException {
		super();
		this.files = new LinkedHashSet<String>(0);
		this.bytes = IOUtils.toByteArray(inputStream);
		this.readMetadata();
	}

	public Collection<String> getFiles() {
		return new ImmutableSet<String>(this.files);
	}

	private void readMetadata() throws IOException {
		ZipInputStream input = new ZipInputStream(new ByteArrayInputStream(this.bytes));
		ZipEntry entry = input.getNextEntry();
		while (entry != null) {
			this.files.add(entry.getName());
			entry = input.getNextEntry();
		}
		input.close();
	}

	public byte[] getFile(final String file) throws IOException {
		if (this.files.contains(file)) {
			ZipInputStream input = new ZipInputStream(new ByteArrayInputStream(this.bytes));
			ZipEntry entry = input.getNextEntry();
			while (entry != null) {
				entry = input.getNextEntry();
				if ((entry.getName().equals(file)) && (!entry.isDirectory())) {
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					IOUtils.copy(input, output);
					output.close();
					input.close();
					return output.toByteArray();
				}
			}
			input.close();
		}
		return null;
	}

	public void unzip(final File outDir) throws IOException {
		ZipInputStream input = new ZipInputStream(new ByteArrayInputStream(this.bytes));
		ZipEntry entry = input.getNextEntry();
		while (entry != null) {
			entry = input.getNextEntry();
			if (entry != null) {
				File file = this.createFile(outDir, entry.getName());
				if (!entry.isDirectory()) {
					FileOutputStream output = new FileOutputStream(file);
					IOUtils.copy(input, output);
					output.close();
				}
			}
		}
		input.close();
	}

	private File createFile(final File outDir, final String name) {
		if (name.indexOf(ZipReader.DIR_SEPARATOR) != -1) {
			String parent = name.substring(0, name.indexOf(ZipReader.DIR_SEPARATOR));
			String newName = name.substring(name.indexOf(ZipReader.DIR_SEPARATOR) + 1);
			File newOutDir = new File(outDir, parent);
			if (!newOutDir.exists()) {
				newOutDir.mkdirs();
			}

			if ((newName == null) || (newName.trim().length() == 0)) {
				return newOutDir;
			}

			return this.createFile(newOutDir, newName);
		}
		return new File(outDir, name);
	}

}
