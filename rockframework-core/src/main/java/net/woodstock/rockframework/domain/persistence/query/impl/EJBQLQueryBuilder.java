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
package net.woodstock.rockframework.domain.persistence.query.impl;

import java.util.HashMap;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.query.BuilderException;
import net.woodstock.rockframework.domain.persistence.query.LikeMode;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;

public abstract class EJBQLQueryBuilder extends AbstractQueryBuilder {

	private Map<String, Object>	options;

	private QueryContext		context;

	public EJBQLQueryBuilder() {
		super();
		this.options = new HashMap<String, Object>();
		this.options.put(QueryBuilder.OPTION_IGNORE_CASE, Boolean.TRUE);
		this.options.put(QueryBuilder.OPTION_LIKE_MODE, LikeMode.ALL);
	}

	public QueryBuilder setOption(String name, Object value) {
		this.options.put(name, value);
		return this;
	}

	protected Object getOption(String name) {
		return this.options.get(name);
	}

	protected boolean containsOption(String name) {
		return this.options.containsKey(name);
	}

	public QueryBuilder parse(Entity<?> e) throws BuilderException {
		this.context = QueryContextHelper.createQueryContext(e, this.options);
		return this;
	}

	public String getQueryString() throws BuilderException {
		if (this.context == null) {
			throw new IllegalStateException("Entity not parsed");
		}
		return this.context.getQueryString();
	}

	public Object getQuery(Object manager) throws BuilderException {
		if (this.context == null) {
			throw new IllegalStateException("Entity not parsed");
		}
		String sql = this.context.getQueryString();

		Object query = this.getQueryLocal(sql, manager);

		this.setQueryOptions(query, this.options);

		for (QueryContextParameter parameter : this.context.getParametersRecursive()) {
			this.setQueryParameter(query, parameter.getAlias(), parameter.getValue());
		}
		return query;
	}

	protected abstract Object getQueryLocal(String sql, Object manager) throws BuilderException;

	protected abstract void setQueryOptions(Object query, Map<String, Object> options) throws BuilderException;

	protected abstract void setQueryParameter(Object query, String name, Object value) throws BuilderException;

}
