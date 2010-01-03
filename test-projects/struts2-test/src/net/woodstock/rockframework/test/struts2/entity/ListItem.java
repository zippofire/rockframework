package net.woodstock.rockframework.test.struts2.entity;

import net.woodstock.rockframework.domain.util.IntegerEntity;

public class ListItem extends IntegerEntity {

	private static final long	serialVersionUID	= -6111184164689839730L;

	private Integer				id;

	private String				name;

	private Boolean				status;

	public ListItem() {
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

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
