package br.net.woodstock.rockframework.test.office;

import java.io.FileInputStream;
import java.io.InputStream;

import br.net.woodstock.rockframework.office.spreadsheet.Cell;
import br.net.woodstock.rockframework.office.spreadsheet.Row;
import br.net.woodstock.rockframework.office.spreadsheet.Sheet;
import br.net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocument;
import br.net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocumentReader;
import br.net.woodstock.rockframework.office.spreadsheet.poi.HSSFSpreadsheetDocumentReader;

import junit.framework.TestCase;

public class TestXLS extends TestCase {

	public void xtest1() throws Exception {
		InputStream inputStream = new FileInputStream("C:/temp/teste.xls");
		SpreadsheetDocumentReader reader = HSSFSpreadsheetDocumentReader.getInstance();
		SpreadsheetDocument document = reader.read(inputStream);
		for (Sheet sheet : document.getSheets()) {
			for (Row row : sheet.getRows()) {
				for (Cell cell : row.getCells()) {
					System.out.print("|" + cell.getValue() + "|");
				}
				System.out.println();
			}
		}
	}

}
