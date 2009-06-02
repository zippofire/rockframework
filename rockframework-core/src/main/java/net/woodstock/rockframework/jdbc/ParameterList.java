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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Ref;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;

public class ParameterList extends LinkedList<Parameter> {

	private static final long	serialVersionUID	= 17529574718383571L;

	public ParameterList() {
		super();
	}

	public ParameterList(boolean... params) {
		super();
		for (boolean p : params) {
			this.add(p);
		}
	}

	public ParameterList(byte... params) {
		super();
		for (byte p : params) {
			this.add(p);
		}
	}

	public ParameterList(char... params) {
		super();
		for (char p : params) {
			this.add(p);
		}
	}

	public ParameterList(double... params) {
		super();
		for (double p : params) {
			this.add(p);
		}
	}

	public ParameterList(float... params) {
		super();
		for (float p : params) {
			this.add(p);
		}
	}

	public ParameterList(int... params) {
		super();
		for (int p : params) {
			this.add(p);
		}
	}

	public ParameterList(long... params) {
		super();
		for (long p : params) {
			this.add(p);
		}
	}

	public ParameterList(short... params) {
		super();
		for (short p : params) {
			this.add(p);
		}
	}

	public ParameterList(String... params) {
		super();
		for (String p : params) {
			this.add(p);
		}
	}

	public ParameterList(Object... params) {
		super();
		for (Object p : params) {
			try {
				Method m = this.getClass().getMethod("add", p.getClass());
				m.invoke(this, p);
			} catch (Exception e) {
				throw new RuntimeException("Invalid parameter type: '" + p.getClass().getCanonicalName()
						+ "'");
			}
		}
	}

	public void add(boolean b) {
		this.add(new Parameter(b));
	}

	public void add(byte b) {
		this.add(new Parameter(b));
	}

	public void add(char c) {
		this.add(new Parameter(c));
	}

	public void add(double d) {
		this.add(new Parameter(d));
	}

	public void add(float f) {
		this.add(new Parameter(f));
	}

	public void add(int i) {
		this.add(new Parameter(i));
	}

	public void add(long l) {
		this.add(new Parameter(l));
	}

	public void add(short s) {
		this.add(new Parameter(s));
	}

	public void add(int index, boolean b) {
		this.add(index, new Parameter(b));
	}

	public void add(int index, byte b) {
		this.add(index, new Parameter(b));
	}

	public void add(int index, char c) {
		this.add(index, new Parameter(c));
	}

	public void add(int index, double d) {
		this.add(index, new Parameter(d));
	}

	public void add(int index, float f) {
		this.add(index, new Parameter(f));
	}

	public void add(int index, int i) {
		this.add(index, new Parameter(i));
	}

	public void add(int index, long l) {
		this.add(index, new Parameter(l));
	}

	public void add(int index, short s) {
		this.add(index, new Parameter(s));
	}

	public void add(BigDecimal b) {
		this.add(new Parameter(b));
	}

	public void add(BigInteger b) {
		this.add(new Parameter(b));
	}

	public void add(Boolean b) {
		this.add(new Parameter(b));
	}

	public void add(Byte b) {
		this.add(new Parameter(b));
	}

	public void add(Character c) {
		this.add(new Parameter(c));
	}

	public void add(Date d) {
		this.add(new Parameter(d));
	}

	public void add(java.sql.Date d) {
		this.add(new Parameter(d));
	}

	public void add(Double d) {
		this.add(new Parameter(d));
	}

	public void add(Float f) {
		this.add(new Parameter(f));
	}

	public void add(Integer i) {
		this.add(new Parameter(i));
	}

	public void add(Ref r) {
		this.add(new Parameter(r));
	}

	public void add(Long l) {
		this.add(new Parameter(l));
	}

	public void add(Short s) {
		this.add(new Parameter(s));
	}

	public void add(String s) {
		this.add(new Parameter(s));
	}

	public void add(Timestamp t) {
		this.add(new Parameter(t));
	}

	public void add(Time t) {
		this.add(new Parameter(t));
	}

	public void add(int index, BigDecimal b) {
		this.add(index, new Parameter(b));
	}

	public void add(int index, BigInteger b) {
		this.add(index, new Parameter(b));
	}

	public void add(int index, Boolean b) {
		this.add(index, new Parameter(b));
	}

	public void add(int index, Byte b) {
		this.add(index, new Parameter(b));
	}

	public void add(int index, Character c) {
		this.add(index, new Parameter(c));
	}

	public void add(int index, Date d) {
		this.add(index, new Parameter(d));
	}

	public void add(int index, java.sql.Date d) {
		this.add(index, new Parameter(d));
	}

	public void add(int index, Double d) {
		this.add(index, new Parameter(d));
	}

	public void add(int index, Float f) {
		this.add(index, new Parameter(f));
	}

	public void add(int index, Integer i) {
		this.add(index, new Parameter(i));
	}

	public void add(int index, Long l) {
		this.add(index, new Parameter(l));
	}

	public void add(int index, Ref r) {
		this.add(index, new Parameter(r));
	}

	public void add(int index, Short s) {
		this.add(index, new Parameter(s));
	}

	public void add(int index, String s) {
		this.add(index, new Parameter(s));
	}

	public void add(int index, Timestamp t) {
		this.add(index, new Parameter(t));
	}

	public void add(int index, Time t) {
		this.add(index, new Parameter(t));
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		boolean first = true;
		s.append("[");
		for (Parameter p : this) {
			if (first) {
				s.append(p.toString());
				first = false;
			} else {
				s.append(", ");
				s.append(p.toString());
			}
		}
		s.append("]");
		return s.toString();
	}

}
