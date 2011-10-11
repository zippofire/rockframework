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
package br.net.woodstock.rockframework.utils;

import java.awt.Color;

import br.net.woodstock.rockframework.util.LPadTransformer;

public abstract class ColorUtils {

	private ColorUtils() {
		//
	}

	public static Color createColor(final String s) {
		if (s.startsWith("#")) {
			return ColorUtils.createColorHTML(s);
		} else if (s.startsWith("rgb")) {
			return ColorUtils.createColorRGB(s);
		}
		return null;
	}

	public static Color createColorHTML(final String s) {
		int red = 0;
		int green = 0;
		int blue = 0;
		if ((s.startsWith("#")) && (s.length() == 7)) {
			red = Integer.decode("0x" + s.substring(1, 3)).intValue();
			green = Integer.decode("0x" + s.substring(3, 5)).intValue();
			blue = Integer.decode("0x" + s.substring(5)).intValue();
		}

		return new Color(red, green, blue);
	}

	public static Color createColorRGB(final String s) {
		int red = 0;
		int green = 0;
		int blue = 0;
		if ((s.startsWith("rgb(")) && (s.endsWith(")"))) {
			String[] colors = s.replaceAll("rgb\\(", "").replaceAll("\\)", "").split(",");
			if (colors.length == 3) {
				red = Integer.parseInt(colors[0].trim());
				green = Integer.parseInt(colors[1].trim());
				blue = Integer.parseInt(colors[2].trim());
			}
		}
		return new Color(red, green, blue);
	}

	public static Color createColorRGB(final int red, final int green, final int blue) {
		return new Color(red, green, blue);
	}

	public static String toStringHTML(final Color c) {
		StringBuilder b = new StringBuilder();
		LPadTransformer transform = new LPadTransformer(2, '0');
		b.append("#");
		b.append(transform.transform(Integer.toHexString(c.getRed()).toUpperCase()));
		b.append(transform.transform(Integer.toHexString(c.getGreen()).toUpperCase()));
		b.append(transform.transform(Integer.toHexString(c.getBlue()).toUpperCase()));
		return b.toString();
	}

	public static String toStringRGB(final Color c) {
		StringBuilder b = new StringBuilder();
		b.append("rgb(");
		b.append(c.getRed());
		b.append(",");
		b.append(c.getGreen());
		b.append(",");
		b.append(c.getBlue());
		b.append(")");
		return b.toString();
	}

}
