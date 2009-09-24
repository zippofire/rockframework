package net.woodstock.rockframework.domain.reflection;

import net.woodstock.rockframework.domain.Entity;

public class Foo implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				name;

	private Bar					bar;

	public Foo() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(Bar bar) {
		this.bar = bar;
	}

}
