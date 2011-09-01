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
package br.net.woodstock.rockframework.domain.persistence.orm.impl;

import java.util.HashMap;
import java.util.Map;

import br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata;


public class QueryMetadataImpl implements QueryMetadata {

	private static final long	serialVersionUID	= 4971001334323658467L;

	private String				query;

	private Map<String, Object>	parameters;

	private Map<String, Object>	options;

	public QueryMetadataImpl(final String query) {
		super();
		this.query = query;
		this.parameters = new HashMap<String, Object>();
		this.options = new HashMap<String, Object>();
	}

	public QueryMetadataImpl(final String query, final Map<String, Object> parameters, final Map<String, Object> options) {
		super();
		this.query = query;
		this.parameters = parameters;
		this.options = options;
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	@Override
	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	@Override
	public Map<String, Object> getOptions() {
		return this.options;
	}

}
