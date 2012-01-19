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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.Splitter;

import br.net.woodstock.rockframework.office.DocumentException;
import br.net.woodstock.rockframework.office.pdf.PDFException;
import br.net.woodstock.rockframework.util.Assert;

public class PDFBoxManager extends AbstractManager {

	public static final String	GIF_FORMAT	= "gif";

	public static final String	JPEG_FORMAT	= "jpeg";

	public static final String	PNG_FORMAT	= "png";

	@Override
	public InputStream cut(final InputStream source, final int start, final int end) {
		try {
			Assert.notNull(source, "source");
			Assert.greaterOrEqual(start, 1, "start");

			PDFParser parser = new PDFParser(source);
			parser.parse();

			PDDocument document = parser.getPDDocument();

			Splitter splitter = new Splitter();
			splitter.setSplitAtPage(1);

			List<PDDocument> list = splitter.split(document);
			int pageCount = list.size();

			int startPage = start;
			if (startPage < 1) {
				startPage = 1;
			}
			startPage = startPage - 1;

			int endPage = end;
			if (endPage > pageCount) {
				endPage = pageCount;
			}

			PDDocument destination = list.get(startPage);

			PDFMergerUtility merger = new PDFMergerUtility();
			for (int i = startPage + 1; i < endPage; i++) {
				PDDocument tmpDocument = list.get(i);
				merger.appendDocument(destination, tmpDocument);
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			COSWriter writer = new COSWriter(bos);
			writer.write(destination);

			for (PDDocument doc : list) {
				doc.close();
			}

			document.close();
			destination.close();

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

			return bis;
		} catch (IOException e) {
			throw new PDFException(e);
		} catch (COSVisitorException e) {
			throw new DocumentException(e);
		}
	}

	@Override
	public InputStream merge(final InputStream[] sources) {
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
		} catch (IOException e) {
			throw new PDFException(e);
		} catch (COSVisitorException e) {
			throw new DocumentException(e);
		}
	}

	@Override
	public InputStream[] split(final InputStream source, final int size) {
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
		} catch (IOException e) {
			throw new PDFException(e);
		} catch (COSVisitorException e) {
			throw new DocumentException(e);
		}
	}

	@Override
	public String getText(final InputStream source) {
		try {
			Assert.notNull(source, "source");

			PDFParser parser = new PDFParser(source);
			parser.parse();

			PDDocument document = parser.getPDDocument();
			PDFTextStripper stripper = new PDFTextStripper();

			String text = stripper.getText(document);

			document.close();

			return text;
		} catch (IOException e) {
			throw new PDFException(e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public InputStream[] toImage(final InputStream source, final String format) {
		try {
			PDDocument document = PDDocument.load(source);
			List<PDPage> pages = document.getDocumentCatalog().getAllPages();
			int pageCount = pages.size();
			int index = 0;
			InputStream[] array = new InputStream[pageCount];
			for (PDPage page : pages) {
				BufferedImage image = page.convertToImage();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ImageIO.write(image, format, outputStream);
				InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
				array[index++] = inputStream;
			}
			return array;
		} catch (IOException e) {
			throw new PDFException(e);
		}
	}

}
