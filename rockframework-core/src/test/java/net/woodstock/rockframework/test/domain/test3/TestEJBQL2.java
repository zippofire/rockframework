package net.woodstock.rockframework.test.domain.test3;

import junit.framework.TestCase;
import net.woodstock.rockframework.test.domain.test.TestQueryBuilder;

public class TestEJBQL2 extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		return foo;
	}

	public void test1() throws Exception {
		Foo foo = this.getFoo();

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(foo);
		String sql = builder.getQueryString();
		System.out.println(sql);
	}

}
