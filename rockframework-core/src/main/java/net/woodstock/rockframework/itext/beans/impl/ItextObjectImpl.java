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
package net.woodstock.rockframework.itext.beans.impl;

import net.woodstock.rockframework.itext.beans.ItextObject;

import com.lowagie.text.pdf.PdfContentByte;

public abstract class ItextObjectImpl implements ItextObject {

	private static final long			serialVersionUID	= 1243475141524626891L;

	private transient PdfContentByte	content;

	public PdfContentByte getContentByte() {
		return this.content;
	}

	public void setContent(PdfContentByte content) {
		this.content = content;
	}

}
