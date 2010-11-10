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

import java.util.Collection;
import java.util.LinkedHashSet;

import net.woodstock.rockframework.net.ldap.LDAPRestriction;
import net.woodstock.rockframework.net.ldap.LDAPRestrictionGroup;

class LDAPRestrictionOr extends LDAPRestrictionGroup {

	private static final long			serialVersionUID	= 3117224346910387931L;

	private Collection<LDAPRestriction>	restrictions;

	public LDAPRestrictionOr() {
		super();
		this.restrictions = new LinkedHashSet<LDAPRestriction>();
	}

	@Override
	public void add(final LDAPRestriction restriction) {
		this.restrictions.add(restriction);
	}

	@Override
	public String getRestriction() {
		StringBuilder builder = new StringBuilder();
		builder.append("(|");
		for (LDAPRestriction restriction : this.restrictions) {
			builder.append(restriction.getRestriction());
		}
		builder.append(")");
		return builder.toString();
	}
}
