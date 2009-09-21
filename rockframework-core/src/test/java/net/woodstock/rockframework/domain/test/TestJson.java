package net.woodstock.rockframework.domain.test;

import java.util.HashSet;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.pojo.converter.JsonConverter;

public class TestJson extends TestCase {

	private Baz getBaz() {
		Baz baz = new Baz();
		baz.setId(new Integer(1));
		baz.setName("baz");
		baz.setBars(new HashSet<Bar>());

		Foo foo = new Foo();
		foo.setId(new Integer(1));
		foo.setName("Foo");
		baz.setFoo(foo);

		for (int i = 0; i < 2; i++) {
			Bar bar = new Bar();
			bar.setId(new Integer(1));
			bar.setValue("Bar");
			bar.setBaz(baz);
			baz.getBars().add(bar);
		}
		return baz;
	}

	public void test() throws Exception {
		Baz baz = this.getBaz();

		String s = new JsonConverter().to(baz);
		System.out.println(s);
	}

}
