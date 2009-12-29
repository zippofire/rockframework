package net.woodstock.rockframework.test.domain.test3;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import net.woodstock.rockframework.domain.util.AbstractEntity;

@javax.persistence.Entity
public class Bar extends AbstractEntity<Integer> {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer				id;

	@Column(name = "value", length = 10, nullable = false)
	private String				value;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_foo", referencedColumnName = "id")
	private Foo					foo;

	public Bar() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(final Foo foo) {
		this.foo = foo;
	}

}
