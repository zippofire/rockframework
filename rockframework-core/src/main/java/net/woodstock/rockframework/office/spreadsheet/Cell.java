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

	private Object							value;

	private CellType						type;

	public Cell() {
		super();
		this.font = Cell.DEFAULT_FONT;
		this.fontSize = Cell.DEFAULT_FONT_SIZE;
		this.fontColor = Cell.DEFAULT_FONT_COLOR;
		this.alignment = Cell.DEFAULT_ALIGNMENT;
		this.verticalAlignment = Cell.DEFAULT_CERTICAL_ALIGMENT;
	}

	public Cell(final Object value) {
		this(value, CellType.TEXT);
	}

	public Cell(final Object value, final CellType type) {
		this();
		this.value = value;
		this.type = type;
	}

	public boolean isWrap() {
		return this.wrap;
	}

	public void setWrap(final boolean wrap) {
		this.wrap = wrap;
	}

	public boolean isBold() {
		return this.bold;
	}

	public void setBold(final boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return this.italic;
	}

	public void setItalic(final boolean italic) {
		this.italic = italic;
	}

	public boolean isStrikeout() {
		return this.strikeout;
	}

	public void setStrikeout(final boolean strikeout) {
		this.strikeout = strikeout;
	}

	public boolean isUnderline() {
		return this.underline;
	}

	public void setUnderline(final boolean underline) {
		this.underline = underline;
	}

	public String getFont() {
		return this.font;
	}

	public void setFont(final String font) {
		this.font = font;
	}

	public int getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(final int fontSize) {
		this.fontSize = fontSize;
	}

	public Color getFontColor() {
		return this.fontColor;
	}

	public void setFontColor(final Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Border getLeftBorder() {
		return this.leftBorder;
	}

	public void setLeftBorder(final Border leftBorder) {
		this.leftBorder = leftBorder;
	}

	public Border getTopBorder() {
		return this.topBorder;
	}

	public void setTopBorder(final Border topBorder) {
		this.topBorder = topBorder;
	}

	public Border getRightBorder() {
		return this.rightBorder;
	}

	public void setRightBorder(final Border rightBorder) {
		this.rightBorder = rightBorder;
	}

	public Border getBottomBorder() {
		return this.bottomBorder;
	}

	public void setBottomBorder(final Border bottomBorder) {
		this.bottomBorder = bottomBorder;
	}

	public Alignment getAlignment() {
		return this.alignment;
	}

	public void setAlignment(final Alignment alignment) {
		this.alignment = alignment;
	}

	public VerticalAlignment getVerticalAlignment() {
		return this.verticalAlignment;
	}

	public void setVerticalAlignment(final VerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public CellType getType() {
		return this.type;
	}

	public void setType(final CellType type) {
		this.type = type;
	}

}
