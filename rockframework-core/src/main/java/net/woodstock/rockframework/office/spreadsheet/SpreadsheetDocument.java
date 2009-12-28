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

import net.woodstock.rockframework.office.AbstractDocument;

public abstract class SpreadsheetDocument extends AbstractDocument {

	private static final long	serialVersionUID	= 6813958886966892724L;

	private List<Sheet>			sheets;

	public SpreadsheetDocument() {
		super();
		this.sheets = new LinkedList<Sheet>();
	}

	public List<Sheet> getSheets() {
		return this.sheets;
	}

	public void setSheets(final List<Sheet> sheets) {
		this.sheets = sheets;
	}

}
