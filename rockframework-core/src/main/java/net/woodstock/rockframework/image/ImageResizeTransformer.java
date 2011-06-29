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
package net.woodstock.rockframework.image;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import net.woodstock.rockframework.util.Assert;

public class ImageResizeTransformer implements ImageTransformer, Serializable {

	private static final long	serialVersionUID	= 416003072718277672L;

	private int					percent				= -1;

	private int					width				= -1;

	private int					height				= -1;

	private boolean				fixRatio;

	public ImageResizeTransformer(final int percent) {
		super();
		Assert.greaterThan(percent, 0, "percent");
		this.percent = percent;
	}

	public ImageResizeTransformer(final int width, final int height, final boolean fixRatio) {
		super();
		Assert.greaterThan(width, 0, "width");
		Assert.greaterThan(height, 0, "height");
		this.width = width;
		this.height = height;
		this.fixRatio = fixRatio;
	}

	@Override
	public Image transform(final Image image) {
		if (this.percent != -1) {
			return this.resizeByPercent(image);
		}

		return this.resizeByWidthAndHeight(image);
	}

	private Image resizeByPercent(final Image image) {
		float fw = (image.getWidth() * 1.0f) * ((this.percent * 1.0f) / 100f);
		float fh = (image.getHeight() * 1.0f) * ((this.percent * 1.0f) / 100f);

		int width = (int) fw;
		int height = (int) fh;

		return this.doResize(image, width, height);
	}

	private Image resizeByWidthAndHeight(final Image image) {
		int w = this.width;
		int h = this.height;

		if (this.fixRatio) {
			int percentWidth = (this.width * 100) / image.getWidth();
			int percentHeight = (this.height * 100) / image.getHeight();

			if (percentWidth != percentHeight) {
				if (percentWidth > percentHeight) {
					float f = (image.getWidth() * 1.0f) * ((percentHeight * 1.0f) / 100f);
					w = (int) f;
				} else {
					float f = (image.getHeight() * 1.0f) * ((percentWidth * 1.0f) / 100f);
					h = (int) f;
				}
			}
		}

		return this.doResize(image, w, h);
	}

	private Image doResize(final Image image, final int width, final int height) {
		int type = image.getBuffered().getType();

		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image.getBuffered(), 0, 0, width, height, null);
		g.dispose();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		return Image.toImage(resizedImage);
	}

}
