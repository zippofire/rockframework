package net.woodstock.rockframework.test.domain.springtest;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.spring.SpringLoader;

public class Test extends TestCase {

	public void test1() throws Exception {
		Object1 o = SpringLoader.getInstance().getObject(Object1.class);
		o.hello();
	}

}
