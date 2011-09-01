package br.net.woodstock.rockframework.domain.test.test4;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.business.BusinessResult;
import br.net.woodstock.rockframework.domain.business.impl.AbstractJPABusiness;
import br.net.woodstock.rockframework.domain.validator.jpa.Operation;
import br.net.woodstock.rockframework.utils.DateUtils;
import junit.framework.TestCase;

public class TestFoo extends TestCase {

	public void test1() throws Exception {
		Foo foo = new Foo();
		foo.setDate(DateUtils.parse("00/00/0000", "dd/MM/yyyy"));

		MyBusiness business = new MyBusiness();
		business.validate(foo, Operation.PERSIST);
	}

	public class MyBusiness extends AbstractJPABusiness {

		@SuppressWarnings("rawtypes")
		@Override
		public BusinessResult validate(final Entity entity, final Operation operation) {
			return super.validate(entity, operation);
		}

	}

}
