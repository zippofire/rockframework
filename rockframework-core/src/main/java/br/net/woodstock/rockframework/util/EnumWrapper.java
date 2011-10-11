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
package br.net.woodstock.rockframework.util;

public class EnumWrapper<E extends Enum<E>> {

	private Enum<E>	wrapped;

	public EnumWrapper(final Enum<E> wrapped) {
		super();
		Assert.notNull(wrapped, "enum");
	}

	public String getName() {
		return this.wrapped.name();
	}

	public String getLowerName() {
		return this.wrapped.name().toLowerCase();
	}

	public String getUpperName() {
		return this.wrapped.name().toUpperCase();
	}

	public int getOrdinal() {
		return this.wrapped.ordinal();
	}

}
