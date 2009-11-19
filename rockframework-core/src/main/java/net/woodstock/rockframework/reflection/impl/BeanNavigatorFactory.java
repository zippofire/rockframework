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

	private static BeanNavigatorFactory	fieldBeanNavigatorFactory;

	private static BeanNavigatorFactory	methodBeanNavigatorFactory;

	private static BeanNavigatorFactory	mixedBeanNavigatorFactory;

	public abstract BeanNavigator getBeanNavigator(Object bean);

	public static BeanNavigatorFactory getInstance(ReflectionType type) {
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
		if (BeanNavigatorFactory.fieldBeanNavigatorFactory == null) {
			synchronized (BeanNavigatorFactory.class) {
				if (BeanNavigatorFactory.fieldBeanNavigatorFactory == null) {
					BeanNavigatorFactory.fieldBeanNavigatorFactory = new FieldBeanNavigatorFactory();
				}
			}
		}
		return BeanNavigatorFactory.fieldBeanNavigatorFactory;
	}

	private static BeanNavigatorFactory getByMethodInstance() {
		if (BeanNavigatorFactory.methodBeanNavigatorFactory == null) {
			synchronized (BeanNavigatorFactory.class) {
				if (BeanNavigatorFactory.methodBeanNavigatorFactory == null) {
					BeanNavigatorFactory.methodBeanNavigatorFactory = new MethodBeanNavigatorFactory();
				}
			}
		}
		return BeanNavigatorFactory.methodBeanNavigatorFactory;
	}

	private static BeanNavigatorFactory getMixedInstance() {
		if (BeanNavigatorFactory.mixedBeanNavigatorFactory == null) {
			synchronized (BeanNavigatorFactory.class) {
				if (BeanNavigatorFactory.mixedBeanNavigatorFactory == null) {
					BeanNavigatorFactory.mixedBeanNavigatorFactory = new MixedBeanNavigatorFactory();
				}
			}
		}
		return BeanNavigatorFactory.mixedBeanNavigatorFactory;
	}

	static class FieldBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactory.getInstance(ReflectionType.FIELD), bean, null, null);
		}

	}

	static class MethodBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactory.getInstance(ReflectionType.METHOD), bean, null, null);
		}

	}

	static class MixedBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactory.getInstance(ReflectionType.MIXED), bean, null, null);
		}

	}

}
