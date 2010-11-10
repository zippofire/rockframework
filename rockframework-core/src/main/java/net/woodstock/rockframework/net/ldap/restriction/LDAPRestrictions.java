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
package net.woodstock.rockframework.net.ldap.restriction;

import net.woodstock.rockframework.net.ldap.LDAPRestriction;
import net.woodstock.rockframework.net.ldap.LDAPRestrictionGroup;

public abstract class LDAPRestrictions {

	private LDAPRestrictions() {
		//
	}

	// Static(Like Hibernate)
	public static LDAPRestriction contains(final String propertyName) {
		return new LDAPRestrictionContains(propertyName);
	}

	public static LDAPRestriction eq(final String propertyName, final Object value) {
		return new LDAPRestrictionEQ(propertyName, value);
	}

	public static LDAPRestriction like(final String propertyName, final Object value) {
		return new LDAPRestrictionLike(propertyName, value);
	}

	public static LDAPRestriction ne(final String propertyName, final Object value) {
		return new LDAPRestrictionNE(propertyName, value);
	}

	public static LDAPRestriction notContains(final String propertyName) {
		return new LDAPRestrictionNotContains(propertyName);
	}

	// Group
	public static LDAPRestrictionGroup and() {
		return new LDAPRestrictionAnd();
	}

	public static LDAPRestrictionGroup or() {
		return new LDAPRestrictionOr();
	}

}
