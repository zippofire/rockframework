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

import net.woodstock.rockframework.cache.CacheManager;

abstract class CacheManagerHelper {

	private static final String	EHCACHE_CLASS	= "net.sf.ehcache.CacheManager";

	private CacheManagerHelper() {
		//
	}

	public static CacheManager getInstance() {
		try {
			Class.forName(CacheManagerHelper.EHCACHE_CLASS);
			EHCacheManagerImpl manager = new EHCacheManagerImpl();
			return manager;
		} catch (ClassNotFoundException e) {
			MemoryCacheManagerImpl manager = new MemoryCacheManagerImpl();
			return manager;
		}
	}

}
