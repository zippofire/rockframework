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

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.BeanDescriptorFactory;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.util.Assert;

public abstract class BeanDescriptorFactoryImpl implements BeanDescriptorFactory {

	public static final String				REFLECTION_TYPE_PROPERTY	= "reflection.type";

	public static final String				REFLECTION_TYPE_VALUE		= CoreConfig.getInstance().getValue(BeanDescriptorFactoryImpl.REFLECTION_TYPE_PROPERTY);

	public static final ReflectionType		REFLECTION_TYPE				= ReflectionType.valueOf(BeanDescriptorFactoryImpl.REFLECTION_TYPE_VALUE);

	private static BeanDescriptorFactory	fieldBeanDescriptorFactory	= new FieldBeanDescriptorFactory();

	private static BeanDescriptorFactory	methodBeanDescriptorFactory	= new MethodBeanDescriptorFactory();

	private static BeanDescriptorFactory	mixedBeanDescriptorFactory	= new MixedBeanDescriptorFactory();

	public abstract BeanDescriptor getBeanDescriptor(Class<?> clazz);

	public static BeanDescriptorFactory getInstance() {
		return BeanDescriptorFactoryImpl.getInstance(BeanDescriptorFactoryImpl.REFLECTION_TYPE);
	}

	public static BeanDescriptorFactory getInstance(final ReflectionType type) {
		Assert.notNull(type, "type");
		switch (type) {
			case FIELD:
				return BeanDescriptorFactoryImpl.fieldBeanDescriptorFactory;
			case METHOD:
				return BeanDescriptorFactoryImpl.methodBeanDescriptorFactory;
			case MIXED:
				return BeanDescriptorFactoryImpl.mixedBeanDescriptorFactory;
			default:
				return null;
		}
	}

}
