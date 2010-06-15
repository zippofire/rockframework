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
package net.woodstock.rockframework.office.util;

import java.io.IOException;
import java.io.InputStream;

import net.woodstock.rockframework.office.pdf.PDFManager;

public abstract class PDFUtils {

	private PDFUtils() {
		//
	}

	public static InputStream cut(final InputStream source, final int start, final int end) throws IOException {
		return PDFManager.getInstance().cut(source, start, end);
	}

	public static InputStream merge(final InputStream[] sources) throws IOException {
		return PDFManager.getInstance().merge(sources);
	}

	public static InputStream[] split(final InputStream source, final int size) throws IOException {
		return PDFManager.getInstance().split(source, size);
	}
}
