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
package br.net.woodstock.rockframework.domain.util;

import org.springframework.util.Assert;

import br.net.woodstock.rockframework.domain.Pojo;

public class Range<T extends Comparable<T>> implements Pojo {

	private static final long	serialVersionUID	= 1182462172427235262L;

	private T					start;

	private T					end;

	public Range() {
		super();
	}

	public Range(final T start, final T end) {
		super();
		Assert.notNull(start, "start");
		Assert.notNull(end, "end");
		this.start = start;
		this.end = end;
	}

	public T getStart() {
		return this.start;
	}

	public T getEnd() {
		return this.end;
	}

	// Aux
	public boolean isValid(final T value) {
		if (this.start.compareTo(value) > 0) {
			return false;
		}
		if (this.end.compareTo(value) < 0) {
			return false;
		}
		return true;
	}
}
