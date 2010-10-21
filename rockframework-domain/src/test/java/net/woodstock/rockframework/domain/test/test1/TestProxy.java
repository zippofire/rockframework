package net.woodstock.rockframework.domain.test.test1;

import junit.framework.TestCase;
import net.woodstock.rockframework.domain.Entity;

public class TestProxy extends TestCase {

	private Bar getBar() {
		Bar bar = new Bar();
		bar.setId(new Integer(1));
		return bar;
	}

	public void xtest1() throws Exception {
		Bar bar = ProxyEntity.newInstance(this.getBar());
		bar.setId(new Integer(1));
		bar.setValue("Teste");
		System.out.println(bar);
	}

	public void xtest2() throws Exception {
		Bar bar = (Bar) ProxyEntity.newInstance2(this.getBar());
		bar.setId(new Integer(1));
		bar.setValue("Teste");
		System.out.println(bar);
	}

	public void test3() throws Exception {
		Object bar = ProxyObject.newInstance(this.getBar());
		System.out.println(bar);
		System.out.println(bar instanceof Bar);
		System.out.println(bar instanceof Entity);
		System.out.println(bar.getClass().getCanonicalName());
	}

}
