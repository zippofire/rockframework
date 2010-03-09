package net.woodstock.rockframework.test.domain.reflection;

import java.lang.annotation.Annotation;

import javax.persistence.Column;
import javax.persistence.Id;

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

	public void xtest2() throws Exception {
		X x = new X();

		BeanDescriptor bean = BeanDescriptorFactoryImpl.getInstance().getBeanDescriptor(x.getClass());

		bean.getProperty("id").setValue(x, new Integer(1));
		bean.getProperty("firstName").setValue(x, "Lourival");
		bean.getProperty("lastName").setValue(x, "Sabino");

		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getName() + " => " + property.getValue(x));
		}
	}

	public void test3() throws Exception {
		BeanDescriptor bean = BeanDescriptorFactoryImpl.getInstance(ReflectionType.MIXED).getBeanDescriptor(X.class);
		PropertyDescriptor property = bean.getProperty("id");
		Annotation[] annotations = property.getAnnotations();
		for (Annotation annotation : annotations) {
			System.out.println(annotation);
		}
	}

	public static class X {

		@Id
		private Integer	id;

		private String	firstName;

		private String	lastName;

		public X() {
			super();
		}

		@Column
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
