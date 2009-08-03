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

import java.io.IOException;
import java.net.MalformedURLException;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;

import net.woodstock.rockframework.utils.StringUtils;

public class ItextImage extends ItextBasicImpl {

	private static final long	serialVersionUID	= 1L;

	private int					angle				= 0;

	private Image				image				= null;

	private String				imageUrl			= StringUtils.BLANK;

	private float				scale				= 100;

	public ItextImage() {
		super();
	}

	public int getAngle() {
		return this.angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public float getScale() {
		return this.scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void write(Document document, PdfContentByte content) throws DocumentException {
		if (this.image == null) {
			try {
				this.image = Image.getInstance(this.imageUrl);
			} catch (BadElementException e) {
				throw new DocumentException(e);
			} catch (MalformedURLException e) {
				throw new DocumentException(e);
			} catch (IOException e) {
				throw new DocumentException(e);
			}
		}
		this.image.scalePercent(this.scale);
		this.image.setAbsolutePosition(this.left, document.getPageSize().top() - this.top);
		this.image.setTransparency(new int[] { 255, 255 });
		this.image.setRotation((float) Math.toRadians(this.angle));
		content.addImage(this.image);
	}

}
