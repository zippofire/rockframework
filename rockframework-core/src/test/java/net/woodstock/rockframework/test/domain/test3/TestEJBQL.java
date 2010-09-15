package net.woodstock.rockframework.test.domain.test3;

import junit.framework.TestCase;
import net.woodstock.rockframework.test.domain.test.TestQueryBuilder;

public class TestEJBQL extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setName("Foo");

		Bar bar = new Bar();
		bar.setValue("Bar");

		foo.setBar(bar);
		bar.setFoo(foo);
		return foo;
	}

	private Bar getBar() {
		return this.getFoo().getBar();
	}

	public void test0() throws Exception {
		Foo foo = new Foo();

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(foo);
		String sql = builder.getQueryString();
		System.out.println(sql);
	}

	public void test1() throws Exception {
		Foo foo = this.getFoo();

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(foo);

		String sql = builder.getQueryString();
		System.out.println(sql);
	}

	public void test2() throws Exception {
		Bar bar = this.getBar();

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(bar);
		String sql = builder.getQueryString();
		System.out.println(sql);
	}

}
