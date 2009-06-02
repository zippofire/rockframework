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

public class Color implements SpreadsheetElement {

	private static final long	serialVersionUID	= -1698323182988298815L;

	private int					red;

	private int					green;

	private int					blue;

	public Color() {
		super();
	}

	public Color(int red, int green, int blue) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed() {
		return this.red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return this.green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return this.blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

}
