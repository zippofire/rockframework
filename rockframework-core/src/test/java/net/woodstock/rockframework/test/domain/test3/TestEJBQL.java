package net.woodstock.rockframework.test.domain.test3;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.impl.QueryBuilderAdapter;

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

	public void test1() throws Exception {
		Foo foo = this.getFoo();

		QueryBuilderAdapter builder = new QueryBuilderAdapter();
		builder.setEntity(foo);
		builder.build();

		String sql = builder.getQueryString();
		System.out.println(sql);
	}

	public void test2() throws Exception {
		Bar bar = this.getBar();

		QueryBuilderAdapter builder = new QueryBuilderAdapter();
		builder.setEntity(bar);
		builder.build();
		String sql = builder.getQueryString();
		System.out.println(sql);
	}

}
