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

public class NamedParameter extends Parameter {

	private static final long	serialVersionUID	= -1160839266198779724L;

	private String				name;

	public NamedParameter(final String name, final Object value, final Type type) {
		super(value, type);
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof NamedParameter) {
			NamedParameter other = (NamedParameter) obj;
			if (!this.getName().equals(other.getName())) {
				return false;
			}
			if (!this.getValue().equals(other.getValue())) {
				return false;
			}
			if (this.getType() != other.getType()) {
				return false;
			}
			return true;
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
