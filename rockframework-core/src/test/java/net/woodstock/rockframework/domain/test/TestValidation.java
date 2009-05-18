package net.woodstock.rockframework.domain.test;

import java.util.Collection;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.business.validation.local.ObjectValidator;
import net.woodstock.rockframework.domain.business.validation.local.Operation;
import net.woodstock.rockframework.domain.business.validation.local.ValidationResult;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

public class TestValidation extends TestCase {

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		return bar;
	}

	private Foo getFoo() {
		Foo foo = new Foo();
		// foo.setId(new Integer(1));
		foo.setName("Teste1");
		foo.setBar(getBar());
		return foo;
	}

	public void testRockapi() throws Exception {
		Foo foo = getFoo();

		Collection<ValidationResult> results = ObjectValidator.validate(foo, Operation.CREATE);

		// System.out.println("OK");
		// for (ValidationResult result : results) {
		// if (result.isSuccess()) {
		// System.out.println("\t" + result.getContext().getCanonicalName());
		// System.out.println("\t\t" + result.getContext().getAnnotation());
		// }
		// }

		System.out.println("ERROR");
		for (ValidationResult result : ValidationResult.getErrors(results)) {
			System.out.println("\t" + result.getContext().getCanonicalName());
			System.out.println("\t\t" + result.getContext().getAnnotation());
			System.out.println("\t\t" + result.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void xtestHibernate() throws Exception {
		Foo foo = getFoo();

		ClassValidator<Foo> validator = new ClassValidator(Foo.class);
		if (validator.hasValidationRules()) {
			InvalidValue values[] = validator.getInvalidValues(foo);
			for (InvalidValue value : values) {
				System.out.println("\t" + value.getPropertyName());
				System.out.println("\t\t" + value.getMessage());
			}
		}
	}

}
