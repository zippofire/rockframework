package net.woodstock.rockframework.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

public class BeanDescriptorTest extends TestCase {

	public void test1() throws Exception {
		ITest x = new ConcreteX();
		BeanDescriptor bean = BeanDescriptorFactory.getInstance(ReflectionType.FIELD).getBeanDescriptor(x.getClass());
		System.out.println("Name: " + bean.getName());
		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getType().getSimpleName() + " " + property.getName());
		}
	}

	public void test2() throws Exception {
		ITest y = new ConcreteY();
		BeanDescriptor bean = BeanDescriptorFactory.getInstance(ReflectionType.METHOD).getBeanDescriptor(y.getClass());
		System.out.println("Name: " + bean.getName());
		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getType().getSimpleName() + " " + property.getName());
		}
	}

	public void test3() throws Exception {
		ConcreteX x = new ConcreteX();
		BeanDescriptor bean = BeanDescriptorFactory.getInstance(ReflectionType.FIELD).getBeanDescriptor(x.getClass());
		System.out.println("Name: " + bean.getName());
		for (PropertyDescriptor property : bean.getProperties()) {
			System.out.println(property.getType().getSimpleName() + " " + property.getName());
		}

		PropertyDescriptor property = bean.getProperty("id");
		property.setValue(x, new Integer(1));
		System.out.println("ID: " + property.getValue(x));
	}

	public void test4() throws Exception {
		ITest y = new ConcreteY();
		BeanDescriptor bean = BeanDescriptorFactory.getInstance(ReflectionType.METHOD).getBeanDescriptor(ITest.class);
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

	public static abstract class AbstractX implements ITest {

		private Integer	id;

		private String	name;

		public AbstractX() {
			super();
		}

		public Integer getId() {
			return this.id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	public static class ConcreteX extends AbstractX {

		private Float	value;

		public ConcreteX() {
			super();
		}

		public Float getValue() {
			return this.value;
		}

		public void setValue(Float value) {
			this.value = value;
		}

	}

	public static abstract class AbstractY implements ITest {

		protected Map<String, Object>	params;

		public AbstractY() {
			super();
			this.params = new HashMap<String, Object>();
		}

		public Integer getId() {
			return (Integer) this.params.get("ID");
		}

		public void setId(Integer id) {
			this.params.put("ID", id);
		}

		public String getName() {
			return (String) this.params.get("NAME");
		}

		public void setName(String name) {
			this.params.put("NAME", name);
		}

	}

	public static class ConcreteY extends AbstractY {

		public ConcreteY() {
			super();
		}

		public Float getValue() {
			return (Float) this.params.get("VALUE");
		}

		public void setValue(Float value) {
			this.params.put("VALUE", value);
		}

	}

}
