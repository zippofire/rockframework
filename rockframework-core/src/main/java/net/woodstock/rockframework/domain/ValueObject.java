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
package net.woodstock.rockframework.domain;

import net.woodstock.rockframework.utils.ObjectUtils;

public class ValueObject<T> implements Pojo {

	private static final long	serialVersionUID	= 5096193480689528838L;

	private T					value;

	public ValueObject(final T value) {
		super();
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	// Object
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (obj instanceof ValueObject<?>) {
			return false;
		}

		ValueObject<?> other = (ValueObject<?>) obj;
		if ((this.value == null) && (other.value != null)) {
			return false;
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = ObjectUtils.HASH_PRIME;
		int result = 1;
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValueObject[");
		builder.append(this.value);
		builder.append("]");
		return builder.toString();
	}

}
