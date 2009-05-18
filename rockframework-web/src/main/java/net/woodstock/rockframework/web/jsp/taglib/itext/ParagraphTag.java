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

import net.woodstock.rockframework.itext.beans.impl.ItextParagraph;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.itext.types.Font;
import net.woodstock.rockframework.itext.types.FontStyle;
import net.woodstock.rockframework.utils.ColorUtils;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD;
import net.woodstock.rockframework.web.jsp.taglib.common.TLDAttribute;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD.BodyContent;

@TLD(name = "paragraph", type = BodyContent.SCRIPTLESS)
public class ParagraphTag extends ITextTag {

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			alignment;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			backgroundColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			color;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			firstLineIndent;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			font;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			fontSize;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			fontStyle;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			indentationLeft;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			indentationRight;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			leading;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			spacingAfter;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String			spacingBefore;

	private ItextParagraph	paragraph;

	@Override
	protected void doTagInternal() throws JspException, IOException {
		if (this.getContainer() == null) {
			throw new JspException("Paragraph must appers inside a page");
		}

		this.paragraph = new ItextParagraph();

		if (!StringUtils.isEmpty(this.alignment)) {
			this.paragraph.setAlignment(Alignment.valueOf(this.alignment));
		}

		if (!StringUtils.isEmpty(this.backgroundColor)) {
			this.paragraph.setBackgroundColor(ColorUtils.createColor(this.backgroundColor));
		}

		if (!StringUtils.isEmpty(this.color)) {
			this.paragraph.setColor(ColorUtils.createColor(this.color));
		}

		if (!StringUtils.isEmpty(this.firstLineIndent)) {
			this.paragraph.setFirstLineIndent(Float.parseFloat(this.firstLineIndent));
		}

		if (!StringUtils.isEmpty(this.font)) {
			this.paragraph.setFont(Font.valueOf(this.font));
		}

		if (!StringUtils.isEmpty(this.fontSize)) {
			this.paragraph.setFontSize(Float.parseFloat(this.fontSize));
		}

		if (!StringUtils.isEmpty(this.fontStyle)) {
			this.paragraph.setFontStyle(FontStyle.valueOf(this.fontStyle));
		}

		if (!StringUtils.isEmpty(this.indentationLeft)) {
			this.paragraph.setIndentationLeft(Float.parseFloat(this.indentationLeft));
		}

		if (!StringUtils.isEmpty(this.indentationRight)) {
			this.paragraph.setIndentationRight(Float.parseFloat(this.indentationRight));
		}

		if (!StringUtils.isEmpty(this.leading)) {
			this.paragraph.setLeading(Float.parseFloat(this.leading));
		}

		if (!StringUtils.isEmpty(this.spacingAfter)) {
			this.paragraph.setSpacingAfter(Float.parseFloat(this.spacingAfter));
		}

		if (!StringUtils.isEmpty(this.spacingBefore)) {
			this.paragraph.setSpacingBefore(Float.parseFloat(this.spacingBefore));
		}

		StringWriter writer = new StringWriter();

		this.getJspBody().invoke(writer);

		this.paragraph.setText(writer.getBuffer().toString().trim());

		this.getContainer().add(this.paragraph);
	}

	// Getters and Setters
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

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFirstLineIndent() {
		return this.firstLineIndent;
	}

	public void setFirstLineIndent(String firstLineIndent) {
		this.firstLineIndent = firstLineIndent;
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

	public String getLeading() {
		return this.leading;
	}

	public void setLeading(String leading) {
		this.leading = leading;
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

}
