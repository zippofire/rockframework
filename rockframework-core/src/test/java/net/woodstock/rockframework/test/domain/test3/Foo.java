package net.woodstock.rockframework.test.domain.test3;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.woodstock.rockframework.domain.Entity;

public class Foo implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer				id;

	@Column(name = "name", length = 10, nullable = false)
	private String				name;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_bar", referencedColumnName = "id")
	private Bar					bar;

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

	public Bar getBar() {
		return this.bar;
	}

	public void setBar(final Bar bar) {
		this.bar = bar;
	}

}
