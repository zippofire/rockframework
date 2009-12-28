package net.woodstock.rockframework.domain.conversion;

import net.woodstock.rockframework.domain.util.IntegerEntity;

public class Baz extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				name;

	private Bar					bar;

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

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(final Bar bar) {
		this.bar = bar;
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
