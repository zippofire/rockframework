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
package net.woodstock.rockframework.net.ldap;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;

public class LDAPFilter implements Serializable {

	private static final long			serialVersionUID	= 7941625856466284063L;

	private String						baseName;

	private String[]					attributes;

	private int							limit;

	private Collection<LDAPRestriction>	restrictions;

	public LDAPFilter(final String baseName) {
		super();
		this.baseName = baseName;
		this.restrictions = new LinkedHashSet<LDAPRestriction>();
	}

	public LDAPFilter(final String baseName, final String[] attributes) {
		super();
		this.baseName = baseName;
		this.attributes = attributes;
		this.restrictions = new LinkedHashSet<LDAPRestriction>();
	}

	public LDAPFilter(final String baseName, final int limit) {
		super();
		this.baseName = baseName;
		this.limit = limit;
		this.restrictions = new LinkedHashSet<LDAPRestriction>();
	}

	public LDAPFilter(final String baseName, final String[] attributes, final int limit) {
		super();
		this.baseName = baseName;
		this.attributes = attributes;
		this.limit = limit;
		this.restrictions = new LinkedHashSet<LDAPRestriction>();
	}

	public void add(final LDAPRestriction restriction) {
		this.restrictions.add(restriction);
	}

	public String getBaseName() {
		return this.baseName;
	}

	public void setBaseName(final String baseName) {
		this.baseName = baseName;
	}

	public String[] getAttributes() {
		return this.attributes;
	}

	public void setAttributes(final String[] attributes) {
		this.attributes = attributes;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(final int limit) {
		this.limit = limit;
	}

	public Collection<LDAPRestriction> getRestrictions() {
		return this.restrictions;
	}

	public void setRestrictions(final Collection<LDAPRestriction> restrictions) {
		this.restrictions = restrictions;
	}

	@Override
	public String toString() {
		return this.getFilter();
	}

	public String getFilter() {
		StringBuilder builder = new StringBuilder();
		for (LDAPRestriction restriction : this.restrictions) {
			builder.append(restriction.getRestriction());
		}
		return builder.toString();
	}

}
