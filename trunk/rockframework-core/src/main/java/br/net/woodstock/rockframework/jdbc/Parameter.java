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
package br.net.woodstock.rockframework.jdbc;

import java.io.Serializable;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ObjectUtils;

public class Parameter implements Serializable {

	private static final long	serialVersionUID	= -1779134234723910581L;

	private Object				value;

	private Type				type;

	public Parameter(final Object value, final Type type) {
		super();
		Assert.notEmpty(type, "type");
		this.value = value;
		this.type = type;
	}

	public Type getType() {
		return this.type;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Parameter) {
			Parameter other = (Parameter) obj;
			if (!this.type.equals(other.getType())) {
				return false;
			}
			if ((this.value != null) && (other.getValue() == null)) {
				return false;
			}
			if ((this.value == null) && (other.getValue() != null)) {
				return false;
			}
			if (!this.value.equals(other.getValue())) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hash = this.type.hashCode();
		if (this.value != null) {
			hash = (ObjectUtils.HASH_PRIME * hash) + this.value.hashCode();
		} else {
			hash = ObjectUtils.HASH_PRIME * hash;
		}
		return hash;
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
