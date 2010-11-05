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
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.directory.SearchResult;

public class LDAPSearchResult {

	private SearchResult		searchResult;

	private Map<String, String>	attributes;

	public LDAPSearchResult(final SearchResult searchResult, final Map<String, String> attributes) {
		super();
		this.searchResult = searchResult;
		this.attributes = attributes;
	}

	public SearchResult getSearchResult() {
		return this.searchResult;
	}

	public String getAttribute(final String name) {
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
