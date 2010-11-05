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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.woodstock.rockframework.util.Assert;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.pdfbox.util.Splitter;

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

			PDFMergerUtility merger = new PDFMergerUtility();
			for (InputStream source : sources) {
				merger.addSource(source);
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			merger.setDestinationStream(bos);
			merger.mergeDocuments();

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

			return bis;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public InputStream[] split(final InputStream source, final int size) throws IOException {
		try {
			Assert.notNull(source, "source");
			Assert.greaterThan(size, 0, "size");

			PDFParser parser = new PDFParser(source);

			parser.parse();

			PDDocument document = parser.getPDDocument();

			Splitter splitter = new Splitter();

			splitter.setSplitAtPage(size);

			List<PDDocument> list = splitter.split(document);
			InputStream[] array = new InputStream[list.size()];

			for (int i = 0; i < list.size(); i++) {
				PDDocument d = list.get(i);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				COSWriter writer = new COSWriter(bos);
				writer.write(d);
				array[i] = new ByteArrayInputStream(bos.toByteArray());
				d.close();
			}

			return array;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
