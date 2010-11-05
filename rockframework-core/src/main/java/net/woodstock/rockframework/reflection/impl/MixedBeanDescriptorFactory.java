package net.woodstock.rockframework.reflection.impl;

import net.woodstock.rockframework.reflection.BeanDescriptor;

class MixedBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

	@Override
	public BeanDescriptor getBeanDescriptorInternal(final Class<?> clazz) {
		MixedBeanDescriptor beanDescriptor = new MixedBeanDescriptor(clazz);
		beanDescriptor.configure();
		return beanDescriptor;
	}

}