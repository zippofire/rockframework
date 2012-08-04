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
package br.net.woodstock.rockframework.office.spreadsheet;

public class CellMerge<R, C> implements SpreadsheetElement {

	private static final long	serialVersionUID	= -3884496278809863338L;

	private R					firstRow;

	private R					lastRow;

	private C					firstColumn;

	private C					lastColumn;

	public CellMerge() {
		super();
	}

	public CellMerge(final R firstRow, final R lastRow, final C firstColumn, final C lastColumn) {
		super();
		this.firstRow = firstRow;
		this.lastRow = lastRow;
		this.firstColumn = firstColumn;
		this.lastColumn = lastColumn;
	}

	public R getFirstRow() {
		return this.firstRow;
	}

	public void setFirstRow(final R firstRow) {
		this.firstRow = firstRow;
	}

	public R getLastRow() {
		return this.lastRow;
	}

	public void setLastRow(final R lastRow) {
		this.lastRow = lastRow;
	}

	public C getFirstColumn() {
		return this.firstColumn;
	}

	public void setFirstColumn(final C firstColumn) {
		this.firstColumn = firstColumn;
	}

	public C getLastColumn() {
		return this.lastColumn;
	}

	public void setLastColumn(final C lastColumn) {
		this.lastColumn = lastColumn;
	}

}
