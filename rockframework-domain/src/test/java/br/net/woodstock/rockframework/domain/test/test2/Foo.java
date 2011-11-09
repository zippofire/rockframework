package br.net.woodstock.rockframework.domain.test.test2;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Foo {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer		id;

	@Column(name = "name", length = 10, nullable = false)
	private String		name;

	@OneToMany(mappedBy = "foo")
	private Set<Bar>	bars;

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

	public Set<Bar> getBars() {
		return this.bars;
	}

	public void setBars(final Set<Bar> bars) {
		this.bars = bars;
	}
}
