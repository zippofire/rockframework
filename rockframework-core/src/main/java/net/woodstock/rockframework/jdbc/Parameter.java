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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Ref;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Parameter implements Serializable {

	private static final long	serialVersionUID	= -1779134234723910581L;

	private Object				value;

	private SqlType				type;

	public Parameter(boolean b) {
		this(Boolean.valueOf(b), SqlType.BOOLEAN);
	}

	public Parameter(byte b) {
		this(Byte.valueOf(b), SqlType.TINYINT);
	}

	public Parameter(char c) {
		this(Character.valueOf(c), SqlType.CHAR);
	}

	public Parameter(double d) {
		this(Double.valueOf(d), SqlType.DOUBLE);
	}

	public Parameter(float f) {
		this(Float.valueOf(f), SqlType.FLOAT);
	}

	public Parameter(int i) {
		this(Integer.valueOf(i), SqlType.INTEGER);
	}

	public Parameter(long l) {
		this(Long.valueOf(l), SqlType.BIGINT);
	}

	public Parameter(short s) {
		this(Short.valueOf(s), SqlType.SMALLINT);
	}

	public Parameter(BigDecimal b) {
		this(b, SqlType.NUMERIC);
	}

	public Parameter(BigInteger b) {
		this(b, SqlType.NUMERIC);
	}

	public Parameter(Boolean b) {
		this(b, SqlType.BOOLEAN);
	}

	public Parameter(Byte b) {
		this(b, SqlType.TINYINT);
	}

	public Parameter(Character c) {
		this(c, SqlType.CHAR);
	}

	public Parameter(Date d) {
		this(d, SqlType.DATE);
	}

	public Parameter(java.sql.Date d) {
		this(d, SqlType.DATE);
	}

	public Parameter(Double d) {
		this(d, SqlType.DOUBLE);
	}

	public Parameter(Float f) {
		this(f, SqlType.FLOAT);
	}

	public Parameter(Integer i) {
		this(i, SqlType.INTEGER);
	}

	public Parameter(Long l) {
		this(l, SqlType.BIGINT);
	}

	public Parameter(Ref r) {
		this(r, SqlType.REF);
	}

	public Parameter(Short s) {
		this(s, SqlType.SMALLINT);
	}

	public Parameter(Timestamp t) {
		this(t, SqlType.TIMESTAMP);
	}

	public Parameter(Time t) {
		this(t, SqlType.TIME);
	}

	public Parameter(String s) {
		this(s, SqlType.VARCHAR);
	}

	public Parameter(boolean b, SqlType type) {
		this(Boolean.valueOf(b), type);
	}

	public Parameter(byte b, SqlType type) {
		this(Byte.valueOf(b), type);
	}

	public Parameter(char c, SqlType type) {
		this(Character.valueOf(c), type);
	}

	public Parameter(double d, SqlType type) {
		this(Double.valueOf(d), type);
	}

	public Parameter(float f, SqlType type) {
		this(Float.valueOf(f), type);
	}

	public Parameter(int i, SqlType type) {
		this(Integer.valueOf(i), type);
	}

	public Parameter(long l, SqlType type) {
		this(Long.valueOf(l), type);
	}

	public Parameter(short s, SqlType type) {
		this(Short.valueOf(s), type);
	}

	public Parameter(Object value, SqlType type) {
		this.value = value;
		this.type = type;
	}

	public SqlType getType() {
		return this.type;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Parameter) {
			Parameter other = (Parameter) obj;
			return (this.type.type() == other.getType().type()) && this.value.equals(other.getValue());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.type.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(this.type.name());
		s.append(": ");
		s.append(this.value.toString());
		return s.toString();
	}

}
