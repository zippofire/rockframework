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
package br.net.woodstock.rockframework.net.ldap.filter;

import java.io.Serializable;

public abstract class LDAPRestriction implements Serializable {

	private static final long	serialVersionUID	= 1753834349732544941L;

	private String				propertyName;

	private Object				value;

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.getRestriction();
	}

	public abstract String getRestriction();

	// Static(Like Hibernate)
	public static LDAPRestriction contains(final String propertyName) {
		LDAPRestriction restriction = new LDAPRestrictionContains(propertyName);
		return restriction;
	}

	public static LDAPRestriction eq(final String propertyName, final Object value) {
		LDAPRestriction restriction = new LDAPRestrictionEQ(propertyName, value);
		return restriction;
	}

	public static LDAPRestriction like(final String propertyName, final Object value) {
		LDAPRestriction restriction = new LDAPRestrictionLike(propertyName, value);
		return restriction;
	}

	public static LDAPRestriction ne(final String propertyName, final Object value) {
		LDAPRestriction restriction = new LDAPRestrictionNE(propertyName, value);
		return restriction;
	}

	public static LDAPRestriction notContains(final String propertyName) {
		LDAPRestriction restriction = new LDAPRestrictionNotContains(propertyName);
		return restriction;
	}

}
