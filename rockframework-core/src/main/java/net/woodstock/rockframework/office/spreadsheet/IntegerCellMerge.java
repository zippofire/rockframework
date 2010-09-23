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
package net.woodstock.rockframework.office.spreadsheet;

public class IntegerCellMerge extends CellMerge<Integer, Integer> {

	private static final long	serialVersionUID	= -8141176230372029827L;

	public IntegerCellMerge() {
		super();
	}

	public IntegerCellMerge(final Integer firstRow, final Integer lastRow, final Integer firstColumn, final Integer lastColumn) {
		super(firstRow, lastRow, firstColumn, lastColumn);
	}

	public IntegerCellMerge(final int firstRow, final int lastRow, final int firstColumn, final int lastColumn) {
		super(Integer.valueOf(firstRow), Integer.valueOf(lastRow), Integer.valueOf(firstColumn), Integer.valueOf(lastColumn));
	}

}