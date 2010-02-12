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

import net.woodstock.rockframework.reflection.BeanNavigator;
import net.woodstock.rockframework.reflection.ReflectionType;

public abstract class BeanNavigatorFactory {

	private static BeanNavigatorFactory	fieldBeanNavigatorFactory	= new FieldBeanNavigatorFactory();

	private static BeanNavigatorFactory	methodBeanNavigatorFactory	= new MethodBeanNavigatorFactory();

	private static BeanNavigatorFactory	mixedBeanNavigatorFactory	= new MixedBeanNavigatorFactory();

	public abstract BeanNavigator getBeanNavigator(Object bean);

	public static BeanNavigatorFactory getInstance() {
		return BeanNavigatorFactory.getInstance(BeanDescriptorFactoryImpl.REFLECTION_TYPE);
	}

	public static BeanNavigatorFactory getInstance(final ReflectionType type) {
		if (type == null) {
			throw new IllegalArgumentException("Type must be not null");
		}
		switch (type) {
			case FIELD:
				return BeanNavigatorFactory.getByFieldInstance();
			case METHOD:
				return BeanNavigatorFactory.getByMethodInstance();
			case MIXED:
				return BeanNavigatorFactory.getMixedInstance();
			default:
				throw new IllegalArgumentException("Invalid type " + type);
		}
	}

	private static BeanNavigatorFactory getByFieldInstance() {
		return BeanNavigatorFactory.fieldBeanNavigatorFactory;
	}

	private static BeanNavigatorFactory getByMethodInstance() {
		return BeanNavigatorFactory.methodBeanNavigatorFactory;
	}

	private static BeanNavigatorFactory getMixedInstance() {
		return BeanNavigatorFactory.mixedBeanNavigatorFactory;
	}

	static class FieldBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(final Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactoryImpl.getInstance(ReflectionType.FIELD), bean, null, null);
		}

	}

	static class MethodBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(final Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactoryImpl.getInstance(ReflectionType.METHOD), bean, null, null);
		}

	}

	static class MixedBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(final Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactoryImpl.getInstance(ReflectionType.MIXED), bean, null, null);
		}

	}

}
