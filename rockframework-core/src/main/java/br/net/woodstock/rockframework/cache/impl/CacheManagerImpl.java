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

import br.net.woodstock.rockframework.cache.Cache;
import br.net.woodstock.rockframework.cache.CacheManager;

public final class CacheManagerImpl implements CacheManager {

	private static CacheManager	instance	= new CacheManagerImpl();

	private CacheManager		delegate	= CacheManagerHolder.getInstance();

	private CacheManagerImpl() {
		super();
	}

	@Override
	public boolean contains(final String id) {
		return this.delegate.contains(id);
	}

	@Override
	public Cache create(final String id) {
		return this.delegate.create(id);
	}

	@Override
	public Cache get(final String id) {
		return this.delegate.get(id);
	}

	@Override
	public void remove(final String id) {
		this.delegate.remove(id);
	}

	public static CacheManager getInstance() {
		return CacheManagerImpl.instance;
	}

}
