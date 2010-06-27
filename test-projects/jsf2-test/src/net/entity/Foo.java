package net.woodstoc.rockframework.jsf.test.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Foo implements Serializable {

	private static final long serialVersionUID = -2808735155422408898L;

	private Integer id;

	@NotNull
	@Size(min = 5, max = 50)
	private String name;

	public Foo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
