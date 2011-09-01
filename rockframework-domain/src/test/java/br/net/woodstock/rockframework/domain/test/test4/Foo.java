package br.net.woodstock.rockframework.domain.test.test4;

import java.util.Date;

import br.net.woodstock.rockframework.domain.Entity;


public class Foo implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	private Date				date;

	public Foo() {
		super();
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
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
