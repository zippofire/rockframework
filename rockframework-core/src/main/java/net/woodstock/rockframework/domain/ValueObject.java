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

@SuppressWarnings("unchecked")
public class ValueObject<T> implements Comparable<ValueObject>, Pojo {

	private static final long	serialVersionUID	= 6578883151658994879L;

	private T					value;

	public ValueObject(T value) {
		super();
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	// Comparable
	@Override
	public int compareTo(ValueObject o) {
		if (this.value == null) {
			if (o.value == null) {
				return 0;
			}
			return -1;
		}
		if (o.value == null) {
			return 1;
		}
		if ((this.value instanceof Comparable) && (o.value instanceof Comparable)) {
			Comparable c1 = (Comparable) this.value;
			Comparable c2 = (Comparable) o.value;
			return c1.compareTo(c2);
		}
		return 0;
	}

	// Object
	@Override
	public int hashCode() {
		final int prime = ObjectUtils.HASH_PRIME;
		int result = 1;
		result = prime * result + ((this.value == null) ? 0 : this.value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		ValueObject<?> other = (ValueObject<?>) obj;
		if (this.value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
