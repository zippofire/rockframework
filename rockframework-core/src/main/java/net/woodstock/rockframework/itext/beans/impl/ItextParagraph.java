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

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;

import net.woodstock.rockframework.itext.beans.ItextObject;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;
import net.woodstock.rockframework.utils.StringUtils;

public class ItextParagraph extends ItextObjectImpl {

	private static final long		serialVersionUID	= 4662309802060227935L;

	private Alignment				alignment;

	private Color					backgroundColor;

	private Color					color;

	private float					firstLineIndent;

	private Font					font;

	private float					fontSize;

	private FontStyle				fontStyle;

	private float					indentationLeft;

	private float					indentationRight;

	private float					leading;

	private float					spacingAfter;

	private float					spacingBefore;

	private String					text;

	private Collection<ItextObject>	items;

	public ItextParagraph() {
		super();
		this.alignment = Alignment.DEFAULT;
		this.backgroundColor = Color.WHITE;
		this.color = Color.BLACK;
		this.firstLineIndent = Float.MIN_VALUE;
		this.font = Font.DEFAULT;
		this.fontSize = 10;
		this.fontStyle = FontStyle.DEFAULT;
		this.indentationLeft = Float.MIN_VALUE;
		this.indentationRight = Float.MIN_VALUE;
		this.leading = Float.MIN_VALUE;
		this.spacingAfter = Float.MIN_VALUE;
		this.spacingBefore = Float.MIN_VALUE;
		this.text = StringUtils.BLANK;
		this.items = new ArrayList<ItextObject>();
	}

	public Element getObject() {
		Paragraph paragraph = new Paragraph();

		if (this.alignment != Alignment.DEFAULT) {
			paragraph.setAlignment(this.alignment.getAlignment());
		}

		if (this.indentationLeft != Float.MIN_VALUE) {
			paragraph.setFirstLineIndent(this.firstLineIndent);
		}

		if (this.indentationLeft != Float.MIN_VALUE) {
			paragraph.setIndentationLeft(this.indentationLeft);
		}

		if (this.indentationRight != Float.MIN_VALUE) {
			paragraph.setIndentationRight(this.indentationRight);
		}

		if (this.leading != Float.MIN_VALUE) {
			paragraph.setLeading(this.leading);
		}

		if (this.spacingAfter != Float.MIN_VALUE) {
			paragraph.setSpacingAfter(this.spacingAfter);
		}

		if (this.spacingBefore != Float.MIN_VALUE) {
			paragraph.setSpacingBefore(this.spacingBefore);
		}

		if (this.items.size() > 0) {
			for (ItextObject item : this.items) {
				paragraph.add(item.getObject());
			}
		} else {
			Chunk chunk = new Chunk(this.text);
			chunk.setBackground(this.backgroundColor);
			chunk.setFont(new com.lowagie.text.Font(this.font.getFont(), this.fontSize, this.fontStyle
					.getFontStyle(), this.color));
			paragraph.add(chunk);
			paragraph.add(new Phrase("\n"));
		}

		return paragraph;
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

	// Getters andf Setters
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

	public Color getColor() {
		return this.color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getFirstLineIndent() {
		return this.firstLineIndent;
	}

	public void setFirstLineIndent(float firstLineIndent) {
		this.firstLineIndent = firstLineIndent;
	}

	public Font getFont() {
		return this.font;
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

	public float getIndentationLeft() {
		return this.indentationLeft;
	}

	public void setIndentationLeft(float indentationLeft) {
		this.indentationLeft = indentationLeft;
	}

	public float getIndentationRight() {
		return this.indentationRight;
	}

	public void setIndentationRight(float indentationRight) {
		this.indentationRight = indentationRight;
	}

	public float getLeading() {
		return this.leading;
	}

	public void setLeading(float leading) {
		this.leading = leading;
	}

	public float getSpacingAfter() {
		return this.spacingAfter;
	}

	public void setSpacingAfter(float spacingAfter) {
		this.spacingAfter = spacingAfter;
	}

	public float getSpacingBefore() {
		return this.spacingBefore;
	}

	public void setSpacingBefore(float spacingBefore) {
		this.spacingBefore = spacingBefore;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
