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
import java.util.Map.Entry;

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

	@Override
	public QueryBuilder<T> setOption(final String name, final Object value) {
		if (this.build) {
			CoreLog.getInstance().getLog().warn("Query alread build");
		} else {
			this.options.put(name, value);
		}
		return this;
	}

	@Override
	public T getQuery() {
		if (this.context == null) {
			this.build();
		}
		String sql = this.context.getQueryString();

		T query = this.getQuery(sql);

		for (Entry<String, Object> entry : this.options.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			CoreLog.getInstance().getLog().debug("Setting option[" + name + "] => " + value);
			this.setQueryOption(query, name, value);
		}

		for (QueryContextParameter parameter : this.context.getParametersRecursive()) {
			String name = parameter.getAlias();
			Object value = parameter.getValue();
			CoreLog.getInstance().getLog().debug("Setting parameter[" + name + "] => " + value);
			this.setQueryParameter(query, name, value);
		}

		return query;
	}

	public String getQueryString() {
		if (this.context == null) {
			this.build();
		}
		return this.getContext().getQueryString();
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

	protected abstract void setQueryOption(T query, String name, Object value);

	protected abstract void setQueryParameter(T query, String name, Object value);

}
