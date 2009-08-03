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

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.ReflectionException;

public abstract class BeanDescriptorFactory {

	private static BeanDescriptorFactory	fieldBeanDescriptorFactory;

	private static BeanDescriptorFactory	methodBeanDescriptorFactory;

	public abstract BeanDescriptor getBeanDescriptor(Class<?> clazz);

	public static BeanDescriptorFactory getByFieldInstance() {
		if (BeanDescriptorFactory.fieldBeanDescriptorFactory == null) {
			synchronized (BeanDescriptorFactory.class) {
				if (BeanDescriptorFactory.fieldBeanDescriptorFactory == null) {
					BeanDescriptorFactory.fieldBeanDescriptorFactory = new FieldBeanDescriptorFactory();
				}
			}
		}
		return BeanDescriptorFactory.fieldBeanDescriptorFactory;
	}

	public static BeanDescriptorFactory getByMethodInstance() {
		if (BeanDescriptorFactory.methodBeanDescriptorFactory == null) {
			synchronized (BeanDescriptorFactory.class) {
				if (BeanDescriptorFactory.methodBeanDescriptorFactory == null) {
					BeanDescriptorFactory.methodBeanDescriptorFactory = new MethodBeanDescriptorFactory();
				}
			}
		}
		return BeanDescriptorFactory.methodBeanDescriptorFactory;
	}

	abstract static class AbstractBeanDescriptorFactory extends BeanDescriptorFactory {

		private Map<Class<?>, BeanDescriptor>	cache;

		public AbstractBeanDescriptorFactory() {
			this.cache = new HashMap<Class<?>, BeanDescriptor>();
		}

		protected void addToCache(Class<?> clazz, BeanDescriptor descriptor) {
			this.cache.put(clazz, descriptor);
		}

		protected BeanDescriptor getFromCache(Class<?> clazz) {
			return this.cache.get(clazz);
		}

		protected boolean hasOnCache(Class<?> clazz) {
			return this.cache.containsKey(clazz);
		}

		@Override
		public BeanDescriptor getBeanDescriptor(Class<?> clazz) throws ReflectionException {
			if (this.hasOnCache(clazz)) {
				return this.getFromCache(clazz);
			}
			BeanDescriptor descriptor = this.getBeanDescriptorInternal(clazz);
			this.addToCache(clazz, descriptor);
			return descriptor;
		}

		protected abstract BeanDescriptor getBeanDescriptorInternal(Class<?> clazz);

	}

	static class FieldBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

		@Override
		public BeanDescriptor getBeanDescriptorInternal(Class<?> clazz) throws ReflectionException {
			return new FieldBeanDescriptor(clazz);
		}

	}

	static class MethodBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

		@Override
		public BeanDescriptor getBeanDescriptorInternal(Class<?> clazz) throws ReflectionException {
			return new MethodBeanDescriptor(clazz);
		}

	}

}
