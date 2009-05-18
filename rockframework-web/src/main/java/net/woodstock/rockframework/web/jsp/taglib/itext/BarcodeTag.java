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
package net.woodstock.rockframework.web.jsp.taglib.itext;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import net.woodstock.rockframework.itext.beans.impl.ItextBarcode;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.itext.types.Barcode;
import net.woodstock.rockframework.itext.types.BarcodeStyle;
import net.woodstock.rockframework.utils.ColorUtils;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD;
import net.woodstock.rockframework.web.jsp.taglib.common.TLDAttribute;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD.BodyContent;

@TLD(name = "barcode", type = BodyContent.EMPTY)
public class BarcodeTag extends ITextTag {

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			absoluteX;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			absoluteY;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			alignment;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			alt;

	@TLDAttribute(required = true, rtexprvalue = true)
	private String			barcode;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			barcodeStyle;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			barcodeColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			borderColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			borderWidth;

	@TLDAttribute(required = true, rtexprvalue = true)
	private String			height;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			indentationLeft;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			indentationRight;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			rotation;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			scale;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			showText;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			spacingAfter;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			spacingBefore;

	@TLDAttribute(required = true, rtexprvalue = true)
	private String			text;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			textAlignment;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			textColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			textSize;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			width;

	private ItextBarcode	code;

	@Override
	public void doTagInternal() throws JspException, IOException {
		if (this.getContainer() == null) {
			throw new JspException("Barcode must appers inside a page");
		}

		this.code = new ItextBarcode();

		if (!StringUtils.isEmpty(this.absoluteX)) {
			this.code.setAbsoluteX(Float.parseFloat(this.absoluteX));
		}

		if (!StringUtils.isEmpty(this.absoluteY)) {
			this.code.setAbsoluteY(Float.parseFloat(this.absoluteY));
		}

		if (!StringUtils.isEmpty(this.alignment)) {
			this.code.setAlignment(Alignment.valueOf(this.alignment));
		}

		if (!StringUtils.isEmpty(this.alt)) {
			this.code.setAlt(this.alt);
		}

		if (!StringUtils.isEmpty(this.barcode)) {
			this.code.setBarcode(Barcode.valueOf(this.barcode));
		}

		if (!StringUtils.isEmpty(this.barcodeStyle)) {
			this.code.setBarcodeStyle(BarcodeStyle.valueOf(this.barcodeStyle));
		}

		if (!StringUtils.isEmpty(this.barcodeColor)) {
			this.code.setBarcodeColor(ColorUtils.createColor(this.barcodeColor));
		}

		if (!StringUtils.isEmpty(this.borderColor)) {
			this.code.setBorderColor(ColorUtils.createColor(this.borderColor));
		}

		if (!StringUtils.isEmpty(this.borderWidth)) {
			this.code.setBorderWidth(Float.parseFloat(this.borderWidth));
		}

		if (!StringUtils.isEmpty(this.height)) {
			this.code.setHeight(Float.parseFloat(this.height));
		}

		if (!StringUtils.isEmpty(this.indentationLeft)) {
			this.code.setIndentationLeft(Float.parseFloat(this.indentationLeft));
		}

		if (!StringUtils.isEmpty(this.indentationRight)) {
			this.code.setIndentationRight(Float.parseFloat(this.indentationRight));
		}

		if (!StringUtils.isEmpty(this.rotation)) {
			this.code.setRotation(Float.parseFloat(this.rotation));
		}

		if (!StringUtils.isEmpty(this.scale)) {
			this.code.setScale(Float.parseFloat(this.scale));
		}

		if (!StringUtils.isEmpty(this.showText)) {
			this.code.setShowText(Boolean.parseBoolean(this.showText));
		}

		if (!StringUtils.isEmpty(this.spacingAfter)) {
			this.code.setSpacingAfter(Float.parseFloat(this.spacingAfter));
		}

		if (!StringUtils.isEmpty(this.spacingBefore)) {
			this.code.setSpacingBefore(Float.parseFloat(this.spacingBefore));
		}

		if (!StringUtils.isEmpty(this.text)) {
			this.code.setText(this.text);
		}

		if (!StringUtils.isEmpty(this.textAlignment)) {
			this.code.setTextAlignment(Alignment.valueOf(this.textAlignment));
		}

		if (!StringUtils.isEmpty(this.textColor)) {
			this.code.setTextColor(ColorUtils.createColor(this.textColor));
		}

		if (!StringUtils.isEmpty(this.textSize)) {
			this.code.setTextSize(Float.parseFloat(this.textSize));
		}

		if (!StringUtils.isEmpty(this.width)) {
			this.code.setWidth(Float.parseFloat(this.width));
		}

		this.getContainer().add(this.code);
	}

	// Getters and Setters
	public String getAbsoluteX() {
		return this.absoluteX;
	}

	public void setAbsoluteX(String absoluteX) {
		this.absoluteX = absoluteX;
	}

	public String getAbsoluteY() {
		return this.absoluteY;
	}

	public void setAbsoluteY(String absoluteY) {
		this.absoluteY = absoluteY;
	}

	public String getAlignment() {
		return this.alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getAlt() {
		return this.alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBarcodeColor() {
		return this.barcodeColor;
	}

	public void setBarcodeColor(String barcodeColor) {
		this.barcodeColor = barcodeColor;
	}

	public String getBarcodeStyle() {
		return this.barcodeStyle;
	}

	public void setBarcodeStyle(String barcodeStyle) {
		this.barcodeStyle = barcodeStyle;
	}

	public String getBorderColor() {
		return this.borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getBorderWidth() {
		return this.borderWidth;
	}

	public void setBorderWidth(String borderWidth) {
		this.borderWidth = borderWidth;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getIndentationLeft() {
		return this.indentationLeft;
	}

	public void setIndentationLeft(String indentationLeft) {
		this.indentationLeft = indentationLeft;
	}

	public String getIndentationRight() {
		return this.indentationRight;
	}

	public void setIndentationRight(String indentationRight) {
		this.indentationRight = indentationRight;
	}

	public String getRotation() {
		return this.rotation;
	}

	public void setRotation(String rotation) {
		this.rotation = rotation;
	}

	public String getScale() {
		return this.scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getShowText() {
		return this.showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}

	public String getSpacingAfter() {
		return this.spacingAfter;
	}

	public void setSpacingAfter(String spacingAfter) {
		this.spacingAfter = spacingAfter;
	}

	public String getSpacingBefore() {
		return this.spacingBefore;
	}

	public void setSpacingBefore(String spacingBefore) {
		this.spacingBefore = spacingBefore;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTextAlignment() {
		return this.textAlignment;
	}

	public void setTextAlignment(String textAlignment) {
		this.textAlignment = textAlignment;
	}

	public String getTextColor() {
		return this.textColor;
	}

	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}

	public String getTextSize() {
		return this.textSize;
	}

	public void setTextSize(String textSize) {
		this.textSize = textSize;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
