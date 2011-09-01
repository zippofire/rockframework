package br.net.woodstock.rockframework.domain.test.test1;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.Valid;

import br.net.woodstock.rockframework.domain.Entity;


@XmlRootElement(name = "baz")
@XmlAccessorType(XmlAccessType.FIELD)
public class Baz implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	// Hibernate
	@NotNull
	@Min(value = 0)
	@Max(value = 10)
	// Xml
	@XmlElement(name = "id")
	private Integer				id;

	// Hibernate
	@NotNull
	@Min(value = 5)
	@Max(value = 50)
	// Xml
	@XmlElement(name = "name")
	private String				name;

	// Hibernate
	@NotNull
	@Valid
	// Xml
	@XmlElement(name = "foo")
	private Foo					foo;

	// Hibernate
	@NotNull
	@Valid
	// Xml
	@XmlElement(name = "bars")
	private Set<Bar>			bars;

	public Baz() {
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

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(final Foo foo) {
		this.foo = foo;
	}

	public Set<Bar> getBars() {
		return this.bars;
	}

	public void setBars(final Set<Bar> bars) {
		this.bars = bars;
	}

}
