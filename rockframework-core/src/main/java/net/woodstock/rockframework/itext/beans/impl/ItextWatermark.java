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

import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.utils.StringUtils;

import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.Watermark;

public class ItextWatermark extends ItextObjectImpl {

	private static final long	serialVersionUID	= -1747198853854030060L;

	private float				absoluteX;

	private float				absoluteY;

	private Alignment			alignment;

	private String				alt;

	private Color				borderColor;

	private float				borderWidth;

	private float				indentationLeft;

	private float				indentationRight;

	private float				offsetX;

	private float				offsetY;

	private float				rotation;

	private float				scale;

	private float				spacingAfter;

	private float				spacingBefore;

	private String				url;

	public ItextWatermark() {
		super();
		this.absoluteX = Float.MIN_VALUE;
		this.absoluteY = Float.MIN_VALUE;
		this.alignment = Alignment.LEFT;
		this.alt = StringUtils.BLANK;
		this.borderColor = Color.black;
		this.borderWidth = Float.MIN_VALUE;
		this.indentationLeft = Float.MIN_VALUE;
		this.indentationRight = Float.MIN_VALUE;
		this.offsetX = Float.MIN_VALUE;
		this.offsetY = Float.MIN_VALUE;
		this.rotation = Float.MIN_VALUE;
		this.scale = Float.MIN_VALUE;
		this.spacingAfter = Float.MIN_VALUE;
		this.spacingBefore = Float.MIN_VALUE;
		this.url = StringUtils.BLANK;
	}

	public Element getObject() {
		try {
			Watermark mark = new Watermark(Image.getInstance(this.url), this.offsetX, this.offsetY);

			if (!StringUtils.isEmpty(this.alt)) {
				mark.setAlt(this.alt);
			}

			if ((this.absoluteX != Float.MIN_VALUE) && (this.absoluteY != Float.MIN_VALUE)) {
				mark.setAbsolutePosition(this.absoluteX, this.absoluteY);
			}

			if (this.alignment != Alignment.DEFAULT) {
				mark.setAlignment(this.alignment.getAlignment());
			}

			if (this.borderWidth != Float.MIN_VALUE) {
				mark.setBorderWidth(this.borderWidth);
				mark.setBorderColor(this.borderColor);
			}

			if (this.indentationLeft != Float.MIN_VALUE) {
				mark.setIndentationLeft(this.indentationLeft);
			}

			if (this.indentationRight != Float.MIN_VALUE) {
				mark.setIndentationRight(this.indentationRight);
			}

			if (this.rotation != Float.MIN_VALUE) {
				mark.setRotation((float) Math.toRadians(this.rotation));
			}

			if (this.spacingAfter != Float.MIN_VALUE) {
				mark.setSpacingAfter(this.spacingAfter);
			}

			if (this.spacingBefore != Float.MIN_VALUE) {
				mark.setSpacingBefore(this.spacingBefore);
			}

			if (this.scale != Float.MIN_VALUE) {
				mark.scalePercent(this.scale);
			}

			return mark;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Getters and Setters
	public float getAbsoluteX() {
		return this.absoluteX;
	}

	public void setAbsoluteX(float absoluteX) {
		this.absoluteX = absoluteX;
	}

	public float getAbsoluteY() {
		return this.absoluteY;
	}

	public void setAbsoluteY(float absoluteY) {
		this.absoluteY = absoluteY;
	}

	public Alignment getAlignment() {
		return this.alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public String getAlt() {
		return this.alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
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

	public float getOffsetX() {
		return this.offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return this.offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}

	public float getRotation() {
		return this.rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public float getScale() {
		return this.scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
