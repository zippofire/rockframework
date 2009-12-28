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

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(final Bar bar) {
		this.bar = bar;
	}

}
