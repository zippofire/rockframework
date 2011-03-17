package net.woodstock.rockframework.domain.test.test1;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.woodstock.rockframework.domain.Entity;

import org.hibernate.validator.Length;
import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.Valid;

@XmlRootElement(name = "foo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Foo implements Entity<Integer> {

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
	@Length(min = 5, max = 50)
	// Xml
	@XmlElement(name = "name")
	private String				name;

	// Hibernate
	@NotNull
	@Valid
	// Xml
	@XmlElement(name = "bar")
	private Bar					bar;

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
