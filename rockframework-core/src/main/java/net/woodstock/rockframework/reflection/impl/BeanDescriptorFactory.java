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

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;

public abstract class BeanDescriptorFactory {

	public static final String				REFLECTION_TYPE_PROPERTY	= "reflection.type";

	public static final String				REFLECTION_TYPE_VALUE		= CoreConfig.getInstance().getValue(BeanDescriptorFactory.REFLECTION_TYPE_PROPERTY);

	public static final ReflectionType		REFLECTION_TYPE				= ReflectionType.valueOf(BeanDescriptorFactory.REFLECTION_TYPE_VALUE);

	private static BeanDescriptorFactory	fieldBeanDescriptorFactory	= new FieldBeanDescriptorFactory();

	private static BeanDescriptorFactory	methodBeanDescriptorFactory	= new MethodBeanDescriptorFactory();

	private static BeanDescriptorFactory	mixedBeanDescriptorFactory	= new MixedBeanDescriptorFactory();

	public abstract BeanDescriptor getBeanDescriptor(Class<?> clazz);

	public static BeanDescriptorFactory getInstance() {
		return BeanDescriptorFactory.getInstance(BeanDescriptorFactory.REFLECTION_TYPE);
	}

	public static BeanDescriptorFactory getInstance(final ReflectionType type) {
		if (type == null) {
			throw new IllegalArgumentException("Type must be not null");
		}
		switch (type) {
			case FIELD:
				return BeanDescriptorFactory.getByFieldInstance();
			case METHOD:
				return BeanDescriptorFactory.getByMethodInstance();
			case MIXED:
				return BeanDescriptorFactory.getMixedInstance();
			default:
				throw new IllegalArgumentException("Invalid type " + type);
		}
	}

	private static BeanDescriptorFactory getByFieldInstance() {
		return BeanDescriptorFactory.fieldBeanDescriptorFactory;
	}

	private static BeanDescriptorFactory getByMethodInstance() {
		return BeanDescriptorFactory.methodBeanDescriptorFactory;
	}

	private static BeanDescriptorFactory getMixedInstance() {
		return BeanDescriptorFactory.mixedBeanDescriptorFactory;
	}

	abstract static class AbstractBeanDescriptorFactory extends BeanDescriptorFactory {

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
		public BeanDescriptor getBeanDescriptor(final Class<?> clazz) {
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
		public BeanDescriptor getBeanDescriptorInternal(final Class<?> clazz) {
			return new FieldBeanDescriptor(clazz);
		}

	}

	static class MethodBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

		@Override
		public BeanDescriptor getBeanDescriptorInternal(final Class<?> clazz) {
			return new MethodBeanDescriptor(clazz);
		}

	}

	static class MixedBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

		@Override
		public BeanDescriptor getBeanDescriptorInternal(final Class<?> clazz) {
			return new MixedBeanDescriptor(clazz);
		}

	}

}
