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
package net.woodstock.rockframework.itext.types;

public enum FontStyle {

	BOLD(com.lowagie.text.Font.BOLD), BOLDITALIC(com.lowagie.text.Font.BOLDITALIC), DEFAULT(
			com.lowagie.text.Font.NORMAL), ITALIC(com.lowagie.text.Font.ITALIC), NORMAL(
			com.lowagie.text.Font.NORMAL), STRIKETHRU(com.lowagie.text.Font.STRIKETHRU), UNDERLINE(
			com.lowagie.text.Font.UNDERLINE);

	private int	fontStyle;

	private FontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}

	public int getFontStyle() {
		return this.fontStyle;
	}

}
