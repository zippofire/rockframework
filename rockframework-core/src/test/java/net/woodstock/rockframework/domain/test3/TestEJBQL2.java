package net.woodstock.rockframework.domain.test3;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.QueryBuilderAdapter;

public class TestEJBQL2 extends TestCase {

	private Foo getFoo() {
		Foo foo = new Foo();
		return foo;
	}

	public void test1() throws Exception {
		Foo foo = this.getFoo();

		QueryBuilder builder = new QueryBuilderAdapter();
		String sql = builder.parse(foo).getQueryString();
		System.out.println(sql);
	}

}
