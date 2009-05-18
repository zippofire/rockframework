package net.woodstock.rockframework.domain.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.validation.local.Operation;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateIntRange;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateLength;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNotEmpty;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNotNull;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateNull;

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
	// Rockapi
	@ValidateNull(operation = { Operation.CREATE })
	@ValidateNotNull(operation = { Operation.RETRIEVE, Operation.UPDATE, Operation.DELETE })
	@ValidateIntRange(min = 0, max = 10)
	// Xml
	@XmlElement(name = "id")
	private Integer				id;

	// Hibernate
	@NotNull
	@Min(value = 5)
	@Max(value = 50)
	// Rockapi
	@ValidateNotEmpty(operation = { Operation.CREATE, Operation.UPDATE })
	@ValidateLength(min = 5, max = 50)
	// Xml
	@XmlElement(name = "value")
	private String				value;

	public Bar() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
