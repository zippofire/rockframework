package net.woodstock.rockframework.domain.test.test2;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessResult;
import net.woodstock.rockframework.domain.business.impl.AbstractJPABusiness;
import net.woodstock.rockframework.domain.validator.jpa.EntityValidator;
import net.woodstock.rockframework.domain.validator.jpa.Operation;
import net.woodstock.rockframework.domain.validator.jpa.ValidationResult;
import net.woodstock.rockframework.domain.validator.jpa.impl.EntityValidatorImpl;

public class TestJPAValidation extends TestCase {

	private MyBusiness getBusiness() {
		return new MyBusiness();
	}

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

	public void xtest1() throws Exception {
		MyBusiness business = this.getBusiness();
		Bar bar = this.getBar();
		BusinessResult result = business.validate(bar, Operation.PERSIST);
		System.out.println(result.getMessage());
	}

	public void test2() throws Exception {
		Bar bar = this.getBar2();
		EntityValidator validator = new EntityValidatorImpl(Operation.PERSIST, true);
		for (ValidationResult result : validator.validate(bar)) {
			System.out.println(result.getProperty() + " - " + result.getMessage());
		}
	}

	public class MyBusiness extends AbstractJPABusiness {

		@SuppressWarnings("rawtypes")
		@Override
		public BusinessResult validate(final Entity entity, final Operation operation) {
			return super.validate(entity, operation);
		}

	}

}
