package net.woodstock.rockframework.test.domain.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.woodstock.rockframework.domain.Entity;

import org.hibernate.validator.Length;
import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.NotNull;

@XmlRootElement(name = "bar")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bar implements Entity<Integer> {

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
	@XmlElement(name = "value")
	private String				value;

	private Baz					baz;

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

	public Baz getBaz() {
		return this.baz;
	}

	public void setBaz(final Baz baz) {
		this.baz = baz;
	}

	public Foo getFoo() {
		return this.foo;
	}

	public void setFoo(final Foo foo) {
		this.foo = foo;
	}

}
