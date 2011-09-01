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
package br.net.woodstock.rockframework.util;

import java.text.Format;

import br.net.woodstock.rockframework.cache.Cache;
import br.net.woodstock.rockframework.cache.CacheManager;
import br.net.woodstock.rockframework.cache.impl.CacheManagerImpl;
import br.net.woodstock.rockframework.utils.ObjectUtils;


public abstract class FormatFactory<T extends Format> {

	private Cache	cache;

	public FormatFactory() {
		super();
		String id = ObjectUtils.toString(this);
		CacheManager cacheManager = CacheManagerImpl.getInstance();
		this.cache = cacheManager.create(id);
	}

	protected boolean containsOnCache(final String pattern) {
		return this.cache.contains(pattern);
	}

	@SuppressWarnings("unchecked")
	protected T getFromCache(final String pattern) {
		return (T) this.cache.get(pattern);
	}

	protected void addToCache(final String pattern, final T format) {
		this.cache.add(pattern, format);
	}

	public abstract T getFormat(final String pattern);
}
