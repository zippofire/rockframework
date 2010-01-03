package net.woodstoc.rockframework.test.struts2;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.spring.SpringLoader;
import net.woodstock.rockframework.test.struts2.action.TestAction;

public class TestSpring extends TestCase {

	public void xtest1() throws Exception {
		SpringLoader loader = SpringLoader.getInstance();
		System.out.println(loader);
	}

	public void test2() throws Exception {
		SpringLoader loader = SpringLoader.getInstance();
		TestAction action1 = loader.getObject(TestAction.class);
		TestAction action2 = loader.getObject(TestAction.class);
		
		System.out.println(action1);
		System.out.println(action2);
	}

}
