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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import br.net.woodstock.rockframework.util.Assert;


public final class Image {

	private BufferedImage	bufferedImage;

	private int				height;

	private int				width;

	private Image(final BufferedImage bufferedImage) {
		super();
		Assert.notNull(bufferedImage, "bufferedImage");
		this.bufferedImage = bufferedImage;
		this.height = this.bufferedImage.getHeight();
		this.width = this.bufferedImage.getWidth();
	}

	public void write(final File file, final ImageType type) throws IOException {
		Assert.notNull(file, "file");
		Assert.notNull(type, "type");
		ImageIO.write(this.bufferedImage, type.name().toLowerCase(), file);
	}

	public void write(final OutputStream outputStream, final ImageType type) throws IOException {
		Assert.notNull(outputStream, "outputStream");
		Assert.notNull(type, "type");

		ImageIO.write(this.bufferedImage, type.name().toLowerCase(), outputStream);
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public BufferedImage getBuffered() {
		return this.bufferedImage;
	}

	// Static
	public static Image read(final InputStream inputStream) throws IOException {
		Assert.notNull(inputStream, "inputStream");
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		return new Image(bufferedImage);
	}

	public static Image read(final URL url) throws IOException {
		Assert.notNull(url, "url");
		BufferedImage bufferedImage = ImageIO.read(url);
		return new Image(bufferedImage);
	}

	public static Image toImage(final BufferedImage bufferedImage) {
		Assert.notNull(bufferedImage, "bufferedImage");
		return new Image(bufferedImage);
	}

}
