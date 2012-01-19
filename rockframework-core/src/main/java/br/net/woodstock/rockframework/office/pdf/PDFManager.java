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
package br.net.woodstock.rockframework.office.pdf;

import java.io.InputStream;
import java.util.Collection;

import br.net.woodstock.rockframework.security.sign.impl.PDFSignData;

public interface PDFManager {

	InputStream cut(InputStream source, int start, int end);

	InputStream merge(InputStream[] sources);

	InputStream[] split(InputStream source, int size);

	String getText(InputStream source);

	InputStream[] toImage(InputStream source, String format);

	InputStream sign(InputStream source, PDFSignData data);

	Collection<PDFSignature> getSignatures(InputStream source);

}
