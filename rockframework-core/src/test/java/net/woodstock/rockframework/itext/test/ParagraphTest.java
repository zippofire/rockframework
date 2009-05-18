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

import java.awt.Color;
import java.io.File;

import junit.framework.TestCase;
import net.woodstock.rockframework.itext.beans.ItextDocument;
import net.woodstock.rockframework.itext.beans.ItextPage;
import net.woodstock.rockframework.itext.beans.impl.ItextParagraph;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;

public class ParagraphTest extends TestCase {

	public void test1() throws Exception {
		ItextDocument d = new ItextDocument();
		ItextPage p = new ItextPage();

		ItextParagraph p1 = new ItextParagraph();
		ItextParagraph p2 = new ItextParagraph();
		ItextParagraph p3 = new ItextParagraph();

		p1.setAlignment(Alignment.CENTER);
		p1.setBackgroundColor(Color.YELLOW);
		p1.setColor(Color.BLUE);
		p1.setFont(Font.COURIER);
		p1.setFontSize(12);
		p1.setFontStyle(FontStyle.BOLD);
		p1.setText("http://www.bol.com.br\r");

		p2.setAlignment(Alignment.RIGHT);
		p2.setBackgroundColor(Color.YELLOW);
		p2.setColor(Color.BLUE);
		p2.setFont(Font.COURIER);
		p2.setFontSize(12);
		p2.setFontStyle(FontStyle.BOLD);
		p2.setText("www.uol.com.br\r");

		p3.setAlignment(Alignment.LEFT);
		p3.setBackgroundColor(Color.YELLOW);
		p3.setColor(Color.BLUE);
		p3.setFont(Font.COURIER);
		p3.setFontSize(12);
		p3.setFontStyle(FontStyle.BOLD);
		p3.setText("www.uol.com.br\r");

		p.addItem(p1);
		p.addItem(p2);
		p.addItem(p3);

		d.addPage(p);

		d.write(new File("D:/paragraph.pdf"));
	}

}
