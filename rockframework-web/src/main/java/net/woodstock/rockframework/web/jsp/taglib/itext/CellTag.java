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
import java.io.StringWriter;

import javax.servlet.jsp.JspException;

import net.woodstock.rockframework.itext.beans.ItextObject;
import net.woodstock.rockframework.itext.beans.impl.ItextCell;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;
import net.woodstock.rockframework.utils.ColorUtils;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD;
import net.woodstock.rockframework.web.jsp.taglib.common.TLDAttribute;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD.BodyContent;

@TLD(name = "cell", type = BodyContent.SCRIPTLESS)
public class CellTag extends ContainerTag {

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		alignment;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		backgroundColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		borderColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		borderWidth;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		color;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		colspan;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		font;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		fontSize;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		fontStyle;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		height;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		padding;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		verticalAlignment;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		width;

	private ItextCell	cell;

	@Override
	public void doTagInternal() throws JspException, IOException {
		if (!(this.getContainer() instanceof TableTag)) {
			throw new JspException("Cell must appers inside a table");
		}

		this.cell = new ItextCell();

		if (!StringUtils.isEmpty(this.alignment)) {
			this.cell.setAlignment(Alignment.valueOf(this.alignment));
		}

		if (!StringUtils.isEmpty(this.alignment)) {
			this.cell.setAlignment(Alignment.valueOf(this.alignment));
		}

		if (!StringUtils.isEmpty(this.backgroundColor)) {
			this.cell.setBackgroundColor(ColorUtils.createColor(this.backgroundColor));
		}

		if (!StringUtils.isEmpty(this.borderColor)) {
			this.cell.setBorderColor(ColorUtils.createColor(this.borderColor));
		}

		if (!StringUtils.isEmpty(this.borderWidth)) {
			this.cell.setBorderWidth(Float.parseFloat(this.borderWidth));
		}

		if (!StringUtils.isEmpty(this.color)) {
			this.cell.setColor(ColorUtils.createColor(this.color));
		}

		if (!StringUtils.isEmpty(this.colspan)) {
			this.cell.setColspan(Integer.parseInt(this.colspan));
		}

		if (!StringUtils.isEmpty(this.font)) {
			this.cell.setFont(Font.valueOf(this.font));
		}

		if (!StringUtils.isEmpty(this.fontSize)) {
			this.cell.setFontSize(Integer.parseInt(this.fontSize));
		}

		if (!StringUtils.isEmpty(this.fontStyle)) {
			this.cell.setFontStyle(FontStyle.valueOf(this.fontStyle));
		}

		if (!StringUtils.isEmpty(this.height)) {
			this.cell.setHeight(Float.parseFloat(this.height));
		}

		if (!StringUtils.isEmpty(this.padding)) {
			this.cell.setPadding(Float.parseFloat(this.padding));
		}

		if (!StringUtils.isEmpty(this.verticalAlignment)) {
			this.cell.setVerticalAlignment(Alignment.valueOf(this.verticalAlignment));
		}

		if (!StringUtils.isEmpty(this.width)) {
			this.cell.setWidth(Float.parseFloat(this.width));
		}

		StringWriter writer = new StringWriter();

		this.getJspBody().invoke(writer);

		this.cell.setText(writer.getBuffer().toString().trim());

		this.getContainer().add(this.cell);
	}

	@Override
	public void add(ItextObject item) {
		this.cell.addItem(item);
	}

	// Getters andf Setters
	public String getAlignment() {
		return this.alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
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

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColspan() {
		return this.colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getFont() {
		return this.font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public String getFontStyle() {
		return this.fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getPadding() {
		return this.padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

	public String getVerticalAlignment() {
		return this.verticalAlignment;
	}

	public void setVerticalAlignment(String verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
