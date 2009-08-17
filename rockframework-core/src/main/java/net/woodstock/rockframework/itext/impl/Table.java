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
package net.woodstock.rockframework.itext.impl;

import java.util.ArrayList;
import java.util.Collection;

import net.woodstock.rockframework.itext.types.Alignment;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

public class Table extends ObjectBase {

	private static final long	serialVersionUID	= 2409125692861023173L;

	private Alignment			alignment;

	private int					columns;

	private float				spacingAfter;

	private float				spacingBefore;

	private float				totalWidth;

	private Collection<Cell>	cells;

	public Table() {
		super();
		this.alignment = Alignment.DEFAULT;
		this.columns = 1;
		this.spacingAfter = Float.MIN_VALUE;
		this.spacingBefore = Float.MIN_VALUE;
		this.totalWidth = Float.MIN_VALUE;

		this.cells = new ArrayList<Cell>();
	}

	public Element getObject() {
		PdfPTable table = new PdfPTable(this.columns);

		if (this.totalWidth > 0) {
			table.setTotalWidth(this.totalWidth);
			if (this.cells.size() > 0) {
				float[] size = new float[this.columns];
				boolean ok = false;
				for (int i = 0; i < this.columns; i++) {
					Cell cell = ((ArrayList<Cell>) this.cells).get(i);
					if (cell.getWidth() > 0) {
						ok = true;
						size[i] = cell.getWidth();
					} else {
						break;
					}
				}
				if (ok) {
					try {
						table.setWidths(size);
					} catch (DocumentException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		if (this.alignment != Alignment.DEFAULT) {
			table.setHorizontalAlignment(this.alignment.getAlignment());
		}

		if (this.spacingAfter != Float.MIN_VALUE) {
			table.setSpacingAfter(this.spacingAfter);
		}

		if (this.spacingBefore != Float.MIN_VALUE) {
			table.setSpacingBefore(this.spacingBefore);
		}

		for (Cell cell : this.cells) {
			table.addCell((PdfPCell) cell.getObject());
		}

		return table;
	}

	public void addCell(Cell cell) {
		this.cells.add(cell);
	}

	public Collection<Cell> getCells() {
		return this.cells;
	}

	public void removeCell(int index) {
		((ArrayList<Cell>) this.cells).remove(index);
	}

	public void removeCell(Cell cell) {
		this.cells.remove(cell);
	}

	public void setCells(Collection<Cell> cells) {
		if (this.cells.size() > 0) {
			while (this.cells.size() > 0) {
				((ArrayList<Cell>) this.cells).remove(0);
			}
		}

		for (Cell cell : cells) {
			this.cells.add(cell);
		}

	}

	// Getters and Setters
	public Alignment getAlignment() {
		return this.alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public int getColumns() {
		return this.columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public float getSpacingAfter() {
		return this.spacingAfter;
	}

	public void setSpacingAfter(float spacingAfter) {
		this.spacingAfter = spacingAfter;
	}

	public float getSpacingBefore() {
		return this.spacingBefore;
	}

	public void setSpacingBefore(float spacingBefore) {
		this.spacingBefore = spacingBefore;
	}

	public float getTotalWidth() {
		return this.totalWidth;
	}

	public void setTotalWidth(float totalWidth) {
		this.totalWidth = totalWidth;
	}

}
