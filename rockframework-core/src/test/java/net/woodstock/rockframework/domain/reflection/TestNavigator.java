package net.woodstock.rockframework.domain.reflection;

import junit.framework.TestCase;
import net.woodstock.rockframework.reflection.BeanNavigator;
import net.woodstock.rockframework.reflection.PropertyNavigator;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanNavigatorFactory;

public class TestNavigator extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Foo 1");
		return foo;
	}

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		bar.setValue("Bar 1");
		return bar;
	}

	public void xtest1() throws Exception {
		Foo foo = this.getFoo();
		foo.setBar(this.getBar());

		BeanNavigator n1 = BeanNavigatorFactory.getInstance(ReflectionType.FIELD).getBeanNavigator(foo);
		for (PropertyNavigator p : n1.getProperties()) {
			System.out.println(p.getCanonicalName() + " => " + p.getValue());
		}

		// Bar
		BeanNavigator n2 = n1.getPropertyAsBean("bar");
		for (PropertyNavigator p : n2.getProperties()) {
			System.out.println(p.getCanonicalName() + " => " + p.getValue());
		}

	}

	public void test2() throws Exception {
		Foo foo = this.getFoo();
		foo.setBar(this.getBar());

		BeanNavigator n1 = BeanNavigatorFactory.getInstance(ReflectionType.FIELD).getBeanNavigator(foo);
		n1.getProperty("id").setValue(new Integer(2));
		n1.getProperty("name").setValue("Foo 2");

		for (PropertyNavigator p : n1.getProperties()) {
			System.out.println(p.getCanonicalName() + " => " + p.getValue());
		}

		// Bar
		BeanNavigator n2 = n1.getPropertyAsBean("bar");
		n2.getProperty("id").setValue(new Integer(2));
		n2.getProperty("value").setValue("Bar 2");

		for (PropertyNavigator p : n2.getProperties()) {
			System.out.println(p.getCanonicalName() + " => " + p.getValue());
		}

	}

}
