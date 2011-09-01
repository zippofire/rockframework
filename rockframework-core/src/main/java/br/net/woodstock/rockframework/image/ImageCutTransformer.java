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
package br.net.woodstock.rockframework.image;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import br.net.woodstock.rockframework.util.Assert;


public class ImageCutTransformer implements ImageTransformer, Serializable {

	private static final long	serialVersionUID	= 8048865488355909521L;

	private int					x;

	private int					y;

	private int					width;

	private int					height;

	public ImageCutTransformer(final int x, final int y, final int width, final int height) {
		super();
		Assert.greaterOrEqual(x, 0, "x");
		Assert.greaterOrEqual(y, 0, "y");
		Assert.greaterThan(width, 0, "width");
		Assert.greaterThan(height, 0, "height");
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public Image transform(final Image image) {
		BufferedImage bufferedImage = image.getBuffered();

		int imageWidth = bufferedImage.getWidth();
		int imageHeight = bufferedImage.getHeight();

		int x = this.x;
		int y = this.y;
		int w = this.width;
		int h = this.height;

		if (this.x + w > imageWidth) {
			x = imageWidth - w;
			if (x < 0) {
				x = 0;
				w = imageWidth;
			}
		}

		if (this.y + h > imageHeight) {
			y = imageHeight - h;
			if (y < 0) {
				y = 0;
				h = imageHeight;
			}
		}

		BufferedImage subImage = bufferedImage.getSubimage(x, y, w, h);
		return Image.toImage(subImage);
	}
}
