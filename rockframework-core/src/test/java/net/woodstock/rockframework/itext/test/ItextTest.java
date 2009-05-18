/*
 * This file is part of rockapi.
 * 
 * rockapi is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockapi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package net.woodstock.rockframework.itext.test;

import java.io.FileOutputStream;

import junit.framework.TestCase;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class ItextTest extends TestCase {

	public void test1() throws Exception {
		Document doc = new Document();

		PdfWriter.getInstance(doc, new FileOutputStream("D:/itext.pdf"));

		doc.open();

		Paragraph p1 = new Paragraph();

		p1.add(new Chunk("Um Chunk"));

		Paragraph p2 = new Paragraph("Teste2");
		Paragraph p3 = new Paragraph("Teste3");

		doc.add(p1);
		doc.add(p2);
		doc.add(p3);

		doc.close();
	}

}
