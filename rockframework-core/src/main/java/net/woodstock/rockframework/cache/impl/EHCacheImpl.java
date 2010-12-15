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

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.woodstock.rockframework.cache.Cache;

class EHCacheImpl implements Cache {

	private static final long	serialVersionUID	= 5933239363934204456L;

	private final Ehcache		cache;

	public EHCacheImpl(final Ehcache cache) {
		super();
		this.cache = cache;
	}

	@Override
	public void add(final String name, final Object object) {
		Element element = new Element(name, object);
		this.cache.put(element);
	}

	@Override
	public boolean contains(final String name) {
		return this.cache.isKeyInCache(name);
	}

	@Override
	public Object get(final String name) {
		Element element = this.cache.get(name);
		if (element != null) {
			return element.getObjectValue();
		}
		return null;
	}

	@Override
	public Object remove(final String name) {
		Element element = this.cache.get(name);
		if (element != null) {
			Object value = element.getObjectValue();
			this.cache.remove(name);
			return value;
		}
		return null;
	}

}
