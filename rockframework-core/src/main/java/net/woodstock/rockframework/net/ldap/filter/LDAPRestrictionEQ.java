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
package net.woodstock.rockframework.net.ldap.filter;

class LDAPRestrictionEQ extends LDAPRestriction {

	private static final long	serialVersionUID	= -6948256925994166639L;

	public LDAPRestrictionEQ(final String propertyName, final Object value) {
		super();
		this.setPropertyName(propertyName);
		this.setValue(value);
	}

	@Override
	public String getRestriction() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(this.getPropertyName());
		builder.append("=");
		builder.append(this.getValue().toString());
		builder.append(")");
		return builder.toString();
	}
}
