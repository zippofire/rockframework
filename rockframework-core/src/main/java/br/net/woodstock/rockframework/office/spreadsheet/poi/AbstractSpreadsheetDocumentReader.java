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
package br.net.woodstock.rockframework.office.spreadsheet.poi;

import java.io.IOException;
import java.io.InputStream;


import org.apache.poi.ss.usermodel.Workbook;

import br.net.woodstock.rockframework.office.spreadsheet.Cell;
import br.net.woodstock.rockframework.office.spreadsheet.Row;
import br.net.woodstock.rockframework.office.spreadsheet.Sheet;
import br.net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocument;
import br.net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocumentReader;

abstract class AbstractSpreadsheetDocumentReader implements SpreadsheetDocumentReader {

	protected abstract Workbook getWorkbook(final InputStream inputStream) throws IOException;

	@Override
	public SpreadsheetDocument read(final InputStream inputStream) throws IOException {
		SpreadsheetDocument document = new SpreadsheetDocument();
		Workbook workbook = this.getWorkbook(inputStream);
		int numSheets = workbook.getNumberOfSheets();
		for (int sheetIndex = 0; sheetIndex < numSheets; sheetIndex++) {
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(sheetIndex);
			Sheet s = new Sheet(sheet.getSheetName());

			if (sheet.getPhysicalNumberOfRows() > 0) {
				int numRows = sheet.getLastRowNum();

				for (int rowIndex = 0; rowIndex <= numRows; rowIndex++) {
					org.apache.poi.ss.usermodel.Row row = sheet.getRow(rowIndex);
					this.handleRow(s, row);
				}
				document.getSheets().add(s);
			}
		}
		return document;
	}

	private void handleRow(final Sheet sheet, final org.apache.poi.ss.usermodel.Row row) {
		Row r = new Row();
		if (row.getPhysicalNumberOfCells() > 0) {
			int numCells = row.getLastCellNum();
			for (int cellIndex = 0; cellIndex < numCells; cellIndex++) {
				org.apache.poi.ss.usermodel.Cell cell = row.getCell(cellIndex);
				this.handleCell(r, cell);
			}
		}
		sheet.getRows().add(r);
	}

	private void handleCell(final Row row, final org.apache.poi.ss.usermodel.Cell cell) {
		Cell c = new Cell();

		String value = "";

		if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK) {
			value = cell.getStringCellValue();
		} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN) {
			value = Boolean.toString(cell.getBooleanCellValue());
		} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_ERROR) {
			value = Byte.toString(cell.getErrorCellValue());
		} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA) {
			value = cell.getCellFormula();
		} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
			value = Double.toString(cell.getNumericCellValue());
		} else if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		}

		c.setValue(value);

		row.getCells().add(c);
	}

}
