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
package net.woodstock.rockframework.itext.beans.directcontent.impl;

import java.awt.Color;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;

public class ItextLine extends ItextBasicImpl {

	private static final long	serialVersionUID	= 1L;

	private float				bottom				= 0;

	private Color				color				= Color.BLACK;

	private float				right				= 0;

	private float				width				= 1.5f;

	public ItextLine() {
		super();
	}

	public float getBottom() {
		return this.bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getRight() {
		return this.right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getWidth() {
		return this.width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void write(Document document, PdfContentByte content) throws DocumentException {
		if ((this.right == this.left) && (this.bottom == this.top)) {
			throw new DocumentException("Cold not write line: line size is invalid");
		}
		content.setLineWidth(this.width);
		content.setColorStroke(this.color);
		content.moveTo(this.left, document.getPageSize().top() - this.top);
		content.lineTo(this.right, document.getPageSize().top() - this.bottom);
		content.stroke();
	}

}
