package br.net.woodstock.rockframework.domain.test.test2;

import javax.persistence.Column;

@javax.persistence.Entity
public class Bar2 extends Bar {

	@Column(name = "value2", length = 10, nullable = false)
	private String	value2;

	public Bar2() {
		super();
	}

	public String getValue2() {
		return this.value2;
	}

	public void setValue2(final String value2) {
		this.value2 = value2;
	}

}
