package net.woodstock.rockframework.domain.test4;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.impl.AbstractBusiness;
import net.woodstock.rockframework.utils.DateUtils;

public class TestFoo extends TestCase {

	public void test1() throws Exception {
		Foo foo = new Foo();
		foo.setDate(DateUtils.parse("00/00/0000", "dd/MM/yyyy"));

		GenericBusiness business = new AbstractBusiness() {
		};
		business.validateCreateWithError(foo);
	}

}
