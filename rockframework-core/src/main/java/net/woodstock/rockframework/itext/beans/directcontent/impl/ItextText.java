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
import java.io.IOException;

import net.woodstock.rockframework.itext.types.BaseFont;
import net.woodstock.rockframework.utils.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;

public class ItextText extends ItextBasicImpl {

	private static final long	serialVersionUID	= 1L;

	private int					angle				= 0;

	private Color				color				= Color.BLACK;

	private float				size				= 10f;

	private String				text				= StringUtils.BLANK;

	private BaseFont			font				= BaseFont.DEFAULT;

	public ItextText() {
		super();
	}

	public int getAngle() {
		return this.angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public BaseFont getFont() {
		return this.font;
	}

	public void setFont(BaseFont font) {
		this.font = font;
	}

	public float getSize() {
		return this.size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void write(Document document, PdfContentByte content) throws DocumentException {
		if (StringUtils.isEmpty(this.text)) {
			throw new DocumentException("Cold not write text: text is null or blank");
		}
		com.lowagie.text.pdf.BaseFont baseFont;
		try {
			baseFont = com.lowagie.text.pdf.BaseFont.createFont(this.font.getFont(),
					com.lowagie.text.pdf.BaseFont.CP1252, com.lowagie.text.pdf.BaseFont.NOT_EMBEDDED);
		} catch (IOException e) {
			throw new DocumentException(e);
		}
		content.beginText();
		content.setFontAndSize(baseFont, this.size);
		content.setColorFill(this.color);
		content.setTextMatrix((float) Math.cos(Math.toRadians(this.angle)), (float) Math.sin(Math
				.toRadians(this.angle)), (float) (-Math.sin(Math.toRadians(this.angle))), (float) Math
				.cos(Math.toRadians(this.angle)), this.left, document.getPageSize().top() - this.top);
		content.showText(this.text);
		content.endText();

	}

}
