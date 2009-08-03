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
import net.woodstock.rockframework.itext.types.Barcode;
import net.woodstock.rockframework.itext.types.BarcodeStyle;
import net.woodstock.rockframework.utils.StringUtils;

import com.lowagie.text.Element;
import com.lowagie.text.Image;

public class ItextBarcode extends ItextObjectImpl {

	private static final long	serialVersionUID	= -1723953166749206921L;

	private float				absoluteX;

	private float				absoluteY;

	private Alignment			alignment;

	private String				alt;

	private Barcode				barcode;

	private BarcodeStyle		barcodeStyle;

	private Color				barcodeColor;

	private Color				borderColor;

	private float				borderWidth;

	private float				height;

	private float				indentationLeft;

	private float				indentationRight;

	private float				rotation;

	private float				scale;

	private boolean				showText;

	private float				spacingAfter;

	private float				spacingBefore;

	private String				text;

	private Alignment			textAlignment;

	private Color				textColor;

	private float				textSize;

	private float				width;

	public ItextBarcode() {
		super();
		this.absoluteX = Float.MIN_VALUE;
		this.absoluteY = Float.MIN_VALUE;
		this.alignment = Alignment.LEFT;
		this.alt = StringUtils.BLANK;
		this.barcode = Barcode.DEFAULT;
		this.barcodeStyle = BarcodeStyle.DEFAULT;
		this.borderColor = Color.black;
		this.borderWidth = Float.MIN_VALUE;
		this.height = Float.MIN_VALUE;
		this.indentationLeft = Float.MIN_VALUE;
		this.indentationRight = Float.MIN_VALUE;
		this.rotation = Float.MIN_VALUE;
		this.scale = Float.MIN_VALUE;
		this.showText = true;
		this.spacingAfter = Float.MIN_VALUE;
		this.spacingBefore = Float.MIN_VALUE;
		this.text = StringUtils.BLANK;
		this.textAlignment = Alignment.CENTER;
		this.textColor = Color.black;
		this.textSize = 10;
		this.width = 1;
	}

	public Element getObject() {
		com.lowagie.text.pdf.Barcode barcode = this.barcode.getBarcode();

		barcode.setBarHeight(this.height);
		barcode.setBaseline(this.barcodeStyle.getStyle());
		barcode.setCode(this.text);
		barcode.setStartStopText(false);
		barcode.setX(this.width);
		barcode.setSize(this.textSize);

		if (!this.showText) {
			barcode.setFont(null);
		}

		Image image = barcode
				.createImageWithBarcode(this.getContentByte(), this.barcodeColor, this.textColor);

		if (!StringUtils.isEmpty(this.alt)) {
			image.setAlt(this.alt);
		}

		if ((this.absoluteX != Float.MIN_VALUE) && (this.absoluteY != Float.MIN_VALUE)) {
			image.setAbsolutePosition(this.absoluteX, this.absoluteY);
		}

		if (this.alignment != Alignment.DEFAULT) {
			image.setAlignment(this.alignment.getAlignment());
		}

		if (this.borderWidth > Float.MIN_VALUE) {
			image.setBorderWidth(this.borderWidth);
			image.setBorderColor(this.borderColor);
		}

		if (this.indentationLeft != Float.MIN_VALUE) {
			image.setIndentationLeft(this.indentationLeft);
		}

		if (this.indentationRight != Float.MIN_VALUE) {
			image.setIndentationRight(this.indentationRight);
		}

		if (this.rotation != Float.MIN_VALUE) {
			image.setRotation((float) Math.toRadians(this.rotation));
		}

		if (this.scale != Float.MIN_VALUE) {
			image.scalePercent(this.scale);
		}

		if (this.spacingAfter != Float.MIN_VALUE) {
			image.setSpacingAfter(this.spacingAfter);
		}

		if (this.spacingBefore != Float.MIN_VALUE) {
			image.setSpacingBefore(this.spacingBefore);
		}

		if (this.textAlignment != Alignment.DEFAULT) {
			barcode.setTextAlignment(this.textAlignment.getAlignment());
		}

		image.setTransparency(new int[] { 255, 255 });

		return image;
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

	public Barcode getBarcode() {
		return this.barcode;
	}

	public void setBarcode(Barcode barcode) {
		this.barcode = barcode;
	}

	public Color getBarcodeColor() {
		return this.barcodeColor;
	}

	public void setBarcodeColor(Color barcodeColor) {
		this.barcodeColor = barcodeColor;
	}

	public BarcodeStyle getBarcodeStyle() {
		return this.barcodeStyle;
	}

	public void setBarcodeStyle(BarcodeStyle barcodeStyle) {
		this.barcodeStyle = barcodeStyle;
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

	public float getHeight() {
		return this.height;
	}

	public void setHeight(float height) {
		this.height = height;
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

	public boolean isShowText() {
		return this.showText;
	}

	public void setShowText(boolean showText) {
		this.showText = showText;
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

	public Alignment getTextAlignment() {
		return this.textAlignment;
	}

	public void setTextAlignment(Alignment textAlignment) {
		this.textAlignment = textAlignment;
	}

	public Color getTextColor() {
		return this.textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public float getTextSize() {
		return this.textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getWidth() {
		return this.width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

}
