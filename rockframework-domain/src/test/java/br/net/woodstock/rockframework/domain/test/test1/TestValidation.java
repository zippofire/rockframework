package br.net.woodstock.rockframework.domain.test.test1;

import junit.framework.TestCase;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

public class TestValidation extends TestCase {

	private Bar getBar() {
		Bar bar = new Bar();
		// bar.setId(new Integer(1));
		return bar;
	}

	private Foo getFoo() {
		Foo foo = new Foo();
		// foo.setId(new Integer(1));
		foo.setName("T");
		foo.setBar(this.getBar());
		return foo;
	}

	public void testHibernate() throws Exception {
		Foo foo = this.getFoo();

		ClassValidator<Foo> validator = new ClassValidator<Foo>(Foo.class);
		if (validator.hasValidationRules()) {
			InvalidValue[] values = validator.getInvalidValues(foo);
			for (InvalidValue value : values) {
				System.out.println("\t" + value.getRootBean().getClass().getSimpleName() + "." + value.getPropertyPath());
				System.out.println("\t\t" + value.getMessage());
			}
		}
	}
}
