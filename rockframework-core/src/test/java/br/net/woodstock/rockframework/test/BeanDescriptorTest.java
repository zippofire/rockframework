package br.net.woodstock.rockframework.test;

import java.util.HashMap;
import java.util.Map;

import br.net.woodstock.rockframework.reflection.BeanDescriptor;
import br.net.woodstock.rockframework.reflection.PropertyDescriptor;
import br.net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;

import junit.framework.TestCase;

public class BeanDescriptorTest extends TestCase {

	public void test1() throws Exception {
		ITest x = new ConcreteX();
		BeanDescriptor bean = new BeanDescriptorBuilder(x.getClass()).getBeanDescriptor();
		System.out.println("Name: " + bean.getName());
		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getType().getSimpleName() + " " + property.getName());
		}
	}

	public void test2() throws Exception {
		ITest y = new ConcreteY();
		BeanDescriptor bean = new BeanDescriptorBuilder(y.getClass()).getBeanDescriptor();
		System.out.println("Name: " + bean.getName());
		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getType().getSimpleName() + " " + property.getName());
		}
	}

	public void test3() throws Exception {
		ITest x = new ConcreteX();
		BeanDescriptor bean = new BeanDescriptorBuilder(x.getClass()).getBeanDescriptor();
		System.out.println("Name: " + bean.getName());
		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getType().getSimpleName() + " " + property.getName());
		}

		PropertyDescriptor property = bean.getProperty("id");
		property.setValue(x, Integer.valueOf(1));
		System.out.println("ID: " + property.getValue(x));
	}

	public void test4() throws Exception {
		ITest y = new ConcreteY();
		BeanDescriptor bean = new BeanDescriptorBuilder(y.getClass()).getBeanDescriptor();
		System.out.println("Name: " + bean.getName());
		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getType().getSimpleName() + " " + property.getName());
		}
		PropertyDescriptor property = bean.getProperty("id");
		property.setValue(y, new Integer(1));
		System.out.println("ID: " + property.getValue(y));
	}

	public static interface ITest {

		Integer getId();

		void setId(Integer id);

		String getName();

		void setName(String name);

		Float getValue();

		void setValue(Float value);
	}

	public abstract static class AbstractX implements ITest {

		private Integer	id;

		private String	name;

		public AbstractX() {
			super();
		}

		@Override
		public Integer getId() {
			return this.id;
		}

		@Override
		public void setId(final Integer id) {
			this.id = id;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public void setName(final String name) {
			this.name = name;
		}

	}

	public static class ConcreteX extends AbstractX {

		private Float	value;

		public ConcreteX() {
			super();
		}

		@Override
		public Float getValue() {
			return this.value;
		}

		@Override
		public void setValue(final Float value) {
			this.value = value;
		}

	}

	public abstract static class AbstractY implements ITest {

		private Map<String, Object>	params;

		public AbstractY() {
			super();
			this.params = new HashMap<String, Object>();
		}

		@Override
		public Integer getId() {
			return (Integer) this.params.get("ID");
		}

		@Override
		public void setId(final Integer id) {
			this.params.put("ID", id);
		}

		@Override
		public String getName() {
			return (String) this.params.get("NAME");
		}

		@Override
		public void setName(final String name) {
			this.params.put("NAME", name);
		}

		protected Map<String, Object> getParams() {
			return this.params;
		}

	}

	public static class ConcreteY extends AbstractY {

		public ConcreteY() {
			super();
		}

		@Override
		public Float getValue() {
			return (Float) this.getParams().get("VALUE");
		}

		@Override
		public void setValue(final Float value) {
			this.getParams().put("VALUE", value);
		}

	}

}
