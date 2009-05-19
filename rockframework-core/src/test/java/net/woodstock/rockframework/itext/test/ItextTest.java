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
import net.woodstock.rockframework.itext.Document;
import net.woodstock.rockframework.itext.Page;
import net.woodstock.rockframework.itext.impl.Phrase;
import net.woodstock.rockframework.itext.types.OutputType;

public class ItextTest extends TestCase {

	public void test1() throws Exception {
		Document document = new Document();
		Page page = new Page();
		Phrase phrase = new Phrase();

		document.setTitle("Teste");
		phrase.setText("Teste");

		page.addItem(phrase);
		document.addPage(page);

		document.write(new FileOutputStream("D:/x.html"), OutputType.HTML);
		document.write(new FileOutputStream("D:/x.pdf"), OutputType.PDF);
		document.write(new FileOutputStream("D:/x.rtf"), OutputType.RTF);
		document.write(new FileOutputStream("D:/x.xml"), OutputType.XML);
	}

}
