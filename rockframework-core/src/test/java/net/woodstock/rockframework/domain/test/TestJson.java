package net.woodstock.rockframework.domain.test;

import java.lang.reflect.Method;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.pojo.converter.JsonConverter;

public class TestJson extends TestCase {

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
		foo.setBar(this.getBar());
		return foo;
	}

	public void xtestBar() throws Exception {
		Bar bar = this.getBar();

		String s = new JsonConverter().to(bar);
		System.out.println(s);

		bar = new JsonConverter().from(Bar.class, s);
		new JsonConverter().to(bar);
		System.out.println(s);
	}

	public void xtestFoo() throws Exception {
		Foo foo = this.getFoo();

		String s = new JsonConverter().to(foo);
		System.out.println(s);

		foo = new JsonConverter().from(Foo.class, s);
		new JsonConverter().to(foo);
		System.out.println(s);
	}
	
	public void testXYZ() throws Exception {
		Method method = Foo.class.getMethod("setId", new Class[]{Integer.class});
		System.out.println(method.getReturnType().equals(Void.TYPE));
	}

}
