package net.woodstoc.rockframework.struts2.test;

import net.woodstoc.rockframework.entity.Foo;
import net.woodstock.rockframework.web.struts2.action.BaseAction;

public class TestAction extends BaseAction {

	private static final long	serialVersionUID	= 1L;

	private Foo					foo;

	@Override
	public String execute() throws Exception {
		return BaseAction.SUCCESS;
	}

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

}
