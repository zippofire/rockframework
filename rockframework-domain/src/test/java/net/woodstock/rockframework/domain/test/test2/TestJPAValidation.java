package net.woodstock.rockframework.domain.test.test2;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.impl.AbstractJPABusiness;
import net.woodstock.rockframework.domain.business.impl.JPAOperation;

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

	private Foo getFoo() {
		Foo foo = new Foo();
		foo.setId(new Integer(1));
		// foo.setName("Teste1Teste1Teste1Teste1");
		return foo;
	}

	public void test1() throws Exception {
		MyBusiness business = this.getBusiness();
		Bar bar = this.getBar();
		ValidationResult result = business.validate(bar, JPAOperation.PERSIST);
		System.out.println(result.getMessage());
	}

	public class MyBusiness extends AbstractJPABusiness {

		@SuppressWarnings("rawtypes")
		@Override
		public ValidationResult validate(final Entity entity, final JPAOperation operation) {
			return super.validate(entity, operation);
		}

	}

}
