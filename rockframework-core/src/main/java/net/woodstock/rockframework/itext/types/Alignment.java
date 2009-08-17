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
package net.woodstock.rockframework.itext.types;

import com.lowagie.text.Element;

public enum Alignment {

	BASELINE(Element.ALIGN_BASELINE), BOTTOM(Element.ALIGN_BOTTOM), CENTER(Element.ALIGN_CENTER), DEFAULT(Element.ALIGN_LEFT), JUSTIFIED(Element.ALIGN_JUSTIFIED), LEFT(Element.ALIGN_LEFT), MIDDLE(Element.ALIGN_MIDDLE), RIGHT(Element.ALIGN_RIGHT), TOP(Element.ALIGN_TOP);

	private int	alignment;

	private Alignment(int alignment) {
		this.alignment = alignment;
	}

	public int getAlignment() {
		return this.alignment;
	}

}
