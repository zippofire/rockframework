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
package br.net.woodstock.rockframework.persistence;

import java.io.Serializable;

import br.net.woodstock.rockframework.domain.Entity;

public abstract class AbstractEntity<T extends Serializable> implements Entity<T> {

	private static final long	serialVersionUID	= -2875524758123268708L;

	public AbstractEntity() {
		super();
	}

	public AbstractEntity(final T id) {
		super();
		this.setId(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}

		AbstractEntity<T> e = (AbstractEntity<T>) obj;

		T t1 = this.getId();
		T t2 = e.getId();

		if ((t1 != null) && (t2 != null)) {
			return t1.equals(t2);
		}
		if (((t1 != null) && (t2 == null)) || ((t1 == null) && (t2 != null))) {
			return false;
		}

		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		T id = this.getId();
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName());
		builder.append("[");
		T id = this.getId();
		if (id != null) {
			builder.append(id.toString());
		} else {
			builder.append("undeffined");
		}
		builder.append("]");
		return builder.toString();
	}

}
