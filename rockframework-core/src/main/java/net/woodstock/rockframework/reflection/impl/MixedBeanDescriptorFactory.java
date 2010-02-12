package net.woodstock.rockframework.reflection.impl;

import net.woodstock.rockframework.reflection.BeanDescriptor;

class MixedBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

	@Override
	public BeanDescriptor getBeanDescriptorInternal(final Class<?> clazz) {
		return new MixedBeanDescriptor(clazz);
	}

}