package net.woodstock.rockframework.test.office;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.office.spreadsheet.Border;
import net.woodstock.rockframework.office.spreadsheet.Cell;
import net.woodstock.rockframework.office.spreadsheet.Color;
import net.woodstock.rockframework.office.spreadsheet.Image;
import net.woodstock.rockframework.office.spreadsheet.IntegerCellMerge;
import net.woodstock.rockframework.office.spreadsheet.Row;
import net.woodstock.rockframework.office.spreadsheet.Sheet;
import net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocument;
import net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocumentWriter;
import net.woodstock.rockframework.office.spreadsheet.Image.ImageType;
import net.woodstock.rockframework.office.spreadsheet.poi.HSSFSpreadsheetDocumentWriter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

public class Test1 extends TestCase {

	public void xtest1() throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("Planilha 1");
		sheet.setDisplayGridlines(true);
		sheet.setPrintGridlines(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		sheet.setAutobreaks(true);

		sheet.setColumnWidth(2, 256 * 50);

		HSSFPrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		printSetup.setFitHeight((short) 1);
		printSetup.setFitWidth((short) 1);

		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(20);

		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		font.setColor(HSSFColor.WHITE.index);
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 11);

		// Merge
		CellRangeAddress address = new CellRangeAddress(0, 0, 0, 5);
		sheet.addMergedRegion(address);

		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);
		cellStyle.setFont(font);

		// BG
		cellStyle.setFillForegroundColor(HSSFColor.RED.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		// Border
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);

		for (int i = 0; i < 10; i++) {
			HSSFCell cell = row.createCell(i);
			HSSFRichTextString string = new HSSFRichTextString("Celula " + i);
			cell.setCellValue(string);
			cell.setCellStyle(cellStyle);
		}

		FileOutputStream fos = new FileOutputStream("D:/teste1.xls");
		workbook.write(fos);
		fos.close();
	}

	public void test2() throws Exception {
		SpreadsheetDocument document = new SpreadsheetDocument();

		Sheet sheet = new Sheet("Planilha 1");
		sheet.setLandscape(true);

		Row row = new Row();
		row.setHeight(20);

		sheet.getMerges().add(new IntegerCellMerge(0, 0, 0, 5));

		sheet.getImages().add(new Image(new FileInputStream("D:/image001.jpg"), 0, 0, ImageType.JPEG, 80, 60));

		for (int i = 0; i < 10; i++) {
			Cell cell = new Cell();
			cell.setValue("Celula " + i);
			cell.setBackgroundColor(new Color(192, 192, 192));
			cell.setUnderline(true);
			cell.setBold(true);
			cell.setItalic(true);

			cell.setFont("Times New Roman");
			cell.setFontSize(10);
			cell.setFontColor(new Color(255, 0, 0));

			Border border = new Border();
			border.setWidth(1);
			border.setColor(new Color(0, 0, 0));
			cell.setLeftBorder(border);
			cell.setTopBorder(border);
			cell.setRightBorder(border);
			cell.setBottomBorder(border);

			row.getCells().add(cell);
			sheet.getColumnsWith().put(new Integer(i), new Integer(25));
		}

		sheet.getRows().add(row);
		document.getSheets().add(sheet);

		FileOutputStream fos = new FileOutputStream("D:/teste2.xls");
		SpreadsheetDocumentWriter writer = HSSFSpreadsheetDocumentWriter.getInstance();

		writer.write(document, fos);
		fos.close();
	}

}
