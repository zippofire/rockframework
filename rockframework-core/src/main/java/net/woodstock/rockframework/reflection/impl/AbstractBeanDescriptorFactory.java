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
package net.woodstock.rockframework.reflection.impl;

import net.woodstock.rockframework.cache.Cache;
import net.woodstock.rockframework.cache.CacheManager;
import net.woodstock.rockframework.cache.impl.CacheManagerImpl;
import net.woodstock.rockframework.config.CoreLog;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.BeanDescriptorFactory;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.ObjectUtils;

abstract class AbstractBeanDescriptorFactory implements BeanDescriptorFactory {

	private Cache	cache;

	public AbstractBeanDescriptorFactory() {
		super();
		String id = ObjectUtils.toString(this);
		CacheManager cacheManager = CacheManagerImpl.getInstance();
		this.cache = cacheManager.create(id);
	}

	private void addToCache(final Class<?> clazz, final BeanDescriptor descriptor) {
		this.cache.add(clazz.getCanonicalName(), descriptor);
	}

	private BeanDescriptor getFromCache(final Class<?> clazz) {
		return (BeanDescriptor) this.cache.get(clazz.getCanonicalName());
	}

	private boolean hasOnCache(final Class<?> clazz) {
		return this.cache.contains(clazz.getCanonicalName());
	}

	@Override
	public final BeanDescriptor getBeanDescriptor(final Class<?> clazz) {
		Assert.notNull(clazz, "clazz");

		if (this.hasOnCache(clazz)) {
			CoreLog.getInstance().getLog().info("Class " + clazz.getSimpleName() + " exists on cache");
			return this.getFromCache(clazz);
		}
		BeanDescriptor descriptor = this.getBeanDescriptorInternal(clazz);
		CoreLog.getInstance().getLog().info("Adding class " + clazz.getSimpleName() + " to cache");
		this.addToCache(clazz, descriptor);
		return descriptor;
	}

	protected abstract BeanDescriptor getBeanDescriptorInternal(Class<?> clazz);

}
