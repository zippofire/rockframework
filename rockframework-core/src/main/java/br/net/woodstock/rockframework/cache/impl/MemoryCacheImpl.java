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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.net.woodstock.rockframework.cache.Cache;
import br.net.woodstock.rockframework.config.CoreConfig;
import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.util.Assert;

class MemoryCacheImpl implements Cache {

	private static final long				serialVersionUID		= -8671956307741808591L;

	private static final String				MAX_CACHE_SIZE_PROPERTY	= "cache.size";

	private static final String				MAX_CACHE_SIZE_VALUE	= CoreConfig.getInstance().getValue(MemoryCacheImpl.MAX_CACHE_SIZE_PROPERTY);

	private static final int				MAX_CACHE_SIZE			= Integer.parseInt(MemoryCacheImpl.MAX_CACHE_SIZE_VALUE);

	private static final int				CLEAN_SIZE				= 50;

	private String							id;

	private Map<String, MemoryCacheItem>	map;

	MemoryCacheImpl(final String id) {
		super();
		Assert.notEmpty(id, "id");
		this.id = id;
		this.map = new HashMap<String, MemoryCacheItem>();
	}

	public String getId() {
		return this.id;
	}

	@Override
	public synchronized boolean add(final String name, final Object object) {
		Assert.notEmpty(name, "name");
		if (object == null) {
			CoreLog.getInstance().getLogger().warn("Cache not supports null objects");
			return false;
		}
		if (this.map.size() > MemoryCacheImpl.MAX_CACHE_SIZE) {

			CoreLog.getInstance().getLogger().info("Cache[" + this.id + "]  size is greater than " + MemoryCacheImpl.MAX_CACHE_SIZE + " and will be cleaned");
			Collection<MemoryCacheItem> collection = this.map.values();
			List<MemoryCacheItem> list = new ArrayList<MemoryCacheItem>(collection);
			Collections.sort(list);

			List<MemoryCacheItem> deletables = list.subList(0, MemoryCacheImpl.CLEAN_SIZE);
			for (MemoryCacheItem item : deletables) {
				this.map.remove(item.getName());
			}
			CoreLog.getInstance().getLogger().info("Cache clean");
		}
		MemoryCacheItem item = new MemoryCacheItem(name, object);
		this.map.put(name, item);
		return true;
	}

	@Override
	public synchronized boolean contains(final String name) {
		Assert.notEmpty(name, "name");
		MemoryCacheItem item = this.map.get(name);
		if (item != null) {
			item.updateLastAccess();
			return true;
		}
		return false;
	}

	@Override
	public synchronized Object get(final String name) {
		Assert.notEmpty(name, "name");
		MemoryCacheItem item = this.map.get(name);
		if (item != null) {
			item.updateLastAccess();
			return item.getValue();
		}
		return item;
	}

	@Override
	public synchronized Object remove(final String name) {
		Assert.notEmpty(name, "name");
		MemoryCacheItem item = this.map.get(name);
		if (item != null) {
			item.updateLastAccess();
			this.map.remove(name);
			return item.getValue();
		}
		return null;
	}

}
