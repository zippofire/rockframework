package net.woodstock.rockframework.test;

import javax.persistence.Column;

import junit.framework.TestCase;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactoryImpl;

public class ReflectionTest extends TestCase {

	public void test1() throws Exception {
		long time = System.currentTimeMillis();
		BeanDescriptor beanDescriptor = BeanDescriptorFactoryImpl.getInstance(ReflectionType.MIXED).getBeanDescriptor(X.class);
		X x = new X();
		x.setFirstName("Lourival");
		x.setLastName("Sabino");

		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			System.out.println(propertyDescriptor.getName() + " => " + propertyDescriptor.getValue(x));
			System.out.println(propertyDescriptor.isAnnotationPresent(Column.class));
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Tempo: " + time);
	}

	public static class X {

		@Column
		private String	firstName;

		@Column
		private String	lastName;

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

		@Column
		public String getFullName() {
			return this.firstName + " " + this.lastName;
		}

		public void setFullName(final String fullName) {
			String[] array = fullName.split(" ");
			this.firstName = array[0];
			this.lastName = array[1];
		}

	}

}
