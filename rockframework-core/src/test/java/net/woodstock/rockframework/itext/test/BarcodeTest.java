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
import net.woodstock.rockframework.itext.beans.impl.ItextBarcode;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.itext.types.Barcode;
import net.woodstock.rockframework.itext.types.BarcodeStyle;

public class BarcodeTest extends TestCase {

	public void test1() throws Exception {
		ItextDocument d = new ItextDocument();
		ItextPage p = new ItextPage();

		ItextBarcode o1 = new ItextBarcode();
		o1.setBarcode(Barcode.BARCODE_128);
		o1.setBarcodeColor(Color.RED);
		o1.setBarcodeStyle(BarcodeStyle.TEXT_DOWN);
		o1.setHeight(40);
		o1.setText("123456789");
		o1.setTextAlignment(Alignment.LEFT);
		o1.setTextColor(Color.BLUE);
		o1.setTextSize(15);
		o1.setWidth(2);

		p.addItem(o1);

		d.addPage(p);

		d.write(new File("D:/barcode.pdf"));
	}

}
