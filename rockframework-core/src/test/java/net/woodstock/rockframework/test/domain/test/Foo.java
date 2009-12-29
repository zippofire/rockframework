package net.woodstock.rockframework.test.domain.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateIntRange;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateLength;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNotEmpty;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNotNull;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNull;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateReference;

import org.hibernate.validator.Length;
import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

@XmlRootElement(name = "foo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Foo implements Entity<Integer> {

	private static final long	serialVersionUID	= 1L;

	// Hibernate
	@NotNull
	@Min(value = 0)
	@Max(value = 10)
	// Rockapi
	@ValidateNull(operation = { Operation.SAVE })
	@ValidateNotNull(operation = { Operation.GET, Operation.UPDATE, Operation.DELETE })
	@ValidateIntRange(min = 0, max = 10)
	// Xml
	@XmlElement(name = "id")
	private Integer				id;

	// Hibernate
	@NotNull
	@Length(min = 5, max = 50)
	// Rockapi
	@ValidateNotEmpty(operation = { Operation.SAVE, Operation.UPDATE })
	@ValidateLength(min = 5, max = 50)
	// Xml
	@XmlElement(name = "name")
	private String				name;

	// Hibernate
	@NotNull
	@Valid
	// Rockapi
	@ValidateNotNull(operation = { Operation.SAVE, Operation.UPDATE })
	@ValidateReference(operation = { Operation.SAVE, Operation.UPDATE })
	// Xml
	@XmlElement(name = "bar")
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
