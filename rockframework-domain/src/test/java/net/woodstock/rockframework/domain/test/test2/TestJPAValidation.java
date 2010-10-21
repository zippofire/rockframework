package net.woodstock.rockframework.domain.test.test2;

import junit.framework.TestCase;

import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.impl.AbstractJPABusiness;

public class TestJPAValidation extends TestCase {

	private GenericBusiness getBusiness() {
		return new AbstractJPABusiness() {
			//
		};
	}

	private Bar getBar() {
		Bar bar = new Bar();
		// bar.setId(new Integer(1));
		bar.setFoo(this.getFoo());
		bar.setValue("1234567890");
		return bar;
	}

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		// foo.setName("Teste1Teste1Teste1Teste1");
		return foo;
	}

	public void test1() throws Exception {
		GenericBusiness business = this.getBusiness();
		Bar bar = this.getBar();
		ValidationResult result = business.validateSave(bar);
		System.out.println(result.getMessage());
	}

}
