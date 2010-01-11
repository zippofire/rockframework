package net.woodstock.rockframework.test.domain.springtest;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.spring.ContextHelper;

public class Test extends TestCase {

	public void test1() throws Exception {
		Object1 o = ContextHelper.getInstance().getObject(Object1.class);
		o.hello();
	}

}
