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

public class Cell implements SpreadsheetElement {

	private static final long				serialVersionUID			= -6431558666911367368L;

	public static final String				DEFAULT_FONT				= "Arial";

	public static final int					DEFAULT_FONT_SIZE			= 10;

	public static final Color				DEFAULT_FONT_COLOR			= new Color(0, 0, 0);

	public static final Alignment			DEFAULT_ALIGNMENT			= Alignment.LEFT;

	public static final VerticalAlignment	DEFAULT_CERTICAL_ALIGMENT	= VerticalAlignment.TOP;

	private boolean							wrap;

	private boolean							bold;

	private boolean							italic;

	private boolean							strikeout;

	private boolean							underline;

	private String							font;

	private int								fontSize;

	private Color							fontColor;

	private Color							backgroundColor;

	private Border							leftBorder;

	private Border							topBorder;

	private Border							rightBorder;

	private Border							bottomBorder;

	private Alignment						alignment;

	private VerticalAlignment				verticalAlignment;

	private String							value;

	public Cell() {
		super();
		this.font = Cell.DEFAULT_FONT;
		this.fontSize = Cell.DEFAULT_FONT_SIZE;
		this.fontColor = Cell.DEFAULT_FONT_COLOR;
		this.alignment = Cell.DEFAULT_ALIGNMENT;
		this.verticalAlignment = Cell.DEFAULT_CERTICAL_ALIGMENT;
	}

	public Cell(String value) {
		this();
		this.value = value;
	}

	public boolean isWrap() {
		return this.wrap;
	}

	public void setWrap(boolean wrap) {
		this.wrap = wrap;
	}

	public boolean isBold() {
		return this.bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return this.italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public boolean isStrikeout() {
		return this.strikeout;
	}

	public void setStrikeout(boolean strikeout) {
		this.strikeout = strikeout;
	}

	public boolean isUnderline() {
		return this.underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public String getFont() {
		return this.font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public Color getFontColor() {
		return this.fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Border getLeftBorder() {
		return this.leftBorder;
	}

	public void setLeftBorder(Border leftBorder) {
		this.leftBorder = leftBorder;
	}

	public Border getTopBorder() {
		return this.topBorder;
	}

	public void setTopBorder(Border topBorder) {
		this.topBorder = topBorder;
	}

	public Border getRightBorder() {
		return this.rightBorder;
	}

	public void setRightBorder(Border rightBorder) {
		this.rightBorder = rightBorder;
	}

	public Border getBottomBorder() {
		return this.bottomBorder;
	}

	public void setBottomBorder(Border bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	public Alignment getAlignment() {
		return this.alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public VerticalAlignment getVerticalAlignment() {
		return this.verticalAlignment;
	}

	public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
