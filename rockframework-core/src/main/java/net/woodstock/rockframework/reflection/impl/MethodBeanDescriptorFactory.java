package net.woodstock.rockframework.reflection.impl;

import net.woodstock.rockframework.reflection.BeanDescriptor;

class MethodBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

	@Override
	public BeanDescriptor getBeanDescriptorInternal(final Class<?> clazz) {
		MethodBeanDescriptor beanDescriptor = new MethodBeanDescriptor(clazz);
		beanDescriptor.configure();
		return beanDescriptor;
	}

}