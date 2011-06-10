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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.woodstock.rockframework.office.DocumentException;
import net.woodstock.rockframework.util.Assert;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.Splitter;

public class PDFBoxPDFManager extends PDFManager {

	@Override
	@SuppressWarnings("unchecked")
	public InputStream cut(final InputStream source, final int start, final int end) throws IOException {
		try {
			Assert.notNull(source, "source");

			PDFParser parser = new PDFParser(source);
			parser.parse();

			PDDocument document = parser.getPDDocument();

			Splitter splitter = new Splitter();
			splitter.setSplitAtPage(1);

			List<PDDocument> list = splitter.split(document);
			int pageCount = list.size();

			int endPage = end;
			if (endPage > pageCount) {
				endPage = pageCount;
			}

			PDDocument destination = list.get(start + 1);

			PDFMergerUtility merger = new PDFMergerUtility();
			for (int i = start + 2; i <= endPage; i++) {
				PDDocument tmpDocument = list.get(i);
				merger.appendDocument(destination, tmpDocument);
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			merger.setDestinationStream(bos);
			merger.mergeDocuments();

			for (PDDocument doc : list) {
				doc.close();
			}
			document.close();

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

			return bis;
		} catch (COSVisitorException e) {
			throw new DocumentException(e);
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
		} catch (COSVisitorException e) {
			throw new DocumentException(e);
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
			document.close();

			return array;
		} catch (COSVisitorException e) {
			throw new DocumentException(e);
		}
	}

	@Override
	public String getText(final InputStream source) throws IOException {
		Assert.notNull(source, "source");

		PDFParser parser = new PDFParser(source);
		parser.parse();

		PDDocument document = parser.getPDDocument();
		PDFTextStripper stripper = new PDFTextStripper();

		String text = stripper.getText(document);

		document.close();

		return text;
	}

	@Override
	@SuppressWarnings("unchecked")
	public BufferedImage[] toImage(final InputStream source) throws IOException {
		PDDocument document = PDDocument.load(source);
		List<PDPage> pages = document.getDocumentCatalog().getAllPages();
		int pageCount = pages.size();
		int index = 0;
		BufferedImage[] array = new BufferedImage[pageCount];
		for (PDPage page : pages) {
			BufferedImage image = page.convertToImage();
			array[index++] = image;
		}
		return array;
	}

}
