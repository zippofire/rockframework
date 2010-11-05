package net.woodstock.rockframework.reflection.impl;

import net.woodstock.rockframework.reflection.BeanDescriptor;

class FieldBeanDescriptorFactory extends AbstractBeanDescriptorFactory {

	@Override
	public BeanDescriptor getBeanDescriptorInternal(final Class<?> clazz) {
		FieldBeanDescriptor beanDescriptor = new FieldBeanDescriptor(clazz);
		beanDescriptor.configure();
		return beanDescriptor;
	}

}
