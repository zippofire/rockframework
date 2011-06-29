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
package net.woodstock.rockframework.image;

public enum ImageType {

	JPEG("jpeg"), GIF("gif"), PNG("png"), BMP("bmp"), TIFF("tiff");

	private String	internalName;

	private ImageType(final String internalName) {
		this.internalName = internalName;
	}

	public String getInternalName() {
		return this.internalName;
	}

}
