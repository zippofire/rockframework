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

import java.util.HashMap;
import java.util.Map;

import net.woodstock.rockframework.config.CoreLog;
import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.BeanDescriptorFactory;

abstract class AbstractBeanDescriptorFactory implements BeanDescriptorFactory {

	private Map<Class<?>, BeanDescriptor>	cache;

	public AbstractBeanDescriptorFactory() {
		this.cache = new HashMap<Class<?>, BeanDescriptor>();
	}

	protected void addToCache(final Class<?> clazz, final BeanDescriptor descriptor) {
		this.cache.put(clazz, descriptor);
	}

	protected BeanDescriptor getFromCache(final Class<?> clazz) {
		return this.cache.get(clazz);
	}

	protected boolean hasOnCache(final Class<?> clazz) {
		return this.cache.containsKey(clazz);
	}

	@Override
	public final BeanDescriptor getBeanDescriptor(final Class<?> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException(CoreMessage.getInstance().getMessage(CoreMessage.MESSAGE_NOT_NULL, "Class"));
		}
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
