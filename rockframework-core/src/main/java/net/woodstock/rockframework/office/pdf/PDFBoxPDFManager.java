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

import net.woodstock.rockframework.util.Assert;

class PDFBoxPDFManager extends PDFManager {

	@Override
	public InputStream cut(final InputStream source, final int start, final int end) throws IOException {
		try {
			Assert.notNull(source, "source");
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public InputStream merge(final InputStream[] sources) throws IOException {
		try {
			Assert.notEmpty(sources, "sources");
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public InputStream[] split(final InputStream source, final int size) throws IOException {
		try {
			Assert.notNull(source, "source");
			Assert.greaterThan(size, 0, "size");
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
