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
import java.io.IOException;
import java.io.Serializable;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.ConditionUtils;
import net.woodstock.rockframework.utils.FileUtils;

public class FileInfo implements Serializable, Comparable<FileInfo> {

	private static final long	serialVersionUID	= 1456362383300699776L;

	private String				name;

	private String				path;

	private String				parent;

	private String				extension;

	private String				mimeType;

	private int					size;

	public FileInfo(final String src) {
		super();
		Assert.notEmpty(src, "src");

		this.name = FileUtils.getName(src);
		this.path = FileUtils.getPath(src);
		this.parent = FileUtils.getParentPath(src);

		this.extension = FileUtils.getExtension(this.name);
		if (ConditionUtils.isNotEmpty(this.extension)) {
			this.mimeType = FileUtils.getTypeByExtension(this.extension);
		}
	}

	public FileInfo(final File file) throws IOException {
		super();
		Assert.notNull(file, "file");
		Assert.isFile(file, "file");

		this.name = FileUtils.getName(file);
		this.path = FileUtils.getPath(file);
		this.parent = FileUtils.getParentPath(file);

		this.extension = FileUtils.getExtension(this.name);
		if (ConditionUtils.isNotEmpty(this.extension)) {
			this.mimeType = FileUtils.getTypeByExtension(this.extension);
		}
		this.size = FileUtils.getSize(file);
	}

	public FileInfo(final String name, final String path, final String parent, final String extension, final String mimeType, final int size) {
		super();
		this.name = name;
		this.path = path;
		this.parent = parent;
		this.extension = extension;
		this.mimeType = mimeType;
		this.size = size;
	}

	public String getName() {
		return this.name;
	}

	public String getPath() {
		return this.path;
	}

	public String getParent() {
		return this.parent;
	}

	public String getExtension() {
		return this.extension;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public int getSize() {
		return this.size;
	}

	// Comparable
	@Override
	public int compareTo(final FileInfo o) {
		return this.path.compareTo(o.getParent());
	}

	// Object
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof FileInfo) {
			FileInfo other = (FileInfo) obj;
			return this.path.equals(other.getPath());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.path.hashCode();
	}

	@Override
	public String toString() {
		return this.path;
	}

}
