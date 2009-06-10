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
package net.woodstock.rockframework.office;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.woodstock.rockframework.io.InputOutputStream;

public abstract class AbstractDocument implements Document {

	private static final long	serialVersionUID	= 2007062915200199794L;

	public AbstractDocument() {
		super();
	}

	public void write(File file) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(file);
		this.write(outputStream);
		outputStream.close();
	}

	public byte[] getBytes() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		this.write(outputStream);
		return outputStream.toByteArray();
	}

	public InputStream getInputStream() throws IOException {
		InputOutputStream outputStream = new InputOutputStream();
		this.write(outputStream);
		return outputStream.getInputStream();
	}

}
