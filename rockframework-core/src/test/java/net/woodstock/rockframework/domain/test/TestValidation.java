package net.woodstock.rockframework.domain.test;

import java.util.Collection;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationResult;

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

	public void testRockapi() throws Exception {
		Foo foo = this.getFoo();

		Collection<ValidationResult> results = LocalEntityValidator.getInstance().validate(foo, Operation.GET);

		for (ValidationResult result : LocalValidationResult.getErrors(results)) {
			LocalValidationResult localValidationResult = (LocalValidationResult) result;
			System.out.println("\t" + localValidationResult.getContext().getCanonicalName());
			// System.out.println("\t\t" + localValidationResult.getContext().getAnnotation());
			System.out.println("\t\t" + result.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void testHibernate() throws Exception {
		Foo foo = this.getFoo();

		ClassValidator<Foo> validator = new ClassValidator(Foo.class);
		if (validator.hasValidationRules()) {
			InvalidValue[] values = validator.getInvalidValues(foo);
			for (InvalidValue value : values) {
				System.out.println("\t" + value.getRootBean().getClass().getSimpleName() + "." + value.getPropertyPath());
				System.out.println("\t\t" + value.getMessage());
			}
		}
	}
}
