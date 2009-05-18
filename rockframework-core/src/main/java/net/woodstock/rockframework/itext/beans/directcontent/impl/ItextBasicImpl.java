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
package net.woodstock.rockframework.itext.beans.directcontent.impl;

import net.woodstock.rockframework.itext.beans.ItextBasic;

public abstract class ItextBasicImpl implements ItextBasic {

	protected float	left;

	protected float	top;

	public ItextBasicImpl() {
		super();
		this.left = 0;
		this.top = 0;
	}

	public float getLeft() {
		return this.left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getTop() {
		return this.top;
	}

	public void setTop(float top) {
		this.top = top;
	}

}
