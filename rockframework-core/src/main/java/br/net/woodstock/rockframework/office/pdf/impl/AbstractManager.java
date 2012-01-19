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
package br.net.woodstock.rockframework.office.pdf.impl;

import java.io.InputStream;
import java.util.Collection;

import br.net.woodstock.rockframework.office.pdf.PDFManager;
import br.net.woodstock.rockframework.office.pdf.PDFSignature;
import br.net.woodstock.rockframework.security.sign.impl.PDFSignData;

abstract class AbstractManager implements PDFManager {

	@Override
	public InputStream cut(final InputStream source, final int start, final int end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream merge(final InputStream[] sources) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream[] split(final InputStream source, final int size) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getText(final InputStream source) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream[] toImage(final InputStream source, final String format) {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream sign(final InputStream source, final PDFSignData data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<PDFSignature> getSignatures(final InputStream source) {
		throw new UnsupportedOperationException();
	}

}
