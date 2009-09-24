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

public abstract class BeanNavigatorFactory {

	private static BeanNavigatorFactory	fieldBeanNavigatorFactory;

	private static BeanNavigatorFactory	methodBeanNavigatorFactory;

	public abstract BeanNavigator getBeanNavigator(Object bean);

	public static BeanNavigatorFactory getByFieldInstance() {
		if (BeanNavigatorFactory.fieldBeanNavigatorFactory == null) {
			synchronized (BeanNavigatorFactory.class) {
				if (BeanNavigatorFactory.fieldBeanNavigatorFactory == null) {
					BeanNavigatorFactory.fieldBeanNavigatorFactory = new FieldBeanNavigatorFactory();
				}
			}
		}
		return BeanNavigatorFactory.fieldBeanNavigatorFactory;
	}

	public static BeanNavigatorFactory getByMethodInstance() {
		if (BeanNavigatorFactory.methodBeanNavigatorFactory == null) {
			synchronized (BeanNavigatorFactory.class) {
				if (BeanNavigatorFactory.methodBeanNavigatorFactory == null) {
					BeanNavigatorFactory.methodBeanNavigatorFactory = new MethodBeanNavigatorFactory();
				}
			}
		}
		return BeanNavigatorFactory.methodBeanNavigatorFactory;
	}

	static class FieldBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactory.getByFieldInstance(), bean, null, null);
		}

	}

	static class MethodBeanNavigatorFactory extends BeanNavigatorFactory {

		@Override
		public BeanNavigator getBeanNavigator(Object bean) {
			return new BeanNavigatorImpl(BeanDescriptorFactory.getByMethodInstance(), bean, null, null);
		}

	}

}
