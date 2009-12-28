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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sheet implements SpreadsheetElement {

	private static final long		serialVersionUID	= -5265280416598763448L;

	private boolean					displayGridLines;

	private boolean					printGridLines;

	private boolean					fitToPage;

	private boolean					horizontallyCenter;

	private boolean					landscape;

	private String					name;

	private List<Row>				rows;

	private List<CellMerge<?, ?>>	merges;

	private List<Image>				images;

	private Map<Integer, Integer>	columnsWith;

	public Sheet(final String name) {
		super();
		this.name = name;
		this.displayGridLines = true;
		this.printGridLines = true;
		this.fitToPage = true;
		this.horizontallyCenter = true;
		this.rows = new LinkedList<Row>();
		this.merges = new LinkedList<CellMerge<?, ?>>();
		this.images = new LinkedList<Image>();
		this.columnsWith = new HashMap<Integer, Integer>();
	}

	public boolean isDisplayGridLines() {
		return this.displayGridLines;
	}

	public void setDisplayGridLines(final boolean displayGridLines) {
		this.displayGridLines = displayGridLines;
	}

	public boolean isPrintGridLines() {
		return this.printGridLines;
	}

	public void setPrintGridLines(final boolean printGridLines) {
		this.printGridLines = printGridLines;
	}

	public boolean isFitToPage() {
		return this.fitToPage;
	}

	public void setFitToPage(final boolean fitToPage) {
		this.fitToPage = fitToPage;
	}

	public boolean isHorizontallyCenter() {
		return this.horizontallyCenter;
	}

	public void setHorizontallyCenter(final boolean horizontallyCenter) {
		this.horizontallyCenter = horizontallyCenter;
	}

	public boolean isLandscape() {
		return this.landscape;
	}

	public void setLandscape(final boolean landscape) {
		this.landscape = landscape;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<Row> getRows() {
		return this.rows;
	}

	public void setRows(final List<Row> rows) {
		this.rows = rows;
	}

	public List<CellMerge<?, ?>> getMerges() {
		return this.merges;
	}

	public void setMerges(final List<CellMerge<?, ?>> merges) {
		this.merges = merges;
	}

	public List<Image> getImages() {
		return this.images;
	}

	public void setImages(final List<Image> images) {
		this.images = images;
	}

	public Map<Integer, Integer> getColumnsWith() {
		return this.columnsWith;
	}

	public void setColumnsWith(final Map<Integer, Integer> columnsWith) {
		this.columnsWith = columnsWith;
	}

}
