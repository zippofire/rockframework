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

import net.woodstock.rockframework.config.CoreLog;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.query.LikeMode;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.util.Constants;

public abstract class EJBQLQueryBuilder<T> extends AbstractQueryBuilder<T> {

	private boolean				build;

	private Map<String, Object>	options;

	private QueryContext		context;

	private Entity<?>			entity;

	public EJBQLQueryBuilder() {
		super();
		this.reset();
	}

	@Override
	public void reset() {
		this.build = false;
		this.context = null;
		this.entity = null;
		this.options = new HashMap<String, Object>();
		this.options.put(Constants.OPTION_IGNORE_CASE, Boolean.TRUE);
		this.options.put(Constants.OPTION_LIKE_MODE, LikeMode.ALL);
	}

	@Override
	public QueryBuilder<T> setEntity(final Entity<?> entity) {
		if (this.build) {
			CoreLog.getInstance().getLog().warn("Query alread build");
		} else {
			this.entity = entity;
		}
		return this;
	}

	public QueryBuilder<T> setOption(final String name, final Object value) {
		if (this.build) {
			CoreLog.getInstance().getLog().warn("Query alread build");
		} else {
			this.options.put(name, value);
		}
		return this;
	}

	public T getQuery() {
		if (this.context == null) {
			this.build();
		}
		String sql = this.context.getQueryString();

		T query = this.getQuery(sql);

		this.setQueryOptions(query, this.options);

		for (QueryContextParameter parameter : this.context.getParametersRecursive()) {
			this.setQueryParameter(query, parameter.getAlias(), parameter.getValue());
		}
		return query;
	}

	protected QueryContext getContext() {
		if (this.context == null) {
			this.build();
		}
		return this.context;
	}

	private void build() {
		this.context = QueryContextHelper.createQueryContext(this.entity, this.options);
		this.build = true;
	}

	protected abstract T getQuery(String sql);

	protected abstract void setQueryOptions(Object query, Map<String, Object> options);

	protected abstract void setQueryParameter(Object query, String name, Object value);

}
