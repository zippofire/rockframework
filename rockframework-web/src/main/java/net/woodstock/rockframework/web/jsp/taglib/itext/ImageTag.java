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

import java.io.File;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import net.woodstock.rockframework.itext.impl.Image;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.utils.ColorUtils;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD;
import net.woodstock.rockframework.web.jsp.taglib.common.TLDAttribute;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD.BodyContent;

@TLD(name = "image", type = BodyContent.EMPTY)
public class ImageTag extends ITextTag {

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		absoluteX;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		absoluteY;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		alignment;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		alt;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		borderColor;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		borderWidth;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		indentationLeft;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		indentationRight;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		rotation;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		scale;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		spacingAfter;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String		spacingBefore;

	@TLDAttribute(required = true, rtexprvalue = true)
	private String		src;

	private Image	image;

	@Override
	protected void doTagInternal() throws JspException, IOException {
		if (this.getContainer() == null) {
			throw new JspException("Image must appers inside a page");
		}

		this.image = new Image();

		if (!StringUtils.isEmpty(this.absoluteX)) {
			this.image.setAbsoluteX(Float.parseFloat(this.absoluteX));
		}

		if (!StringUtils.isEmpty(this.absoluteY)) {
			this.image.setAbsoluteY(Float.parseFloat(this.absoluteY));
		}

		if (!StringUtils.isEmpty(this.alignment)) {
			this.image.setAlignment(Alignment.valueOf(this.alignment));
		}

		if (!StringUtils.isEmpty(this.alt)) {
			this.image.setAlt(this.alt);
		}

		if (!StringUtils.isEmpty(this.borderColor)) {
			this.image.setBorderColor(ColorUtils.createColor(this.borderColor));
		}

		if (!StringUtils.isEmpty(this.borderWidth)) {
			this.image.setBorderWidth(Float.parseFloat(this.borderWidth));
		}

		if (!StringUtils.isEmpty(this.indentationLeft)) {
			this.image.setIndentationLeft(Float.parseFloat(this.indentationLeft));
		}

		if (!StringUtils.isEmpty(this.indentationRight)) {
			this.image.setIndentationRight(Float.parseFloat(this.indentationRight));
		}

		if (!StringUtils.isEmpty(this.rotation)) {
			this.image.setRotation(Float.parseFloat(this.rotation));
		}

		if (!StringUtils.isEmpty(this.scale)) {
			this.image.setScale(Float.parseFloat(this.scale));
		}

		if (!StringUtils.isEmpty(this.spacingAfter)) {
			this.image.setSpacingAfter(Float.parseFloat(this.spacingAfter));
		}

		if (!StringUtils.isEmpty(this.spacingBefore)) {
			this.image.setSpacingBefore(Float.parseFloat(this.spacingBefore));
		}

		if (!StringUtils.isEmpty(this.src)) {
			File f = new File(this.src);

			if (f.exists()) {
				this.image.setUrl(this.src);
			} else {
				PageContext context = this.getPageContext();
				this.image.setUrl(context.getServletContext().getRealPath(this.src));
			}
		}

		this.getContainer().add(this.image);
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

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

}
