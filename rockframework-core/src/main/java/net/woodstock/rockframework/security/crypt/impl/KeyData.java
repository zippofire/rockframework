package net.woodstock.rockframework.security.crypt.impl;

import java.io.Serializable;
import java.security.Key;

public class KeyData implements Serializable {

	private static final long	serialVersionUID	= -2115977775361614829L;

	private String				algorithm;

	private Key					key;

	public KeyData(final String algorithm, final Key key) {
		super();
		this.algorithm = algorithm;
		this.key = key;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

	public Key getKey() {
		return this.key;
	}

}
