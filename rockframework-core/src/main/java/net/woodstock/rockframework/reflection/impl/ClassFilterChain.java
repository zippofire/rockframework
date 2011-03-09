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
package net.woodstock.rockframework.reflection.impl;

import java.util.Collection;
import java.util.LinkedList;

import net.woodstock.rockframework.reflection.ClassFilter;
import net.woodstock.rockframework.util.Assert;

public class ClassFilterChain implements ClassFilter {

	private Collection<ClassFilter>	filters;

	public ClassFilterChain() {
		super();
		this.filters = new LinkedList<ClassFilter>();
	}

	public void add(final ClassFilter filter) {
		Assert.notNull(filter, "filter");
		this.filters.add(filter);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean accept(final Class clazz) {
		for (ClassFilter filter : this.filters) {
			boolean b = filter.accept(clazz);
			if (!b) {
				return false;
			}
		}
		return true;
	}

}
