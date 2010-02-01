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
package net.woodstock.rockframework.domain.util;

import net.woodstock.rockframework.domain.Pojo;

public class Range implements Pojo {

	private static final long	serialVersionUID	= 1182462172427235262L;

	private int					start;

	private int					end;

	public Range() {
		super();
	}

	public Range(final int start, final int end) {
		super();
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(final int start) {
		this.start = start;
	}

	public int getEnd() {
		return this.end;
	}

	public void setEnd(final int end) {
		this.end = end;
	}

}
