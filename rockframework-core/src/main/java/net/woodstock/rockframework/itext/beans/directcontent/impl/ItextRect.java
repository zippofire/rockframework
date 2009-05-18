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

public class ItextRect extends ItextBasicImpl {

	private static final long	serialVersionUID	= 1L;

	private Color				fillColor			= Color.BLACK;

	private float				lineWidth			= 1.5f;

	private Color				lineColor			= Color.BLACK;

	private float				height				= 0;

	private float				width				= 0;

	public ItextRect() {
		super();
	}

	public Color getFillColor() {
		return this.fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public float getHeight() {
		return this.height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Color getLineColor() {
		return this.lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public float getLineWidth() {
		return this.lineWidth;
	}

	public void setLineWidth(float lineWidth) {
		this.lineWidth = lineWidth;
	}

	public float getWidth() {
		return this.width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void write(Document document, PdfContentByte content) throws DocumentException {
		if ((this.width == 0) || (this.height == 0)) {
			throw new DocumentException("Cold not write rect: width and height must be greater than 0");
		}
		content.setColorStroke(this.lineColor);
		if (this.fillColor != null) {
			content.setColorFill(this.fillColor);
		}
		content.setLineWidth(this.lineWidth);
		content.rectangle(this.left, document.getPageSize().top() - (this.top + this.height), this.width,
				this.height);
		if (this.fillColor != null) {
			content.fillStroke();
		} else {
			content.stroke();
		}
		content.closePathFillStroke();
	}

}
