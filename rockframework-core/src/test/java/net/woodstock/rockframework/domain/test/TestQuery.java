package net.woodstock.rockframework.domain.test;

import java.util.LinkedHashSet;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.query.LikeMode;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.QueryBuilderAdapter;

public class TestQuery extends TestCase {

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		bar.setValue("Teste BAR");
		return bar;
	}

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(11));
		foo.setName("Teste FOO");
		foo.setBar(getBar());
		return foo;
	}

	private Baz getBaz() {
		Baz baz = new Baz();
		baz.setId(new Integer(111));
		baz.setName("Teste BAZ");
		baz.setFoo(getFoo());
		baz.setBars(new LinkedHashSet<Bar>());

		for (int i = 0; i < 10; i++) {
			Bar bar = new Bar();
			bar.setId(new Integer(1000 + i));
			bar.setValue("Teste BAZ|BAR " + i);
			baz.getBars().add(bar);
		}

		return baz;
	}

	public void xtest1() throws Exception {
		Bar bar = getBar();

		QueryBuilder builder = new QueryBuilderAdapter();
		builder.setOption(QueryBuilder.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(QueryBuilder.OPTION_IGNORE_CASE, Boolean.TRUE);
		builder.parse(bar);

		String s = builder.getQueryString();
		System.out.println(s);
	}

	public void xtest2() throws Exception {
		Foo foo = getFoo();

		QueryBuilder builder = new QueryBuilderAdapter();
		builder.setOption(QueryBuilder.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(QueryBuilder.OPTION_IGNORE_CASE, Boolean.TRUE);
		builder.parse(foo);

		String s = builder.getQueryString();
		System.out.println(s);
	}

	public void test3() throws Exception {
		Baz baz = getBaz();

		QueryBuilder builder = new QueryBuilderAdapter();
		builder.setOption(QueryBuilder.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(QueryBuilder.OPTION_IGNORE_CASE, Boolean.TRUE);
		builder.parse(baz);

		String s = builder.getQueryString();
		System.out.println(s);

		builder.getQuery(null);
	}

}
