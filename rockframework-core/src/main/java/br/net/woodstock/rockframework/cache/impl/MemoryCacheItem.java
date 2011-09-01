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

package br.net.woodstock.rockframework.cache.impl;

import java.io.Serializable;

class MemoryCacheItem implements Serializable, Comparable<MemoryCacheItem> {

	private static final long	serialVersionUID	= -5130406793935609029L;

	private String				name;

	private Object				value;

	private long				lastAccess;

	public MemoryCacheItem(final String name, final Object value) {
		super();
		this.name = name;
		this.value = value;
		this.lastAccess = System.currentTimeMillis();
	}

	public String getName() {
		return this.name;
	}

	public Object getValue() {
		return this.value;
	}

	public long getLastAccess() {
		return this.lastAccess;
	}

	// Aux
	public void updateLastAccess() {
		this.lastAccess = System.currentTimeMillis();
	}

	// Comparable
	@Override
	public int compareTo(final MemoryCacheItem other) {
		if (this.lastAccess > other.getLastAccess()) {
			return 1;
		}
		if (this.lastAccess < other.getLastAccess()) {
			return -1;
		}
		return 0;
	}
}
