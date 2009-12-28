package net.woodstock.rockframework.domain.test4;

import java.util.Date;

import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateDateInterval;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateDateInterval.IntervalType;
import net.woodstock.rockframework.domain.util.AbstractEntity;

public class Foo extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= 1L;

	private Integer				id;

	@ValidateDateInterval(past = 100, future = 100, type = IntervalType.YEARS)
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
