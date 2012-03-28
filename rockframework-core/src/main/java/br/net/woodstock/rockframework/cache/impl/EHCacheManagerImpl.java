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

import java.io.InputStream;

import net.sf.ehcache.Ehcache;
import br.net.woodstock.rockframework.InitializationException;
import br.net.woodstock.rockframework.cache.Cache;
import br.net.woodstock.rockframework.cache.CacheManager;
import br.net.woodstock.rockframework.utils.ClassLoaderUtils;

class EHCacheManagerImpl implements CacheManager {

	private static final String			CACHE_FILE	= "rockframework-ehcache.xml";

	private net.sf.ehcache.CacheManager	manager;

	public EHCacheManagerImpl() {
		super();
		try {
			InputStream inputStream = ClassLoaderUtils.getResourceAsStream(EHCacheManagerImpl.CACHE_FILE);
			this.manager = net.sf.ehcache.CacheManager.create(inputStream);
		} catch (Exception e) {
			throw new InitializationException(e);
		}
	}

	@Override
	public boolean contains(final String id) {
		return this.manager.cacheExists(id);
	}

	@Override
	public Cache create(final String id) {
		this.manager.addCache(id);
		Ehcache cache = this.manager.getEhcache(id);
		return new EHCacheImpl(cache);
	}

	@Override
	public Cache get(final String id) {
		Ehcache cache = this.manager.getEhcache(id);
		return new EHCacheImpl(cache);
	}

	@Override
	public void remove(final String id) {
		this.manager.removeCache(id);
	}

}
