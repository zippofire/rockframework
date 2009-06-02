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

import java.util.LinkedList;
import java.util.List;

public class Row implements SpreadsheetElement {

	private static final long	serialVersionUID	= -6431558666911367368L;

	private int					height;

	private List<Cell>			cells;

	public Row() {
		super();
		this.cells = new LinkedList<Cell>();
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<Cell> getCells() {
		return this.cells;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

}
