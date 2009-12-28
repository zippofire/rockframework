package net.woodstock.rockframework.domain.conversion;

import java.util.Collection;

import net.woodstock.rockframework.domain.util.IntegerEntity;

public class Foo extends IntegerEntity {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private String				name;

	private Collection<Bar>		bars;

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

	public Collection<Bar> getBars() {
		return this.bars;
	}

	public void setBars(final Collection<Bar> bars) {
		this.bars = bars;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ID: " + this.id);
		builder.append(" ");
		builder.append("Name: " + this.name);
		if (this.bars != null) {
			boolean first = true;
			builder.append("[");
			for (Bar b : this.bars) {
				if (!first) {
					builder.append(",");
				}
				builder.append("{");
				builder.append(b);
				builder.append("}");
				if (first) {
					first = false;
				}
			}
			builder.append("]");
		}
		return builder.toString();
	}

}
