package net.woodstock.rockframework.test.domain.test4;

import java.util.Date;

import net.woodstock.rockframework.domain.Entity;

public class Foo implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private Date				date;

	public Foo() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

}
