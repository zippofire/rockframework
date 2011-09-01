package br.net.woodstock.rockframework.domain.test.test2;

import br.net.woodstock.rockframework.domain.validator.jpa.EntityValidator;
import br.net.woodstock.rockframework.domain.validator.jpa.Operation;
import br.net.woodstock.rockframework.domain.validator.jpa.ValidationResult;
import br.net.woodstock.rockframework.domain.validator.jpa.impl.EntityValidatorImpl;
import junit.framework.TestCase;

public class TestJPAValidation extends TestCase {

	private Bar getBar() {
		Bar bar = new Bar();
		// bar.setId(new Integer(1));
		bar.setFoo(this.getFoo());
		bar.setValue("1234567890");
		return bar;
	}

	private Bar getBar2() {
		Bar2 bar = new Bar2();
		bar.setId(new Integer(1));
		bar.setFoo(this.getFoo());
		bar.setValue("1234567890");
		bar.setValue2(null);
		return bar;
	}

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		// foo.setName("Teste1Teste1Teste1Teste1");
		return foo;
	}

	public void test1() throws Exception {
		System.out.println("============= test1 ============");
		Bar bar = this.getBar();
		EntityValidator validator = new EntityValidatorImpl(Operation.PERSIST, true);
		for (ValidationResult result : validator.validate(bar)) {
			System.out.println(result);
		}
	}

	public void test2() throws Exception {
		System.out.println("============= test2 ============");
		Bar bar = this.getBar2();
		bar.setId(null);
		bar.getFoo().setId(null);
		EntityValidator validator = new EntityValidatorImpl(Operation.MERGE, true);
		for (ValidationResult result : validator.validate(bar)) {
			System.out.println(result);
		}
	}

	public void test3() throws Exception {
		System.out.println("============= test3 ============");
		Bar bar = new Bar();
		EntityValidator validator = new EntityValidatorImpl(Operation.FIND, true);
		for (ValidationResult result : validator.validate(bar)) {
			System.out.println(result);
		}
	}

	public void test4() throws Exception {
		System.out.println("============= test3 ============");
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		EntityValidator validator = new EntityValidatorImpl(Operation.REMOVE, true);
		for (ValidationResult result : validator.validate(bar)) {
			System.out.println(result);
		}
	}

}
