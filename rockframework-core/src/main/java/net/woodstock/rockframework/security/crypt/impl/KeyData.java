package net.woodstock.rockframework.security.crypt.impl;

import java.io.Serializable;
import java.security.Key;

import net.woodstock.rockframework.security.common.Charset;


public class KeyData  implements Serializable {

	private static final long	serialVersionUID	= -2115977775361614829L;

	private String				algorithm;

	private Key					key;

	private Charset				charset;

	public KeyData(String algorithm, Key key, Charset charset) {
		super();
		this.algorithm = algorithm;
		this.key = key;
		this.charset = charset;
	}

	public String getAlgorithm() {
		return this.algorithm;
	}

	public Key getKey() {
		return this.key;
	}

	public Charset getCharset() {
		return this.charset;
	}


}
