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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocumentReader;

public final class XSSFSpreadsheetDocumentReader extends AbstractSpreadsheetDocumentReader {

	private static SpreadsheetDocumentReader	instance	= new XSSFSpreadsheetDocumentReader();

	private XSSFSpreadsheetDocumentReader() {
		super();
	}

	@Override
	protected Workbook getWorkbook(final InputStream inputStream) throws IOException {
		return new XSSFWorkbook(inputStream);
	}

	// Instance
	public static SpreadsheetDocumentReader getInstance() {
		return XSSFSpreadsheetDocumentReader.instance;
	}

}
