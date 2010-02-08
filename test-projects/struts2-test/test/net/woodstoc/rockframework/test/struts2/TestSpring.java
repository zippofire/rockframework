package net.woodstoc.rockframework.test.struts2;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.spring.ContextHelper;
import net.woodstock.rockframework.test.struts2.action.TestAction;

public class TestSpring extends TestCase {

	public void xtest1() throws Exception {
		ContextHelper loader = ContextHelper.getInstance();
		System.out.println(loader);
	}

	public void test2() throws Exception {
		ContextHelper loader = ContextHelper.getInstance();
		TestAction action1 = loader.getObject(TestAction.class);
		TestAction action2 = loader.getObject(TestAction.class);

		System.out.println(action1);
		System.out.println(action2);
	}

}
