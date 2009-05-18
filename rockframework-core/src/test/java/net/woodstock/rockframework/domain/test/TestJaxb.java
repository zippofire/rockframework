package net.woodstock.rockframework.domain.test;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.pojo.converter.JaxbConverter;

public class TestJaxb extends TestCase {

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		bar.setValue("Bar");
		return bar;
	}

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Foo");
		foo.setBar(getBar());
		return foo;
	}

	public void testBar() throws Exception {
		Bar bar = getBar();

		String s = new JaxbConverter().to(bar);
		System.out.println(s);
		
		bar = new JaxbConverter().from(Bar.class, s);
		new JaxbConverter().to(bar);
		System.out.println(s);	
	}

	public void testFoo() throws Exception {
		Foo foo = getFoo();

		String s = new JaxbConverter().to(foo);
		System.out.println(s);
		
		foo = new JaxbConverter().from(Foo.class, s);
		new JaxbConverter().to(foo);
		System.out.println(s);		
	}

}
