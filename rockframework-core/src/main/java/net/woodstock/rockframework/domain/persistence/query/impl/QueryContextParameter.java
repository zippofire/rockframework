/*
 * This file is part of rockframework.
 * 
 * rockframework is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockframework is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package net.woodstock.rockframework.domain.persistence.query.impl;

import java.io.Serializable;

class QueryContextParameter implements Serializable {

	private static final long	serialVersionUID	= -656617185510963287L;

	private String				name;

	private String				sqlName;

	private String				alias;

	private String				sqlAlias;

	private Object				value;

	private Operator			operator;

	public QueryContextParameter() {
		super();
	}

	public QueryContextParameter(String name, String sqlName, String alias, String sqlAlias, Object value,
			Operator operator) {
		super();
		this.name = name;
		this.sqlName = sqlName;
		this.alias = alias;
		this.sqlAlias = sqlAlias;
		this.value = value;
		this.operator = operator;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSqlName() {
		return this.sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSqlAlias() {
		return this.sqlAlias;
	}

	public void setSqlAlias(String sqlAlias) {
		this.sqlAlias = sqlAlias;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Operator getOperator() {
		return this.operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public static enum Operator {
		EQUALS("="), LIKE("LIKE");

		private String	operator;

		private Operator(String operator) {
			this.operator = operator;
		}

		public String getOperator() {
			return this.operator;
		}

		@Override
		public String toString() {
			return this.operator;
		}
	}

}
