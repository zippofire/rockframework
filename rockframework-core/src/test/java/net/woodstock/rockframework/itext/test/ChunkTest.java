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
import net.woodstock.rockframework.itext.beans.impl.ItextChunk;
import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;

public class ChunkTest extends TestCase {

	public void test1() throws Exception {
		ItextDocument d = new ItextDocument();
		ItextPage p = new ItextPage();

		ItextChunk o1 = new ItextChunk();
		ItextChunk o2 = new ItextChunk();

		o1.setAnchor("http://www.bol.com.br");
		o1.setBackgroundColor(Color.YELLOW);
		o1.setColor(Color.BLUE);
		o1.setFont(Font.COURIER);
		o1.setFontSize(12);
		o1.setFontStyle(FontStyle.BOLD);
		o1.setText("http://www.bol.com.br\r");
		o1.setUnderLine(1.5f, -3);

		o2.setAnchor("http://www.uol.com.br");
		o2.setBackgroundColor(Color.YELLOW);
		o2.setColor(Color.BLUE);
		o2.setFont(Font.COURIER);
		o2.setFontSize(12);
		o2.setFontStyle(FontStyle.BOLD);
		o2.setText("www.uol.com.br\r");
		o2.setUnderLine(1.5f, -3);

		p.addItem(o1);
		p.addItem(o2);

		d.addPage(p);

		d.write(new File("D:/chunk.pdf"));
	}

}
