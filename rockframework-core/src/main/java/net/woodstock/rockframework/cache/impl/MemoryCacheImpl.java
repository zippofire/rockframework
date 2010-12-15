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
package net.woodstock.rockframework.cache.impl;

import java.util.HashMap;
import java.util.Map;

import net.woodstock.rockframework.cache.Cache;
import net.woodstock.rockframework.util.Assert;

class MemoryCacheImpl implements Cache {

	private static final long	serialVersionUID	= -8671956307741808591L;

	private String				id;

	private Map<String, Object>	map;

	MemoryCacheImpl(final String id) {
		super();
		Assert.notEmpty(id, "id");
		this.id = id;
		this.map = new HashMap<String, Object>();
	}

	public String getId() {
		return this.id;
	}

	@Override
	public void add(final String name, final Object object) {
		Assert.notEmpty(name, "name");
		this.map.put(name, object);
	}

	@Override
	public boolean contains(final String name) {
		Assert.notEmpty(name, "name");
		return this.map.containsKey(name);
	}

	@Override
	public Object get(final String name) {
		Assert.notEmpty(name, "name");
		return this.map.get(name);
	}

	@Override
	public Object remove(final String name) {
		Assert.notEmpty(name, "name");
		return this.map.remove(name);
	}

	// Object
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MemoryCacheImpl)) {
			return false;
		}

		MemoryCacheImpl other = (MemoryCacheImpl) obj;

		return this.id.equals(other.getId());
	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cache[");
		builder.append(this.id);
		builder.append("]");
		return this.id;
	}

}
