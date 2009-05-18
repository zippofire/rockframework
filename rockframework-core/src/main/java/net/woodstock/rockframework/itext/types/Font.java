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

public enum Font {

	COURIER(com.lowagie.text.Font.COURIER), DEFAULT(com.lowagie.text.Font.HELVETICA), HELVETICA(
			com.lowagie.text.Font.HELVETICA), SYMBOL(com.lowagie.text.Font.SYMBOL), TIMES(
			com.lowagie.text.Font.TIMES_ROMAN), ZAPFDINGBATS(com.lowagie.text.Font.ZAPFDINGBATS);

	private int	font;

	private Font(int font) {
		this.font = font;
	}

	public int getFont() {
		return this.font;
	}

}
