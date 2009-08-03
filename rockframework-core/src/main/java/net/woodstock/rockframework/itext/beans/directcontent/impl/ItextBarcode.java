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

import net.woodstock.rockframework.itext.types.Barcode;
import net.woodstock.rockframework.itext.types.BarcodeStyle;
import net.woodstock.rockframework.utils.StringUtils;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;

public class ItextBarcode extends ItextBasicImpl {

	private static final long	serialVersionUID	= 1L;

	private int					angle				= 0;

	private Barcode				barcode				= Barcode.DEFAULT;

	private Color				barcodeColor		= Color.BLACK;

	private BarcodeStyle		barcodeStyle		= BarcodeStyle.DEFAULT;

	private int					charGroup			= 0;

	private char				charSeparator		= ' ';

	private float				height				= 1;

	private String				text				= StringUtils.BLANK;

	private Color				textColor			= Color.BLACK;

	private float				width				= 0.8f;

	public ItextBarcode() {
		super();
	}

	public int getAngle() {
		return this.angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
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

	public int getCharGroup() {
		return this.charGroup;
	}

	public void setCharGroup(int charGroup) {
		this.charGroup = charGroup;
	}

	public char getCharSeparator() {
		return this.charSeparator;
	}

	public void setCharSeparator(char charSeparator) {
		this.charSeparator = charSeparator;
	}

	public float getHeight() {
		return this.height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getTextColor() {
		return this.textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public void write(Document document, PdfContentByte content) throws DocumentException {
		if (StringUtils.isEmpty(this.text)) {
			throw new DocumentException("Cold not write barcode: barcode text not defined");
		}
		if (this.charGroup > 0) {
			String tmp = StringUtils.BLANK;
			while (this.text.length() > this.charGroup) {
				tmp += this.text.substring(0, this.charGroup) + "  " + this.charSeparator + "  ";
				this.text = this.text.substring(this.charGroup);
			}
			tmp += this.text;
			this.text = tmp;
		}
		com.lowagie.text.pdf.Barcode code = this.barcode.getBarcode();

		code.setCode(this.text);
		code.setBarHeight(this.height);
		code.setStartStopText(false);
		code.setBaseline(this.barcodeStyle.getStyle());
		code.setX(this.width);

		Image img = code.createImageWithBarcode(content, this.barcodeColor, this.textColor);
		img.scalePercent(100);
		img.setAbsolutePosition(this.left, document.getPageSize().top() - this.top);
		img.setTransparency(new int[] { 255, 255 });
		img.setRotation((float) Math.toRadians(this.angle));
		content.addImage(img);
	}

	public float getWidth() {
		return this.width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

}
