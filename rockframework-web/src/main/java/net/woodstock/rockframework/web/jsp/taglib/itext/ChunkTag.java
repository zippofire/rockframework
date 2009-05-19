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

import net.woodstock.rockframework.itext.impl.Chunk;
import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;
import net.woodstock.rockframework.utils.ColorUtils;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD;
import net.woodstock.rockframework.web.jsp.taglib.common.TLDAttribute;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD.BodyContent;

@TLD(name = "chunk", type = BodyContent.SCRIPTLESS)
public class ChunkTag extends ITextTag {

	private static final long	serialVersionUID	= 1L;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				anchor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				backgroundColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				color;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				font;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				fontSize;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				fontStyle;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				horizontalScaling;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String				textRise;

	private Chunk			chunk;

	@Override
	protected void doTagInternal() throws JspException, IOException {
		if (this.getContainer() == null) {
			throw new JspException("Chunk must appers inside a page");
		}

		this.chunk = new Chunk();

		if (!StringUtils.isEmpty(this.anchor)) {
			this.chunk.setAnchor(this.anchor);
		}

		if (!StringUtils.isEmpty(this.backgroundColor)) {
			this.chunk.setBackgroundColor(ColorUtils.createColor(this.backgroundColor));
		}

		if (!StringUtils.isEmpty(this.color)) {
			this.chunk.setColor(ColorUtils.createColor(this.color));
		}

		if (!StringUtils.isEmpty(this.font)) {
			this.chunk.setFont(Font.valueOf(this.font));
		}

		if (!StringUtils.isEmpty(this.fontSize)) {
			this.chunk.setFontSize(Integer.parseInt(this.fontSize));
		}

		if (!StringUtils.isEmpty(this.fontStyle)) {
			this.chunk.setFontStyle(FontStyle.valueOf(this.fontStyle));
		}

		if (!StringUtils.isEmpty(this.horizontalScaling)) {
			this.chunk.setHorizontalScaling(Float.parseFloat(this.horizontalScaling));
		}

		if (!StringUtils.isEmpty(this.textRise)) {
			this.chunk.setTextRise(Float.parseFloat(this.textRise));
		}

		StringWriter writer = new StringWriter();

		this.getJspBody().invoke(writer);

		this.chunk.setText(writer.getBuffer().toString().trim());

		this.getContainer().add(this.chunk);
	}

	// Setters and getters
	public String getAnchor() {
		return this.anchor;
	}

	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
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

	public String getHorizontalScaling() {
		return this.horizontalScaling;
	}

	public void setHorizontalScaling(String horizontalScaling) {
		this.horizontalScaling = horizontalScaling;
	}

	public String getTextRise() {
		return this.textRise;
	}

	public void setTextRise(String textRise) {
		this.textRise = textRise;
	}

}
