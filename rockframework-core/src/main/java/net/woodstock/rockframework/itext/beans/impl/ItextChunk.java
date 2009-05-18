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
import java.util.List;

import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;
import net.woodstock.rockframework.utils.StringUtils;

import com.lowagie.text.Chunk;
import com.lowagie.text.Element;

public class ItextChunk extends ItextObjectImpl {

	private static final long	serialVersionUID	= -3200535099860459482L;

	private String				anchor;

	private Color				backgroundColor;

	private Color				color;

	private Font				font;

	private float				fontSize;

	private FontStyle			fontStyle;

	private float				horizontalScaling;

	private float				textRise;

	private String				text;

	private List<float[]>		underlines;

	public ItextChunk() {
		super();
		this.backgroundColor = Color.WHITE;
		this.color = Color.BLACK;
		this.font = Font.DEFAULT;
		this.fontSize = 10;
		this.fontStyle = FontStyle.DEFAULT;
		this.horizontalScaling = 1;
		this.textRise = Float.MIN_VALUE;
		this.text = "";
		this.underlines = new ArrayList<float[]>();
	}

	public Element getObject() {
		Chunk chunk = new Chunk("");

		if (!StringUtils.isEmpty(this.anchor)) {
			chunk.setAnchor(this.anchor);
		}

		if (this.underlines.size() > 0) {
			for (float[] underline : this.underlines) {
				chunk.setUnderline(underline[0], underline[1]);
			}
		}

		chunk.setBackground(this.backgroundColor);
		chunk.setFont(new com.lowagie.text.Font(this.font.getFont(), this.fontSize, this.fontStyle
				.getFontStyle(), this.color));
		chunk.setHorizontalScaling(this.horizontalScaling);
		chunk.append(this.text);

		if (this.textRise != Float.MIN_VALUE) {
			chunk.setTextRise(this.textRise);
		}

		return chunk;
	}

	// Getters and Setters
	public String getAnchor() {
		return this.anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
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

	public float getHorizontalScaling() {
		return this.horizontalScaling;
	}

	public void setHorizontalScaling(float horizontalScaling) {
		this.horizontalScaling = horizontalScaling;
	}

	public float getTextRise() {
		return this.textRise;
	}

	public void setTextRise(float textRise) {
		this.textRise = textRise;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUnderLine(float size, float height) {
		this.underlines.add(new float[] { size, height });
	}

}
