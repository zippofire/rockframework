package net.woodstock.rockframework.domain.test2;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.impl.AbstractJPABusiness;

public class TestJPAValidation extends TestCase {

	private GenericBusiness getBusiness() {
		return new AbstractJPABusiness() {

		};
	}

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		return bar;
	}

	private Foo getFoo() {
		Foo foo = new Foo();
		// foo.setId(new Integer(1));
		foo.setName("Teste1Teste1Teste1Teste1");
		foo.setBar(this.getBar());
		return foo;
	}

	public void xtest1() throws Exception {
		GenericBusiness business = this.getBusiness();
		Bar bar = this.getBar();
		business.validateCreateWithError(bar);
	}

	public void test2() throws Exception {
		GenericBusiness business = this.getBusiness();
		Foo foo = this.getFoo();
		business.validateCreateWithError(foo);
	}

}
