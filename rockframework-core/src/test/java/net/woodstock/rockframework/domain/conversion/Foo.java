package net.woodstock.rockframework.domain.conversion;

import net.woodstock.rockframework.conversion.text.Size;
import net.woodstock.rockframework.domain.util.IntegerEntity;

public class Foo extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	@Size(10)
	private Integer				id;

	@Size(50)
	private String				name;

	@Size(60)
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: " + this.id);
		builder.append(" ");
		builder.append("Name: " + this.name);
		builder.append(" ");
		builder.append("Bar: [" + this.bar + "]");
		return builder.toString();
	}

}
