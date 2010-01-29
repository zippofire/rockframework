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

import net.woodstock.rockframework.io.InputOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public abstract class PDFUtils {

	private PDFUtils() {
		//
	}

	public static InputStream split(final InputStream source, final int start, final int end) throws IOException, DocumentException {
		if (source == null) {
			throw new IllegalArgumentException("Source document must be not null");
		}
		PdfReader reader = new PdfReader(source);
		Document document = new Document(reader.getPageSizeWithRotation(1));
		InputOutputStream outputStream = new InputOutputStream();
		PdfCopy writer = new PdfCopy(document, outputStream);
		int pageCount = reader.getNumberOfPages();

		if (start > pageCount) {
			throw new IllegalArgumentException("Start page greater than document page count");
		}

		int endPage = end;
		if (endPage > pageCount) {
			endPage = pageCount;
		}

		document.open();

		for (int i = start; i <= endPage; i++) {
			PdfImportedPage page = writer.getImportedPage(reader, i);
			writer.addPage(page);
		}

		document.close();
		writer.close();

		return outputStream.getInputStream();
	}

	public static InputStream merge(final InputStream[] sources) throws IOException, DocumentException {
		if (sources == null) {
			throw new IllegalArgumentException("Source documents must be not null");
		}
		if (sources.length == 0) {
			throw new IllegalArgumentException("Source documents must be not empty");
		}

		Document document = new Document();
		InputOutputStream outputStream = new InputOutputStream();
		PdfCopy writer = new PdfCopy(document, outputStream);

		document.open();

		for (InputStream source : sources) {
			PdfReader reader = new PdfReader(source);
			int pageCount = reader.getNumberOfPages();
			for (int i = 1; i <= pageCount; i++) {
				PdfImportedPage page = writer.getImportedPage(reader, i);
				writer.addPage(page);
			}
		}

		document.close();
		writer.close();

		System.out.println("Retornando");
		return outputStream.getInputStream();
	}
}
