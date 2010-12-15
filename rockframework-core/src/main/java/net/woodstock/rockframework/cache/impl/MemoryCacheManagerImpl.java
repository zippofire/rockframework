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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.woodstock.rockframework.cache.Cache;
import net.woodstock.rockframework.cache.CacheManager;
import net.woodstock.rockframework.util.Assert;

class MemoryCacheManagerImpl implements CacheManager {

	private List<Cache>	caches;

	public MemoryCacheManagerImpl() {
		super();
		this.caches = new ArrayList<Cache>();
	}

	@Override
	public boolean contains(final String id) {
		Assert.notEmpty(id, "id");
		boolean b = false;
		for (Cache cache : this.caches) {
			if (((MemoryCacheImpl) cache).getId().equals(id)) {
				b = true;
				break;
			}
		}
		return b;
	}

	@Override
	public Cache create(final String id) {
		Assert.notEmpty(id, "id");
		if (this.contains(id)) {
			throw new IllegalArgumentException("A cache with name " + id + " already exists");
		}
		Cache c = new MemoryCacheImpl(id);
		this.caches.add(c);
		return c;
	}

	@Override
	public Cache get(final String id) {
		Assert.notEmpty(id, "id");
		for (Cache cache : this.caches) {
			if (((MemoryCacheImpl) cache).getId().equals(id)) {
				return cache;
			}
		}
		return null;
	}

	@Override
	public void remove(final String id) {
		Assert.notEmpty(id, "id");
		Iterator<Cache> iterator = this.caches.iterator();
		while (iterator.hasNext()) {
			Cache cache = iterator.next();
			if (((MemoryCacheImpl) cache).getId().equals(id)) {
				iterator.remove();
				break;
			}
		}
	}

}
