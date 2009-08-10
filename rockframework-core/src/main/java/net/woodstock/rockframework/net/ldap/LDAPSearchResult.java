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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;

public class LDAPSearchResult {

	private SearchResult		searchResult;

	private Map<String, String>	attributes;

	public LDAPSearchResult(SearchResult searchResult) throws NamingException {
		super();
		this.searchResult = searchResult;
		this.attributes = new TreeMap<String, String>();
		this.init();
	}

	private void init() throws NamingException {
		Attributes attributes = this.searchResult.getAttributes();
		if (attributes != null) {
			NamingEnumeration<? extends Attribute> ne = attributes.getAll();
			while (ne.hasMoreElements()) {
				Attribute a = ne.nextElement();
				String name = a.getID();
				String value = null;
				if (a.size() > 0) {
					StringBuilder builder = new StringBuilder();
					for (int i = 0; i < a.size(); i++) {
						Object o = a.get(i);
						builder.append(o.toString());
					}
					value = builder.toString();
				}
				this.attributes.put(name, value);
			}
		}
	}

	public String getAttribute(String name) {
		return this.attributes.get(name);
	}

	public Set<Entry<String, String>> getAttributes() {
		return this.attributes.entrySet();
	}

	public Set<String> getAttributeNames() {
		return this.attributes.keySet();
	}

	public Collection<String> getAttributeValues() {
		return this.attributes.values();
	}

}
