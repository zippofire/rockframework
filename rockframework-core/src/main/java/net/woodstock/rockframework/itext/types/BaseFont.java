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

public enum BaseFont {

	DEFAULT(com.lowagie.text.pdf.BaseFont.HELVETICA), HELVETICA(com.lowagie.text.pdf.BaseFont.HELVETICA), HELVETICA_BOLD(
			com.lowagie.text.pdf.BaseFont.HELVETICA_BOLD), HELVETICA_ITALIC(
			com.lowagie.text.pdf.BaseFont.HELVETICA_OBLIQUE), HELVETICA_BOLD_ITALIC(
			com.lowagie.text.pdf.BaseFont.HELVETICA_BOLDOBLIQUE), COURIER(
			com.lowagie.text.pdf.BaseFont.COURIER), COURIER_BOLD(com.lowagie.text.pdf.BaseFont.COURIER_BOLD), COURIER_ITALIC(
			com.lowagie.text.pdf.BaseFont.COURIER_OBLIQUE), COURIER_BOLD_ITALIC(
			com.lowagie.text.pdf.BaseFont.COURIER_BOLDOBLIQUE), TIMES(
			com.lowagie.text.pdf.BaseFont.TIMES_ROMAN), TIMES_BOLD(com.lowagie.text.pdf.BaseFont.TIMES_BOLD), TIMES_ITALIC(
			com.lowagie.text.pdf.BaseFont.TIMES_ITALIC), TIMES_BOLD_ITALIC(
			com.lowagie.text.pdf.BaseFont.TIMES_BOLDITALIC);

	private String	font;

	private BaseFont(String font) {
		this.font = font;
	}

	public String getFont() {
		return this.font;
	}

}
