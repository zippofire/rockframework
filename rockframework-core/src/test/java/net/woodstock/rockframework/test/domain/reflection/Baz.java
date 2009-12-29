package net.woodstock.rockframework.test.domain.reflection;

import java.util.Set;

import net.woodstock.rockframework.domain.Entity;

public class Baz implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				name;

	private Foo					foo;

	private Set<Bar>			bars;

	public Baz() {
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

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(final Foo foo) {
		this.foo = foo;
	}

	public Set<Bar> getBars() {
		return this.bars;
	}

	public void setBars(final Set<Bar> bars) {
		this.bars = bars;
	}

}
