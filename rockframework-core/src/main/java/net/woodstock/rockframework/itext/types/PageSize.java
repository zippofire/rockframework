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

import com.lowagie.text.Rectangle;

public enum PageSize {

	A0(com.lowagie.text.PageSize.A0), A1(com.lowagie.text.PageSize.A1), A2(com.lowagie.text.PageSize.A2), A3(
			com.lowagie.text.PageSize.A3), A4(com.lowagie.text.PageSize.A4), A5(com.lowagie.text.PageSize.A5), A6(
			com.lowagie.text.PageSize.A6), A7(com.lowagie.text.PageSize.A7), A8(com.lowagie.text.PageSize.A8), A9(
			com.lowagie.text.PageSize.A9), A10(com.lowagie.text.PageSize.A10), B0(
			com.lowagie.text.PageSize.B0), B1(com.lowagie.text.PageSize.B1), B2(com.lowagie.text.PageSize.B2), B3(
			com.lowagie.text.PageSize.B3), B4(com.lowagie.text.PageSize.B4), B5(com.lowagie.text.PageSize.B5), DEFAULT(
			com.lowagie.text.PageSize.A4), LEDGER(com.lowagie.text.PageSize.LEDGER), LEGAL(
			com.lowagie.text.PageSize.LEGAL), LETTER(com.lowagie.text.PageSize.LETTER), NOTE(
			com.lowagie.text.PageSize.NOTE);

	private Rectangle	pageSize;

	private PageSize(Rectangle pageSize) {
		this.pageSize = pageSize;
	}

	public Rectangle getPageSize() {
		return this.pageSize;
	}

}
