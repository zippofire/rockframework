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

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import net.woodstock.rockframework.itext.beans.ItextObject;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;

public class ItextCell extends ItextObjectImpl {

	private static final long		serialVersionUID	= -2242004760388063763L;

	private Alignment				alignment;

	private Color					backgroundColor;

	private Color					borderColor;

	private float					borderWidth;

	private Color					color;

	private int						colspan;

	private Font					font;

	private float					fontSize;

	private FontStyle				fontStyle;

	private float					height;

	private float					padding;

	private String					text;

	private Alignment				verticalAlignment;

	private float					width;

	private Collection<ItextObject>	items;

	public ItextCell() {
		super();
		this.alignment = Alignment.LEFT;
		this.backgroundColor = Color.WHITE;
		this.borderColor = Color.black;
		this.borderWidth = Float.MIN_VALUE;
		this.color = Color.BLACK;
		this.colspan = 1;
		this.font = Font.DEFAULT;
		this.fontSize = 10;
		this.fontStyle = FontStyle.DEFAULT;
		this.height = Float.MIN_VALUE;
		this.padding = 1;
		this.text = "";
		this.verticalAlignment = Alignment.TOP;
		this.width = Float.MIN_VALUE;

		this.items = new ArrayList<ItextObject>();
	}

	public Element getObject() {
		PdfPCell cell = new PdfPCell();

		if (this.borderWidth != Float.MIN_VALUE) {
			cell.setBorderColor(this.borderColor);
			cell.setBorderWidth(this.borderWidth);
		}

		if (this.colspan != 1) {
			cell.setColspan(this.colspan);
		}

		if (this.height != Float.MIN_VALUE) {
			cell.setFixedHeight(this.height);
		}

		if (this.padding != Float.MIN_VALUE) {
			cell.setPadding(this.padding);
		}

		if (this.items.size() > 0) {
			for (ItextObject item : this.items) {
				cell.addElement(item.getObject());
			}
		} else {
			Phrase phrase = new Phrase();
			Chunk chunk = new Chunk(this.text);
			chunk.setFont(new com.lowagie.text.Font(this.font.getFont(), this.fontSize, this.fontStyle
					.getFontStyle(), this.color));
			phrase.add(chunk);
			cell.setPhrase(phrase);
		}

		cell.setBackgroundColor(this.backgroundColor);
		cell.setHorizontalAlignment(this.alignment.getAlignment());

		if (this.verticalAlignment != Alignment.TOP) {
			if (this.verticalAlignment == Alignment.BOTTOM) {
				this.setPadding(this.fontSize / 2);
			}
			cell.setVerticalAlignment(this.verticalAlignment.getAlignment());
		}

		return cell;
	}

	public void addItem(ItextObject item) {
		this.items.add(item);
	}

	public void removeItem(int index) {
		((ArrayList<ItextObject>) this.items).remove(index);
	}

	public void removeItem(ItextObject item) {
		this.items.remove(item);
	}

	// Getters and Setters
	public Alignment getAlignment() {
		return this.alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getBorderColor() {
		return this.borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public float getBorderWidth() {
		return this.borderWidth;
	}

	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Font getFont() {
		return this.font;
	}

	public int getColspan() {
		return this.colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public Color getColor() {
		return this.color;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public float getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public FontStyle getFontStyle() {
		return this.fontStyle;
	}

	public void setFontStyle(FontStyle fontStyle) {
		this.fontStyle = fontStyle;
	}

	public float getHeight() {
		return this.height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getPadding() {
		return this.padding;
	}

	public void setPadding(float padding) {
		this.padding = padding;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Alignment getVerticalAlignment() {
		return this.verticalAlignment;
	}

	public void setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public float getWidth() {
		return this.width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

}
