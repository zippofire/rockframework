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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import br.net.woodstock.rockframework.io.InputOutputStream;
import br.net.woodstock.rockframework.office.pdf.PDFManager;
import br.net.woodstock.rockframework.util.Assert;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

class ITextManager implements PDFManager {

	@Override
	public InputStream cut(final InputStream source, final int start, final int end) throws IOException {
		try {
			Assert.notNull(source, "source");
			Assert.greaterOrEqual(start, 1, "start");

			PdfReader reader = new PdfReader(source);
			Document document = new Document(reader.getPageSizeWithRotation(1));
			InputOutputStream outputStream = new InputOutputStream();
			PdfCopy writer = new PdfCopy(document, outputStream);
			int pageCount = reader.getNumberOfPages();

			Assert.lessOrEqual(start, pageCount, "start");

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
			reader.close();

			return outputStream.getInputStream();
		} catch (DocumentException e) {
			throw new br.net.woodstock.rockframework.office.DocumentException(e);
		}
	}

	@Override
	public InputStream merge(final InputStream[] sources) throws IOException {
		try {
			Assert.notNull(sources, "sources");
			Assert.notEmpty(sources, "sources");

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
				reader.close();
			}

			document.close();
			writer.close();

			return outputStream.getInputStream();
		} catch (DocumentException e) {
			throw new br.net.woodstock.rockframework.office.DocumentException(e);
		}
	}

	@Override
	public InputStream[] split(final InputStream source, final int size) throws IOException {
		try {
			Assert.notNull(source, "source");
			Assert.greaterThan(size, 0, "size");

			PdfReader reader = new PdfReader(source);
			int pageCount = reader.getNumberOfPages();
			List<InputStream> list = new LinkedList<InputStream>();

			Document document = null;
			InputOutputStream outputStream = null;
			PdfCopy writer = null;
			for (int i = 1; i <= pageCount; i++) {
				if ((document == null) || ((i % size) == 0)) {
					if (document != null) {
						document.close();
						writer.close();
						list.add(outputStream.getInputStream());
					}
					document = new Document(reader.getPageSizeWithRotation(1));
					outputStream = new InputOutputStream();
					writer = new PdfCopy(document, outputStream);
				}
				PdfImportedPage page = writer.getImportedPage(reader, i);
				writer.addPage(page);
			}

			if (document != null) {
				document.close();
				writer.close();
				list.add(outputStream.getInputStream());
			}

			reader.close();

			return list.toArray(new InputStream[list.size()]);
		} catch (DocumentException e) {
			throw new br.net.woodstock.rockframework.office.DocumentException(e);
		}
	}

	@Override
	public String getText(final InputStream source) throws IOException {
		Assert.notNull(source, "source");

		PdfReader reader = new PdfReader(source);
		PdfReaderContentParser parser = new PdfReaderContentParser(reader);
		TextExtractionStrategy strategy = new SimpleTextExtractionStrategy();
		int pageCount = reader.getNumberOfPages();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);

		for (int i = 1; i <= pageCount; i++) {
			TextExtractionStrategy result = parser.processContent(i, strategy);
			String pageText = result.getResultantText();
			writer.println(pageText);
		}

		reader.close();
		writer.close();

		String text = new String(outputStream.toByteArray());
		return text;
	}

	@Override
	public InputStream[] toImage(final InputStream source, final String format) throws IOException {
		throw new UnsupportedOperationException();
	}

}
