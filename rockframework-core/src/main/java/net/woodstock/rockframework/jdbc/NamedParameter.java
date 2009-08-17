/*
 * This file is part of rockapi.
 * 
 * rockapi is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockapi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package net.woodstock.rockframework.jdbc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Ref;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class NamedParameter extends Parameter {

	private static final long	serialVersionUID	= -1160839266198779724L;

	private String				name;

	public NamedParameter(String name, BigDecimal b) {
		super(b);
		this.name = name;
	}

	public NamedParameter(String name, BigInteger b) {
		super(b);
		this.name = name;
	}

	public NamedParameter(String name, boolean b, SqlType type) {
		super(b, type);
		this.name = name;
	}

	public NamedParameter(String name, boolean b) {
		super(b);
		this.name = name;
	}

	public NamedParameter(String name, Boolean b) {
		super(b);
		this.name = name;
	}

	public NamedParameter(String name, byte b, SqlType type) {
		super(b, type);
		this.name = name;
	}

	public NamedParameter(String name, byte b) {
		super(b);
		this.name = name;
	}

	public NamedParameter(String name, Byte b) {
		super(b);
		this.name = name;
	}

	public NamedParameter(String name, char c, SqlType type) {
		super(c, type);
		this.name = name;
	}

	public NamedParameter(String name, char c) {
		super(c);
		this.name = name;
	}

	public NamedParameter(String name, Character c) {
		super(c);
		this.name = name;
	}

	public NamedParameter(String name, Date d) {
		super(d);
		this.name = name;
	}

	public NamedParameter(String name, java.sql.Date d) {
		super(d);
		this.name = name;
	}

	public NamedParameter(String name, double d, SqlType type) {
		super(d, type);
		this.name = name;
	}

	public NamedParameter(String name, double d) {
		super(d);
		this.name = name;
	}

	public NamedParameter(String name, Double d) {
		super(d);
		this.name = name;
	}

	public NamedParameter(String name, float f, SqlType type) {
		super(f, type);
		this.name = name;
	}

	public NamedParameter(String name, float f) {
		super(f);
		this.name = name;
	}

	public NamedParameter(String name, Float f) {
		super(f);
		this.name = name;
	}

	public NamedParameter(String name, int i, SqlType type) {
		super(i, type);
		this.name = name;
	}

	public NamedParameter(String name, int i) {
		super(i);
		this.name = name;
	}

	public NamedParameter(String name, Integer i) {
		super(i);
		this.name = name;
	}

	public NamedParameter(String name, long l, SqlType type) {
		super(l, type);
		this.name = name;
	}

	public NamedParameter(String name, long l) {
		super(l);
		this.name = name;
	}

	public NamedParameter(String name, Long l) {
		super(l);
		this.name = name;
	}

	public NamedParameter(String name, Object value, SqlType type) {
		super(value, type);
		this.name = name;
	}

	public NamedParameter(String name, Ref r) {
		super(r);
		this.name = name;
	}

	public NamedParameter(String name, short s, SqlType type) {
		super(s, type);
		this.name = name;
	}

	public NamedParameter(String name, short s) {
		super(s);
		this.name = name;
	}

	public NamedParameter(String name, Short s) {
		super(s);
		this.name = name;
	}

	public NamedParameter(String name, String s) {
		super(s);
		this.name = name;
	}

	public NamedParameter(String name, Time t) {
		super(t);
		this.name = name;
	}

	public NamedParameter(String name, Timestamp t) {
		super(t);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NamedParameter) {
			NamedParameter other = (NamedParameter) obj;
			return (this.getType().type() == other.getType().type()) && this.getValue().equals(other.getValue()) && this.getName().equals(other.getName());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.name);
		s.append("[");
		s.append(this.getType().name());
		s.append("]: ");
		s.append(this.getValue().toString());
		return s.toString();
	}

}
