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
import net.woodstock.rockframework.reflection.BeanDescriptorBuilder;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.util.Assert;

public class BeanDescriptorBuilderImpl implements BeanDescriptorBuilder {

	private static final String				REFLECTION_TYPE_PROPERTY	= "reflection.type";

	private static final String				REFLECTION_TYPE_VALUE		= CoreConfig.getInstance().getValue(BeanDescriptorBuilderImpl.REFLECTION_TYPE_PROPERTY);

	private static final ReflectionType		REFLECTION_TYPE				= ReflectionType.valueOf(BeanDescriptorBuilderImpl.REFLECTION_TYPE_VALUE);

	private static BeanDescriptorFactory	fieldBeanDescriptorFactory	= new FieldBeanDescriptorFactory();

	private static BeanDescriptorFactory	methodBeanDescriptorFactory	= new MethodBeanDescriptorFactory();

	private static BeanDescriptorFactory	mixedBeanDescriptorFactory	= new MixedBeanDescriptorFactory();

	private ReflectionType					mode;

	private Class<?>						type;

	public BeanDescriptorBuilderImpl() {
		super();
		this.mode = BeanDescriptorBuilderImpl.REFLECTION_TYPE;
	}

	@Override
	public BeanDescriptorBuilder setMode(final ReflectionType reflectionType) {
		this.mode = reflectionType;
		return this;

	}

	@Override
	public BeanDescriptorBuilder setType(final Class<?> type) {
		this.type = type;
		return this;
	}

	@Override
	public BeanDescriptor getBeanDescriptor() {
		Assert.notNull(this.mode, "mode");
		Assert.notNull(this.type, "type");

		switch (this.mode) {
			case FIELD:
				return BeanDescriptorBuilderImpl.fieldBeanDescriptorFactory.getBeanDescriptor(this.type);
			case METHOD:
				return BeanDescriptorBuilderImpl.methodBeanDescriptorFactory.getBeanDescriptor(this.type);
			case MIXED:
				return BeanDescriptorBuilderImpl.mixedBeanDescriptorFactory.getBeanDescriptor(this.type);
			default:
				throw new IllegalStateException("Unknow mode " + this.mode);
		}
	}

}
