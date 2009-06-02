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
package net.woodstock.rockframework.office.spreadsheet.poi;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map.Entry;

import net.woodstock.rockframework.office.DocumentException;
import net.woodstock.rockframework.office.spreadsheet.Alignment;
import net.woodstock.rockframework.office.spreadsheet.Cell;
import net.woodstock.rockframework.office.spreadsheet.CellMerge;
import net.woodstock.rockframework.office.spreadsheet.Color;
import net.woodstock.rockframework.office.spreadsheet.Image;
import net.woodstock.rockframework.office.spreadsheet.IntegerCellMerge;
import net.woodstock.rockframework.office.spreadsheet.Row;
import net.woodstock.rockframework.office.spreadsheet.Sheet;
import net.woodstock.rockframework.office.spreadsheet.SpreadsheetDocument;
import net.woodstock.rockframework.office.spreadsheet.VerticalAlignment;
import net.woodstock.rockframework.office.spreadsheet.Image.ImageType;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;

public class HSSFSpreadsheetDocument extends SpreadsheetDocument {

	private static final long	serialVersionUID	= 1374393143817935260L;

	public HSSFSpreadsheetDocument() {
		super();
	}

	public void write(OutputStream outputStream) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (Sheet sheet : this.getSheets()) {
			this.handleSheet(workbook, sheet);
		}
		workbook.write(outputStream);
	}

	private void handleSheet(HSSFWorkbook workbook, Sheet sheet) {
		HSSFSheet s = workbook.createSheet(sheet.getName());
		s.setDisplayGridlines(sheet.isDisplayGridLines());
		s.setPrintGridlines(sheet.isPrintGridLines());
		s.setFitToPage(sheet.isFitToPage());
		s.setHorizontallyCenter(sheet.isHorizontallyCenter());
		s.setAutobreaks(true);

		// Print Config
		HSSFPrintSetup printSetup = s.getPrintSetup();
		printSetup.setLandscape(sheet.isLandscape());
		printSetup.setFitHeight((short) 1);
		printSetup.setFitWidth((short) 1);

		// Rows
		int rowNum = 0;

		for (Row row : sheet.getRows()) {
			this.handleRow(workbook, s, row, rowNum++);
		}

		// Widths
		for (Entry<Integer, Integer> entry : sheet.getColumnsWith().entrySet()) {
			int column = entry.getKey().intValue();
			int value = entry.getValue().intValue();
			s.setColumnWidth(column, 256 * value);
		}

		// Merge
		for (CellMerge<?, ?> cellMerge : sheet.getMerges()) {
			IntegerCellMerge integerCellMerge = (IntegerCellMerge) cellMerge;
			int firstRow = integerCellMerge.getFirstRow().intValue();
			int lastRow = integerCellMerge.getLastRow().intValue();
			int firstColumn = integerCellMerge.getFirstColumn().intValue();
			int lastColumn = integerCellMerge.getLastColumn().intValue();
			CellRangeAddress address = new CellRangeAddress(firstRow, lastRow, firstColumn, lastColumn);
			s.addMergedRegion(address);
		}

		// Images
		for (Image image : sheet.getImages()) {
			byte[] bytes = image.getBytes();
			ImageType imageType = image.getType();
			int type = -1;

			switch (imageType) {
				case JPEG:
					type = HSSFWorkbook.PICTURE_TYPE_JPEG;
					break;
				case PNG:
					type = HSSFWorkbook.PICTURE_TYPE_PNG;
					break;
				default:
					break;
			}

			if (type == -1) {
				throw new DocumentException("Unsupported image type " + imageType);
			}

			short row = (short) image.getRow();
			short column = (short) image.getColumn();
			// int width = Math.abs(image.getWidth());
			// int height = Math.abs(image.getHeight());

			int index = workbook.addPicture(bytes, type);
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, row, column, row, column);

			HSSFPatriarch patriarch = s.createDrawingPatriarch();
			HSSFPicture picture = patriarch.createPicture(anchor, index);

			picture.resize();

			// TODO
			// if ((width != 0) && (height != 0)) {
			// picture.getImageDimension().setSize(width, height);
			// }
		}
	}

	private void handleRow(HSSFWorkbook workbook, HSSFSheet sheet, Row row, int rowNum) {
		HSSFRow r = sheet.createRow(rowNum);
		r.setHeightInPoints(row.getHeight());

		int cellNum = 0;

		for (Cell cell : row.getCells()) {
			this.handleCell(workbook, r, cell, cellNum++);
		}
	}

	private void handleCell(HSSFWorkbook workbook, HSSFRow row, Cell cell, int cellNum) {
		HSSFCell c = row.createCell(cellNum);

		HSSFFont font = workbook.createFont();
		font.setColor(this.getColor(workbook, cell.getFontColor()));
		font.setFontName(cell.getFont());
		font.setFontHeightInPoints((short) cell.getFontSize());

		if (cell.isBold()) {
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}

		if (cell.isItalic()) {
			font.setItalic(true);
		}

		if (cell.isStrikeout()) {
			font.setStrikeout(true);
		}

		if (cell.isUnderline()) {
			font.setUnderline(HSSFFont.U_SINGLE);
		}

		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(this.getAlignment(cell.getAlignment()));
		style.setVerticalAlignment(this.getVerticalAlignment(cell.getVerticalAlignment()));
		style.setWrapText(cell.isWrap());
		style.setFont(font);

		if (cell.getLeftBorder() != null) {
			style.setBorderLeft((short) cell.getLeftBorder().getWidth());
			style.setLeftBorderColor(this.getColor(workbook, cell.getLeftBorder().getColor()));
		}

		if (cell.getTopBorder() != null) {
			style.setBorderTop((short) cell.getTopBorder().getWidth());
			style.setTopBorderColor(this.getColor(workbook, cell.getTopBorder().getColor()));
		}

		if (cell.getRightBorder() != null) {
			style.setBorderRight((short) cell.getRightBorder().getWidth());
			style.setRightBorderColor(this.getColor(workbook, cell.getRightBorder().getColor()));
		}

		if (cell.getBottomBorder() != null) {
			style.setBorderBottom((short) cell.getBottomBorder().getWidth());
			style.setBottomBorderColor(this.getColor(workbook, cell.getBottomBorder().getColor()));
		}

		if (cell.getBackgroundColor() != null) {
			style.setFillForegroundColor(this.getBackgroundColor(workbook, cell.getBackgroundColor()));
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		}

		HSSFRichTextString string = new HSSFRichTextString(cell.getValue());
		c.setCellValue(string);
		c.setCellStyle(style);
	}

	private short getAlignment(Alignment alignment) {
		switch (alignment) {
			case CENTER:
				return HSSFCellStyle.ALIGN_CENTER;
			case JUSTIFY:
				return HSSFCellStyle.ALIGN_JUSTIFY;
			case LEFT:
				return HSSFCellStyle.ALIGN_LEFT;
			case RIGHT:
				return HSSFCellStyle.ALIGN_RIGHT;
			default:
				return HSSFCellStyle.ALIGN_LEFT;
		}
	}

	private short getVerticalAlignment(VerticalAlignment verticalAlignment) {
		switch (verticalAlignment) {
			case BOTTOM:
				return HSSFCellStyle.VERTICAL_BOTTOM;
			case CENTER:
				return HSSFCellStyle.VERTICAL_CENTER;
			case TOP:
				return HSSFCellStyle.VERTICAL_TOP;
			default:
				return HSSFCellStyle.VERTICAL_TOP;
		}
	}

	private short getColor(HSSFWorkbook workbook, Color color) {
		HSSFColor c = this.getHSSFColor(workbook, color);
		if (c == null) {
			return HSSFColor.BLACK.index;
		}
		return c.getIndex();
	}

	private short getBackgroundColor(HSSFWorkbook workbook, Color color) {
		HSSFColor c = this.getHSSFColor(workbook, color);
		if (c == null) {
			return HSSFColor.WHITE.index;
		}
		return c.getIndex();
	}

	private HSSFColor getHSSFColor(HSSFWorkbook workbook, Color color) {
		byte red = (byte) color.getRed();
		byte green = (byte) color.getGreen();
		byte blue = (byte) color.getBlue();
		HSSFPalette palette = workbook.getCustomPalette();

		HSSFColor c = palette.findSimilarColor(red, green, blue);
		if (c == null) {
			try {
				c = palette.addColor(red, green, blue);
			} catch (RuntimeException e) {
				c = palette.findSimilarColor(red, green, blue);
			}
		}
		return c;
	}

}
