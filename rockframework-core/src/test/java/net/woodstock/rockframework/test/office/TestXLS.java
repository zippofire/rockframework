package net.woodstock.rockframework.test.office;

import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.office.spreadsheet.Cell;
import net.woodstock.rockframework.office.spreadsheet.Row;
import net.woodstock.rockframework.office.spreadsheet.Sheet;
import net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocument;
import net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocumentReader;
import net.woodstock.rockframework.office.spreadsheet.poi.HSSFSpreadsheetDocumentReader;

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
