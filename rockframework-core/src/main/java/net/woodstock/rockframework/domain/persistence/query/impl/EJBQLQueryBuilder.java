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
import net.woodstock.rockframework.domain.persistence.query.LikeMode;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;

public abstract class EJBQLQueryBuilder<T> extends AbstractQueryBuilder<T> {

	private boolean				build	= false;

	private Map<String, Object>	options;

	private QueryContext		context;

	private Entity<?>			entity;

	public EJBQLQueryBuilder() {
		super();
		this.options = new HashMap<String, Object>();
		this.options.put(QueryBuilder.OPTION_IGNORE_CASE, Boolean.TRUE);
		this.options.put(QueryBuilder.OPTION_LIKE_MODE, LikeMode.ALL);
	}

	@Override
	public void setEntity(final Entity<?> entity) {
		if (this.build) {
			throw new IllegalStateException("Query alread build");
		}
		this.entity = entity;
	}

	public void setOption(final String name, final Object value) {
		if (this.build) {
			throw new IllegalStateException("Query alread build");
		}
		this.options.put(name, value);
	}

	public void build() {
		if (this.build) {
			throw new IllegalStateException("Query alread build");
		}
		this.context = QueryContextHelper.createQueryContext(this.entity, this.options);
		this.build = true;
	}

	public String getQueryString() {
		if (!this.build) {
			throw new IllegalStateException("Query not build");
		}
		return this.context.getQueryString();
	}

	public T getQuery() {
		if (this.context == null) {
			throw new IllegalStateException("Query not build");
		}
		String sql = this.context.getQueryString();

		T query = this.getQuery(sql);

		this.setQueryOptions(query, this.options);

		for (QueryContextParameter parameter : this.context.getParametersRecursive()) {
			this.setQueryParameter(query, parameter.getAlias(), parameter.getValue());
		}
		return query;
	}

	protected abstract T getQuery(String sql);

	protected abstract void setQueryOptions(Object query, Map<String, Object> options);

	protected abstract void setQueryParameter(Object query, String name, Object value);

}
