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
package net.woodstock.rockframework.itext;

import java.io.OutputStream;

import net.woodstock.rockframework.itext.types.OutputType;

import com.lowagie.text.DocWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.xml.XmlWriter;

public abstract class WriterFactory {

	public abstract DocWriter getWriter(Document document, OutputStream output) throws DocumentException;

	private static final WriterFactory	HTML_FACTORY	= new HtmlWriterFactory();

	private static final WriterFactory	PDF_FACTORY		= new PdfWriterFactory();

	private static final WriterFactory	RTF_FACTORY		= new RtfWriterFactory();

	private static final WriterFactory	XML_FACTORY		= new XmlWriterFactory();

	WriterFactory() {
		super();
	}

	public static WriterFactory getInstance(OutputType type) {
		switch (type) {
			case HTML:
				return WriterFactory.HTML_FACTORY;
			case PDF:
				return WriterFactory.PDF_FACTORY;
			case RTF:
				return WriterFactory.RTF_FACTORY;
			case XML:
				return WriterFactory.XML_FACTORY;
			default:
				throw new IllegalArgumentException("Invalid type: " + type);
		}
	}

	static class PdfWriterFactory extends WriterFactory {

		@Override
		public DocWriter getWriter(Document document, OutputStream output) throws DocumentException {
			return PdfWriter.getInstance(document, output);
		}

	}

	static class HtmlWriterFactory extends WriterFactory {

		@Override
		public DocWriter getWriter(Document document, OutputStream output) throws DocumentException {
			return HtmlWriter.getInstance(document, output);
		}

	}

	static class RtfWriterFactory extends WriterFactory {

		@Override
		public DocWriter getWriter(Document document, OutputStream output) throws DocumentException {
			return RtfWriter2.getInstance(document, output);
		}

	}

	static class XmlWriterFactory extends WriterFactory {

		@Override
		public DocWriter getWriter(Document document, OutputStream output) throws DocumentException {
			return XmlWriter.getInstance(document, output);
		}

	}

}
