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
package net.woodstock.rockframework.web.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.woodstock.rockframework.utils.NumberUtils;
import net.woodstock.rockframework.utils.StringUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CaptchaImage {

	private static final int	MAX_DRAW	= 3;

	private static final int	MIN_FONT	= 10;

	private static final int	MAX_FONT	= 16;

	private static final int	HEIGHT		= 25;

	private static final int	CHAR_WIDTH	= 20;

	private static List<Font>	fonts;

	private static List<Color>	fontColors;

	private static List<Color>	backgroundColors;

	private String				text;

	public CaptchaImage() {
		super();
	}

	public CaptchaImage(final String text) {
		super();
		this.text = text;
	}

	// Getters and Setters
	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	// Image
	public byte[] getBytes() throws IOException {
		if (StringUtils.isEmpty(this.text)) {
			throw new IllegalArgumentException("Text must be not empty");
		}

		BufferedImage bufferedImage = new BufferedImage(this.getWidth(), CaptchaImage.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bufferedImage.createGraphics();

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		graphics.setBackground(Color.WHITE);
		graphics.fillRect(1, 1, this.getWidth() - 2, CaptchaImage.HEIGHT - 2);

		for (int i = 0; i < 5; i++) {
			this.drawLines(graphics);
			this.drawOvals(graphics);
			this.drawRects(graphics);
		}

		this.drawText(graphics);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(outputStream);

		encoder.encode(bufferedImage);

		return outputStream.toByteArray();
	}

	private void drawLines(final Graphics2D graphics) {
		for (int i = 0; i < CaptchaImage.MAX_DRAW; i++) {
			graphics.setColor(this.getBackgroundColor());
			int x1 = NumberUtils.random(this.getWidth());
			int x2 = NumberUtils.random(this.getWidth());
			int y1 = NumberUtils.random(CaptchaImage.HEIGHT);
			int y2 = NumberUtils.random(CaptchaImage.HEIGHT);
			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	private void drawOvals(final Graphics2D graphics) {
		for (int i = 0; i < CaptchaImage.MAX_DRAW; i++) {
			graphics.setColor(this.getBackgroundColor());
			int x = NumberUtils.random(this.getWidth());
			int y = NumberUtils.random(this.getWidth());
			int width = NumberUtils.random(20);
			int height = NumberUtils.random(20);
			graphics.fillOval(x, y, width, height);
		}
	}

	private void drawRects(final Graphics2D graphics) {
		for (int i = 0; i < CaptchaImage.MAX_DRAW; i++) {
			graphics.setColor(this.getBackgroundColor());
			int x = NumberUtils.random(this.getWidth());
			int y = NumberUtils.random(this.getWidth());
			int width = NumberUtils.random(20);
			int height = NumberUtils.random(20);
			graphics.fillRect(x, y, width, height);
		}
	}

	private void drawText(final Graphics2D graphics) {
		int left = 5;
		for (char c : this.text.toCharArray()) {
			String s = new Character(c).toString();
			Font font = this.getFont();
			TextLayout textLayout = new TextLayout(s, font, graphics.getFontRenderContext());
			Rectangle2D rectangle = textLayout.getBounds();
			int height = (int) rectangle.getHeight();
			int top = Math.abs((height - CaptchaImage.HEIGHT));

			System.out.println("Height: " + height + " Top: " + top + " Char: " + s);

			graphics.setColor(this.getFontColor());
			textLayout.draw(graphics, left, top);
			left += 20;
		}
	}

	private int getWidth() {
		return this.text.length() * CaptchaImage.CHAR_WIDTH;
	}

	private Color getFontColor() {
		int index = NumberUtils.random(CaptchaImage.fontColors.size() - 1);
		return CaptchaImage.fontColors.get(index);
	}

	private Color getBackgroundColor() {
		int index = NumberUtils.random(CaptchaImage.backgroundColors.size() - 1);
		return CaptchaImage.backgroundColors.get(index);
	}

	private Font getFont() {
		int index = NumberUtils.random(CaptchaImage.fonts.size() - 1);
		return CaptchaImage.fonts.get(index);
	}

	// Static
	static {
		CaptchaImage.fonts = new ArrayList<Font>();
		CaptchaImage.fontColors = new ArrayList<Color>();
		CaptchaImage.backgroundColors = new ArrayList<Color>();

		for (int i = CaptchaImage.MIN_FONT; i < CaptchaImage.MAX_FONT; i++) {
			CaptchaImage.fonts.add(new Font(Font.DIALOG, Font.ITALIC, i));
			CaptchaImage.fonts.add(new Font(Font.MONOSPACED, Font.ITALIC, i));
			CaptchaImage.fonts.add(new Font(Font.SANS_SERIF, Font.ITALIC, i));
		}
		for (int i = CaptchaImage.MIN_FONT; i < CaptchaImage.MAX_FONT; i++) {
			CaptchaImage.fonts.add(new Font(Font.DIALOG, Font.BOLD, i));
			CaptchaImage.fonts.add(new Font(Font.MONOSPACED, Font.BOLD, i));
			CaptchaImage.fonts.add(new Font(Font.SANS_SERIF, Font.BOLD, i));
		}
		for (int i = CaptchaImage.MIN_FONT; i < CaptchaImage.MAX_FONT; i++) {
			CaptchaImage.fonts.add(new Font(Font.DIALOG, Font.BOLD | Font.ITALIC, i));
			CaptchaImage.fonts.add(new Font(Font.MONOSPACED, Font.BOLD | Font.ITALIC, i));
			CaptchaImage.fonts.add(new Font(Font.SANS_SERIF, Font.BOLD | Font.ITALIC, i));
		}

		for (int r = 127; r > 0; r -= 4) {
			for (int g = 127; g > 0; g -= 4) {
				for (int b = 127; b > 0; b -= 4) {
					Color c = new Color(r, g, b);
					CaptchaImage.fontColors.add(c);
				}
			}
		}

		for (int r = 127; r < 255; r += 4) {
			for (int g = 127; g < 255; g += 4) {
				for (int b = 127; b < 255; b += 4) {
					Color c = new Color(r, g, b);
					CaptchaImage.backgroundColors.add(c);
				}
			}
		}
	}
}
