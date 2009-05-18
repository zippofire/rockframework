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
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.itext.beans.ItextDocument;
import net.woodstock.rockframework.itext.beans.ItextPage;
import net.woodstock.rockframework.itext.beans.impl.ItextCell;
import net.woodstock.rockframework.itext.beans.impl.ItextTable;
import net.woodstock.rockframework.itext.types.Alignment;

public class TableTest extends TestCase {

	public void xtest1() throws Exception {
		ItextDocument d = new ItextDocument();
		ItextPage p = new ItextPage();

		ItextTable t = new ItextTable();
		t.setTotalWidth(300);
		t.setAlignment(Alignment.CENTER);
		t.setColumns(1);

		ItextCell c1 = new ItextCell();
		// c1.setHeight(50);
		c1.setAlignment(Alignment.LEFT);
		c1.setBackgroundColor(Color.RED);
		c1.setColor(Color.GREEN);
		c1.setText("Celula 1");
		c1.setVerticalAlignment(Alignment.TOP);
		c1.setWidth(150);
		t.addCell(c1);

		ItextCell c2 = new ItextCell();
		c2.setAlignment(Alignment.CENTER);
		c2.setText("Celula 2");
		c2.setBackgroundColor(Color.GREEN);
		c2.setColor(Color.BLUE);
		c2.setWidth(75);
		c2.setVerticalAlignment(Alignment.MIDDLE);
		t.addCell(c2);

		ItextCell c3 = new ItextCell();
		c3.setAlignment(Alignment.RIGHT);
		c3.setText("Celula 3");
		c3.setBackgroundColor(Color.BLUE);
		c3.setColor(Color.RED);
		c3.setWidth(75);
		c3.setVerticalAlignment(Alignment.BOTTOM);
		t.addCell(c3);

		p.addItem(t);

		d.addPage(p);

		d.write(new File("D:/table.pdf"));
	}

	public void test2() throws Exception {
		ItextDocument d = new ItextDocument();
		ItextPage p = new ItextPage();

		ItextTable t = new ItextTable();
		t.setAlignment(Alignment.CENTER);
		t.setColumns(2);
		t.setTotalWidth(400);

		ItextCell c1 = new ItextCell();
		c1.setText("Nome");
		c1.setAlignment(Alignment.RIGHT);
		c1.setPadding(0);
		c1.setWidth(100);
		t.addCell(c1);

		ItextCell c2 = new ItextCell();
		c2.setText("Lourival Sabino");
		c2.setAlignment(Alignment.LEFT);
		c2.setPadding(0);
		c2.setWidth(300);
		t.addCell(c2);

		ItextCell c3 = new ItextCell();
		c3.setText("Telefone");
		c3.setAlignment(Alignment.RIGHT);
		c3.setPadding(0);
		t.addCell(c3);

		ItextCell c4 = new ItextCell();
		c4.setText("9231-0557");
		c4.setAlignment(Alignment.LEFT);
		c4.setPadding(0);
		t.addCell(c4);

		p.addItem(t);

		d.addPage(p);

		d.write(new File("D:/table.pdf"));

		FileOutputStream fos = new FileOutputStream("D:/table1.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(d);
		oos.close();
		fos.close();
	}

}
