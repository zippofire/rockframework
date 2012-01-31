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
package br.net.woodstock.rockframework.security.store;

import java.io.Serializable;

import br.net.woodstock.rockframework.security.Alias;

public class StoreEntry implements Serializable {

	private static final long	serialVersionUID	= -4601283475496635273L;

	private Alias				alias;

	private Object				value;

	private StoreEntryType		type;

	public StoreEntry(final Alias alias, final Object value, final StoreEntryType type) {
		super();
		this.alias = alias;
		this.value = value;
		this.type = type;
	}

	public Alias getAlias() {
		return this.alias;
	}

	public Object getValue() {
		return this.value;
	}

	public StoreEntryType getType() {
		return this.type;
	}

	@Override
	public String toString() {
		if (this.value != null) {
			return this.value.toString();
		}
		return super.toString();
	}

}
