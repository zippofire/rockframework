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
package br.net.woodstock.rockframework.domain.persistence.orm.query.impl;


import org.hibernate.Query;
import org.hibernate.Session;

import br.net.woodstock.rockframework.domain.config.DomainLog;
import br.net.woodstock.rockframework.domain.persistence.orm.CacheMode;
import br.net.woodstock.rockframework.domain.persistence.orm.Constants;

public class HibernateQueryBuilder extends JPQLQueryBuilder<Query> {

	private Session	session;

	public HibernateQueryBuilder(final Session session) {
		super();
		this.session = session;
	}

	@Override
	protected Query getQuery(final String sql) {
		Query query = this.session.createQuery(sql);
		return query;
	}

	@Override
	protected void setQueryParameter(final Query query, final String name, final Object value) {
		query.setParameter(name, value);
	}

	@Override
	protected void setQueryOption(final Query query, final String name, final Object value) {
		if (name.equals(Constants.OPTION_CACHE_MODE)) {
			if (value instanceof CacheMode) {
				CacheMode cacheMode = (CacheMode) value;
				if (cacheMode == CacheMode.ENABLED) {
					query.setCacheable(true);
					query.setCacheMode(org.hibernate.CacheMode.NORMAL);
				}
			} else if (value != null) {
				DomainLog.getInstance().getLog().warn("Illegal option type[" + name + "] => " + value.getClass().getCanonicalName() + ", must be " + CacheMode.class.getCanonicalName());
			} else {
				DomainLog.getInstance().getLog().warn("Illegal option value[" + name + "] => null");
			}
		} else if (name.equals(Constants.OPTION_FIRST_RESULT)) {
			if (value instanceof Integer) {
				Integer firstResult = (Integer) value;
				query.setFirstResult(firstResult.intValue());
			} else if (value != null) {
				DomainLog.getInstance().getLog().warn("Illegal option type[" + name + "] => " + value.getClass().getCanonicalName() + ", must be " + Integer.class.getCanonicalName());
			} else {
				DomainLog.getInstance().getLog().warn("Illegal option value[" + name + "] => null");
			}
		} else if (name.equals(Constants.OPTION_MAX_RESULT)) {
			if (value instanceof Integer) {
				Integer maxResult = (Integer) value;
				query.setMaxResults(maxResult.intValue());
			} else if (value != null) {
				DomainLog.getInstance().getLog().warn("Illegal option type[" + name + "] => " + value.getClass().getCanonicalName() + ", must be " + Integer.class.getCanonicalName());
			} else {
				DomainLog.getInstance().getLog().warn("Illegal option value[" + name + "] => null");
			}
		} else if (name.equals(Constants.OPTION_READ_ONLY)) {
			if (value instanceof Boolean) {
				Boolean readOnly = (Boolean) value;
				query.setReadOnly(readOnly.booleanValue());
			} else if (value != null) {
				DomainLog.getInstance().getLog().warn("Illegal option type[" + name + "] => " + value.getClass().getCanonicalName() + ", must be " + Boolean.class.getCanonicalName());
			} else {
				DomainLog.getInstance().getLog().warn("Illegal option value[" + name + "] => null");
			}
		} else {
			DomainLog.getInstance().getLog().warn("Illegal option[" + name + "] => " + value);
		}
	}

}
