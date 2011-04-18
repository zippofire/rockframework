package net.woodstock.rockframework.domain.test.test4;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.impl.AbstractJPABusiness;
import net.woodstock.rockframework.domain.business.impl.JPAOperation;
import net.woodstock.rockframework.utils.DateUtils;

public class TestFoo extends TestCase {

	public void test1() throws Exception {
		Foo foo = new Foo();
		foo.setDate(DateUtils.parse("00/00/0000", "dd/MM/yyyy"));

		MyBusiness business = new MyBusiness();
		business.validate(foo, JPAOperation.PERSIST);
	}

	public class MyBusiness extends AbstractJPABusiness {

		@SuppressWarnings("rawtypes")
		@Override
		public ValidationResult validate(final Entity entity, final JPAOperation operation) {
			return super.validate(entity, operation);
		}

	}

}
