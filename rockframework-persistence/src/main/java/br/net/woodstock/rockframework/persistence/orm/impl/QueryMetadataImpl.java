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
package br.net.woodstock.rockframework.persistence.orm.impl;

import java.util.HashMap;
import java.util.Map;

import br.net.woodstock.rockframework.persistence.orm.Constants;
import br.net.woodstock.rockframework.persistence.orm.Page;
import br.net.woodstock.rockframework.persistence.orm.QueryMetadata;

public class QueryMetadataImpl implements QueryMetadata {

	private static final long		serialVersionUID	= 4971001334323658467L;

	private static final Integer	MAX_RESULT			= Integer.valueOf(50);

	private String					query;

	private String					countQuery;

	private Page					page;

	private boolean					cacheEnabled;

	private boolean					named;

	private boolean					nativeSQL;

	private Map<String, Object>		parameters;

	private Map<String, Object>		options;

	public QueryMetadataImpl() {
		super();
	}

	public QueryMetadataImpl(final String query) {
		this(query, null, null, null, null);
	}

	public QueryMetadataImpl(final String query, final String countQuery, final Page page) {
		this(query, countQuery, page, null, null);
	}

	public QueryMetadataImpl(final String query, final Map<String, Object> parameters, final Map<String, Object> options) {
		this(query, null, null, parameters, options);
	}

	public QueryMetadataImpl(final String query, final String countQuery, final Page page, final Map<String, Object> parameters, final Map<String, Object> options) {
		super();
		this.query = query;
		this.countQuery = countQuery;
		this.page = page;
		this.parameters = parameters;
		this.options = options;

		if (this.options == null) {
			this.options = new HashMap<String, Object>();
		}

		if (!this.options.containsKey(Constants.OPTION_MAX_RESULT)) {
			this.options.put(Constants.OPTION_MAX_RESULT, QueryMetadataImpl.MAX_RESULT);
		}
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	public void setQuery(final String query) {
		this.query = query;
	}

	@Override
	public String getCountQuery() {
		return this.countQuery;
	}

	public void setCountQuery(final String countQuery) {
		this.countQuery = countQuery;
	}

	@Override
	public Page getPage() {
		return this.page;
	}

	public void setPage(final Page page) {
		this.page = page;
	}

	@Override
	public boolean isCacheEnabled() {
		return this.cacheEnabled;
	}

	public void setCacheEnabled(final boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}

	@Override
	public boolean isNamed() {
		return this.named;
	}

	public void setNamed(final boolean named) {
		this.named = named;
	}

	@Override
	public boolean isNativeSQL() {
		return this.nativeSQL;
	}

	public void setNativeSQL(final boolean nativeSQL) {
		this.nativeSQL = nativeSQL;
	}

	@Override
	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	public void setParameters(final Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Map<String, Object> getOptions() {
		return this.options;
	}

	public void setOptions(final Map<String, Object> options) {
		this.options = options;
	}

}
