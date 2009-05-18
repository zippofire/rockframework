package net.woodstock.rockframework.domain.test3;

import java.util.Map;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.BuilderException;
import net.woodstock.rockframework.domain.persistence.query.impl.EJBQLQueryBuilder;

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

		EJBQLQueryBuilder builder = new TestEJBSQLQueryBuilder();
		String sql = builder.parse(foo).getQueryString();
		System.out.println(sql);
	}

	public void test2() throws Exception {
		Bar bar = this.getBar();

		EJBQLQueryBuilder builder = new TestEJBSQLQueryBuilder();
		String sql = builder.parse(bar).getQueryString();
		System.out.println(sql);
	}

	class TestEJBSQLQueryBuilder extends EJBQLQueryBuilder {

		@Override
		protected Object getQueryLocal(String sql, Object manager) throws BuilderException {
			return null;
		}

		@Override
		protected void setQueryOptions(Object query, Map<String, Object> options) throws BuilderException {
		}

		@Override
		protected void setQueryParameter(Object query, String name, Object value) throws BuilderException {
		}
	}

}
