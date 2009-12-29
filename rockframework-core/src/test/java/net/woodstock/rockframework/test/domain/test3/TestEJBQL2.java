package net.woodstock.rockframework.test.domain.test3;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.impl.QueryBuilderAdapter;

public class TestEJBQL2 extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		return foo;
	}

	public void test1() throws Exception {
		Foo foo = this.getFoo();

		QueryBuilderAdapter builder = new QueryBuilderAdapter();
		builder.setEntity(foo);
		builder.build();
		String sql = builder.getQueryString();
		System.out.println(sql);
	}

}
