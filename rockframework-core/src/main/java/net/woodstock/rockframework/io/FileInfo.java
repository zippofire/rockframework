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
import java.io.Serializable;

import net.woodstock.rockframework.utils.FileUtils;
import net.woodstock.rockframework.utils.StringUtils;

public class FileInfo implements Serializable {

	private static final long	serialVersionUID	= 1456362383300699776L;

	private String				name;

	private String				path;

	private String				extension;

	private String				mimeType;

	public FileInfo(final String src) {
		super();
		if (StringUtils.isEmpty(src)) {
			throw new IllegalArgumentException("Source must be not null");
		}
		int index = src.indexOf(File.separator);
		if (index != -1) {
			this.path = src.substring(src.lastIndexOf(0, index));
			this.name = src.substring(src.lastIndexOf(index + 1));
		} else {
			this.name = src;
		}

		index = this.name.indexOf(FileUtils.EXTENSION_SEPARATOR);
		if (index != -1) {
			this.extension = this.name.substring(index + 1);
			this.mimeType = FileUtils.getMimeType(this.extension);
		}
	}

	public FileInfo(final File file) {
		super();
		if (file == null) {
			throw new IllegalArgumentException("File must be not null");
		}
		this.name = file.getName();
		this.path = file.getAbsolutePath();

		int index = this.name.indexOf(FileUtils.EXTENSION_SEPARATOR);
		if (index != -1) {
			this.extension = this.name.substring(index + 1);
			this.mimeType = FileUtils.getMimeType(this.extension);
		}
	}

	public FileInfo(final String name, final String path, final String extension, final String mimeType) {
		super();
		this.name = name;
		this.path = path;
		this.extension = extension;
		this.mimeType = mimeType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}

}
