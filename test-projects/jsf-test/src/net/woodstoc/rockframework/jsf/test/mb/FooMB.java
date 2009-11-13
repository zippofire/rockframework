package net.woodstoc.rockframework.jsf.test.mb;

import java.util.List;

import net.woodstoc.rockframework.jsf.test.entity.Foo;
import net.woodstoc.rockframework.jsf.test.persistence.FooRepository;
import net.woodstock.rockframework.web.jsf.controler.ManagedBean;

public class FooMB extends ManagedBean {

	private static final long	serialVersionUID	= 1L;

	private FooRepository		fooRepository;

	private Foo					foo;

	private List<Foo>			foos;

	private String				message;

	public FooMB() {
		super();
		this.foo = new Foo();
		this.fooRepository = new FooRepository();
	}

	public String delete() {
		this.fooRepository.delete(this.foo);
		return ManagedBean.SUCCESS;
	}

	public String edit() {
		System.out.println("ID: " + this.foo.getId());
		this.foo = this.fooRepository.get(this.foo);
		return ManagedBean.SUCCESS;
	}

	public String list() {
		this.foos = this.fooRepository.list();
		return ManagedBean.SUCCESS;
	}

	public String save() {
		this.fooRepository.save(this.foo);
		this.foo = new Foo();
		this.message = "Success!!!";
		return ManagedBean.SUCCESS;
	}

	public String update() {
		this.fooRepository.update(this.foo);
		this.message = "Success!!!";
		return ManagedBean.SUCCESS;
	}

	// Getters
	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(Foo foo) {
		this.foo = foo;
	}

	public List<Foo> getFoos() {
		return this.foos;
	}

	public void setFoos(List<Foo> foos) {
		this.foos = foos;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
