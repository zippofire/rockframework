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
import com.lowagie.text.pdf.PdfContentByte;

public class ItextCircle extends ItextBasicImpl {

	private static final long	serialVersionUID	= 1L;

	private Color				borderColor;

	private Color				fillColor;

	private float				lineWidth;

	private float				size;

	public ItextCircle() {
		super();
		this.borderColor = Color.BLACK;
		this.fillColor = Color.BLACK;
		this.lineWidth = 1.5f;
		this.size = 0;
	}

	public void write(Document document, PdfContentByte content) {
		this.setLeft(this.left * 1.5f);
		this.size = this.size / 2;

		content.setLineWidth(this.lineWidth);
		content.circle(this.left, document.getPageSize().top() - (this.top + this.size), this.size);
		content.setColorFill(this.fillColor);
		content.setColorStroke(this.borderColor);
		content.fillStroke();
		content.closePathFillStroke();
	}

	// Getters and Setters
	public Color getBorderColor() {
		return this.borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public float getLineWidth() {
		return this.lineWidth;
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public float getSize() {
		return this.size;
	}

	public void setSize(float size) {
		this.size = size;
	}

}
