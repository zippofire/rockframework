package net.woodstock.rockframework.domain.reflection;

import net.woodstock.rockframework.domain.Entity;

public class Bar implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				value;

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

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public Baz getBaz() {
		return this.baz;
	}

	public void setBaz(final Baz baz) {
		this.baz = baz;
	}

}
