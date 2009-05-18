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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URLConnection;

public class File extends java.io.File {

	private static final long	serialVersionUID	= 5432226606852967223L;

	public File(java.io.File parent, String child) {
		super(parent, child);
	}

	public File(String parent, String child) {
		super(parent, child);
	}

	public File(String pathname) {
		super(pathname);
	}

	public File(URI uri) {
		super(uri);
	}

	// Streams
	public InputStream getInputStream() throws FileNotFoundException {
		return new FileInputStream(this);
	}

	public OutputStream getOutputStream() throws FileNotFoundException {
		return new FileOutputStream(this);
	}

	public Reader getReader() throws FileNotFoundException {
		return new FileReader(this);
	}

	public Writer getWriter() throws FileNotFoundException, IOException {
		return new FileWriter(this);
	}

	// Utils
	public int getContentLength() throws IOException {
		URLConnection con = this.toURI().toURL().openConnection();
		return con.getContentLength();
	}

	public String getContentType() throws IOException {
		URLConnection con = this.toURI().toURL().openConnection();
		return con.getContentType();
	}

}
