package net.woodstock.rockframework.domain.conversion;

import net.woodstock.rockframework.conversion.text.Size;
import net.woodstock.rockframework.domain.util.IntegerEntity;

public class Bar extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	@Size(10)
	private Integer				id;

	@Size(50)
	private String				name;

	public Bar() {
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: " + this.id);
		builder.append(" ");
		builder.append("Name: " + this.name);
		return builder.toString();
	}

}
