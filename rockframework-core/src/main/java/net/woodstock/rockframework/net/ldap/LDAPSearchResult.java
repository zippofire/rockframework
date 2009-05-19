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

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public class LDAPSearchResult {

	private SearchResult	searchResult;

	public LDAPSearchResult(SearchResult searchResult) {
		super();
		this.searchResult = searchResult;
	}

	public String getAttribute(String attributeName) throws NamingException {
		Attributes attributes = this.searchResult.getAttributes();
		if (attributes != null) {
			Attribute attribute = attributes.get(attributeName);
			if ((attribute != null) && (attribute.size() > 0)) {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < attribute.size(); i++) {
					Object o = attribute.get(i);
					builder.append(o.toString());
				}
				return builder.toString().trim();
			}
		}
		return null;
	}

}
