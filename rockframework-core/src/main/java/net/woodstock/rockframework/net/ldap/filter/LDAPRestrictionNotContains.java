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

class LDAPRestrictionNotContains extends LDAPRestriction {

	private static final long	serialVersionUID	= 7524499520806772784L;

	public LDAPRestrictionNotContains(String propertyName) {
		super();
		this.setPropertyName(propertyName);
	}

	@Override
	public String getRestriction() {
		StringBuilder builder = new StringBuilder();
		builder.append("(!(");
		builder.append(this.getPropertyName());
		builder.append("=*))");
		return builder.toString();
	}
}
