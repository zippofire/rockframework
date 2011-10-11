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
package br.net.woodstock.rockframework.office.spreadsheet;

import java.io.IOException;
import java.io.InputStream;

import br.net.woodstock.rockframework.utils.IOUtils;

public class Image implements SpreadsheetElement {

	private static final long	serialVersionUID	= 8304414656932408168L;

	private byte[]				bytes;

	private int					row;

	private int					column;

	private ImageType			type;

	private int					width;

	private int					height;

	public Image() {
		super();
	}

	public Image(final byte[] bytes, final int row, final int column, final ImageType type) {
		super();
		this.bytes = bytes;
		this.row = row;
		this.column = column;
		this.type = type;
	}

	public Image(final InputStream inputStream, final int row, final int column, final ImageType type) throws IOException {
		super();
		this.bytes = IOUtils.toByteArray(inputStream);
		this.row = row;
		this.column = column;
		this.type = type;
	}

	public Image(final byte[] bytes, final int row, final int column, final ImageType type, final int width, final int height) {
		super();
		this.bytes = bytes;
		this.row = row;
		this.column = column;
		this.type = type;
		this.width = width;
		this.height = height;
	}

	public Image(final InputStream inputStream, final int row, final int column, final ImageType type, final int width, final int height) throws IOException {
		super();
		this.bytes = IOUtils.toByteArray(inputStream);
		this.row = row;
		this.column = column;
		this.type = type;
		this.width = width;
		this.height = height;
	}

	public byte[] getBytes() {
		return this.bytes;
	}

	public void setBytes(final byte[] bytes) {
		this.bytes = bytes;
	}

	public int getRow() {
		return this.row;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	public int getColumn() {
		return this.column;
	}

	public void setColumn(final int column) {
		this.column = column;
	}

	public ImageType getType() {
		return this.type;
	}

	public void setType(final ImageType type) {
		this.type = type;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public static enum ImageType {
		BMP, GIF, JPEG, PNG;
	}

}
