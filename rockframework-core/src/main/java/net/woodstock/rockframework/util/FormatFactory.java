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
package net.woodstock.rockframework.util;

import java.util.Locale;

import net.woodstock.rockframework.cache.Cache;
import net.woodstock.rockframework.cache.CacheManager;
import net.woodstock.rockframework.cache.impl.CacheManagerImpl;
import net.woodstock.rockframework.utils.LocaleUtils;
import net.woodstock.rockframework.utils.ObjectUtils;

public abstract class FormatFactory<T> {

	private static final String	SEPARATOR	= "@";

	private Cache				cache;

	public FormatFactory() {
		super();
		String id = ObjectUtils.toString(this);
		CacheManager cacheManager = CacheManagerImpl.getInstance();
		this.cache = cacheManager.create(id);
	}

	protected boolean containsOnCache(final String pattern, final Locale locale) {
		String key = pattern + FormatFactory.SEPARATOR + locale;
		return this.cache.contains(key);
	}

	@SuppressWarnings("unchecked")
	protected T getFromCache(final String pattern, final Locale locale) {
		String key = pattern + FormatFactory.SEPARATOR + locale;
		return (T) this.cache.get(key);
	}

	protected void addToCache(final String pattern, final Locale locale, final T format) {
		String key = pattern + FormatFactory.SEPARATOR + locale;
		this.cache.add(key, format);
	}

	public T getFormat(final String pattern) {
		return this.getFormat(pattern, LocaleUtils.getLocale());
	}

	public abstract T getFormat(String pattern, Locale locale);
}
