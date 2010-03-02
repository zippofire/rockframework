package net.woodstoc.rockframework.test.struts;

import net.woodstock.rockframework.web.struts.BaseForm;

public class PeopleForm extends BaseForm {

	private static final long	serialVersionUID	= 1L;

	private People				people;

	public PeopleForm() {
		super();
		this.people = new People();
	}

	public People getPeople() {
		return this.people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

}
