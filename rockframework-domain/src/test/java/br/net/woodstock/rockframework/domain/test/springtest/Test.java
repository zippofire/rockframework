package br.net.woodstock.rockframework.domain.test.springtest;

import br.net.woodstock.rockframework.domain.spring.ContextHelper;
import junit.framework.TestCase;

public class Test extends TestCase {

	public void test1() throws Exception {
		Object1 o = ContextHelper.getInstance().getObject(Object1.class);
		o.hello();
	}

}
