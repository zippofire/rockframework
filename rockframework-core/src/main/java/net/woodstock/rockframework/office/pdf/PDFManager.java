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
package net.woodstock.rockframework.office.pdf;

import java.io.IOException;
import java.io.InputStream;

public interface PDFManager {

	InputStream cut(InputStream source, int start, int end) throws IOException;

	InputStream merge(InputStream[] sources) throws IOException;

	InputStream[] split(InputStream source, int size) throws IOException;

	String getText(InputStream source) throws IOException;

	InputStream[] toImage(InputStream source, String format) throws IOException;

}
