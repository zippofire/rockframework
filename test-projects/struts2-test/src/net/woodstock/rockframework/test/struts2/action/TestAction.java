package net.woodstock.rockframework.test.struts2.action;

import net.woodstock.rockframework.test.struts2.entity.Foo;
import net.woodstock.rockframework.web.struts2.Action;

import org.springframework.context.annotation.Scope;

@net.woodstock.rockframework.web.struts2.spring.Action
@Scope(value = "prototype")
public class TestAction extends Action {

	private static final long	serialVersionUID	= 1L;

	private Foo					foo;

	@Override
	public String execute() throws Exception {
		return net.woodstock.rockframework.web.struts2.Constants.SUCCESS;
	}

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

}
