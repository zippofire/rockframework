package net.woodstock.rockframework.domain.test.test1;

import java.util.HashSet;
import java.util.LinkedHashSet;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.persistence.orm.Constants;
import net.woodstock.rockframework.domain.persistence.orm.LikeMode;

public class TestQuery extends TestCase {

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		bar.setValue("Teste BAR");
		return bar;
	}

	private Foo getFoo(final boolean child) {
		Foo foo = new Foo();
		foo.setId(new Integer(11));
		foo.setName("Teste FOO");
		if (child) {
			foo.setBar(this.getBar());
			foo.getBar().setFoo(foo);
		}
		return foo;
	}

	private Baz getBaz(final boolean child) {
		Baz baz = new Baz();
		baz.setId(new Integer(111));
		baz.setName("Teste BAZ");
		if (child) {
			baz.setFoo(this.getFoo(true));
			baz.setBars(new LinkedHashSet<Bar>());

			for (int i = 0; i < 10; i++) {
				Bar bar = new Bar();
				bar.setId(new Integer(1000 + i));
				bar.setValue("Teste BAZ|BAR " + i);
				bar.setBaz(baz);
				baz.getBars().add(bar);
			}
		}

		return baz;
	}

	public void xtest1() throws Exception {
		Bar bar = this.getBar();

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(bar);
		builder.setOption(Constants.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(Constants.OPTION_IGNORE_CASE, Boolean.TRUE);

		String s = builder.getQueryString();
		System.out.println(s);

		// builder.getQuery(null);
	}

	public void xtest2() throws Exception {
		Foo foo = this.getFoo(true);

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(foo);
		builder.setOption(Constants.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(Constants.OPTION_IGNORE_CASE, Boolean.TRUE);

		String s = builder.getQueryString();
		System.out.println(s);

		// builder.getQuery(null);
	}

	public void test3() throws Exception {
		Baz baz = this.getBaz(true);

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(baz);
		builder.setOption(Constants.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(Constants.OPTION_IGNORE_CASE, Boolean.TRUE);
		builder.setOption(Constants.OPTION_ORDER_BY, "nome ASC, id DESC");

		String s = builder.getQueryString();
		System.out.println(s);

		// builder.getQuery(null);
	}

	// Verifica se o objeto estando nao nulo mas sem nenhum campo
	// populado entra na consulta
	public void xtest4() throws Exception {
		Foo foo = this.getFoo(false);
		foo.setBar(new Bar());

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(foo);
		builder.setOption(Constants.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(Constants.OPTION_IGNORE_CASE, Boolean.TRUE);

		String s = builder.getQueryString();
		System.out.println(s);

		// builder.getQuery(null);
	}

	// Verifica se a colecao estando nao nulo mas vazia
	// entra na consulta
	public void xtest5() throws Exception {
		Baz baz = this.getBaz(false);
		baz.setFoo(new Foo());
		baz.setBars(new HashSet<Bar>());

		TestQueryBuilder builder = new TestQueryBuilder();
		builder.setEntity(baz);
		builder.setOption(Constants.OPTION_LIKE_MODE, LikeMode.ALL);
		builder.setOption(Constants.OPTION_IGNORE_CASE, Boolean.TRUE);

		String s = builder.getQueryString();
		System.out.println(s);

		// builder.getQuery(null);
	}

}
