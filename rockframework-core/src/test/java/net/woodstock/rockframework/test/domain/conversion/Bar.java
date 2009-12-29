package net.woodstock.rockframework.test.domain.conversion;

import net.woodstock.rockframework.domain.util.IntegerEntity;

public class Bar extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				name;

	private Foo					foo;

	private Baz					baz;

	public Bar() {
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

	public Baz getBaz() {
		return this.baz;
	}

	public void setBaz(final Baz baz) {
		this.baz = baz;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: " + this.id);
		builder.append(" ");
		builder.append("Name: " + this.name);
		return builder.toString();
	}

}
