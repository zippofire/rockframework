package net.woodstock.rockframework.test.domain.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactoryImpl;

public class TestBeanDescriptor extends TestCase {

	public void xtest1() throws Exception {
		X x = new X();
		BeanDescriptor bean = BeanDescriptorFactoryImpl.getInstance(ReflectionType.METHOD).getBeanDescriptor(x.getClass());

		bean.getProperty("id").setValue(x, new Integer(1));
		bean.getProperty("firstName").setValue(x, "Lourival");
		bean.getProperty("lastName").setValue(x, "Sabino");

		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getName() + " => " + property.getValue(x));
		}

		bean.getProperty("fullName").setValue(x, "Lourival Sabino");
	}

	public void test2() throws Exception {
		X x = new X();

		BeanDescriptor bean = BeanDescriptorFactoryImpl.getInstance().getBeanDescriptor(x.getClass());

		bean.getProperty("id").setValue(x, new Integer(1));
		bean.getProperty("firstName").setValue(x, "Lourival");
		bean.getProperty("lastName").setValue(x, "Sabino");

		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getName() + " => " + property.getValue(x));
		}
	}

	public static class X {

		private Integer	id;

		private String	firstName;

		private String	lastName;

		public X() {
			super();
		}

		public Integer getId() {
			return this.id;
		}

		public void setId(final Integer id) {
			this.id = id;
		}

		public String getFirstName() {
			return this.firstName;
		}

		public void setFirstName(final String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return this.lastName;
		}

		public void setLastName(final String lastName) {
			this.lastName = lastName;
		}

		// test
		public String getFullName() {
			return this.firstName + " " + this.lastName;
		}

	}

}
