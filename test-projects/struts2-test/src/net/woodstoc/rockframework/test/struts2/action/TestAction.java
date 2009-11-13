package net.woodstoc.rockframework.test.struts2.action;

import net.woodstoc.rockframework.test.struts2.entity.Foo;
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
