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
package net.woodstock.rockframework.domain.persistence.orm.impl;

import java.util.Collection;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.persistence.orm.Page;
import net.woodstock.rockframework.domain.query.Constants;
import net.woodstock.rockframework.domain.query.impl.HibernateQueryBuilder;
import net.woodstock.rockframework.utils.ConditionUtils;

import org.hibernate.Query;
import org.hibernate.Session;

@SuppressWarnings("rawtypes")
class CommonHibernatePagedRepository extends AbstractPagedRepository {

	private Session	session;

	public CommonHibernatePagedRepository(final Session session) {
		super();
		this.session = session;
	}

	@Override
	protected Collection getCollectionFromEntityFilter(final EntityFilter filter, final Page page) {
		HibernateQueryBuilder builder = new HibernateQueryBuilder(this.session);
		builder.setEntity(filter.getEntity());
		if (page.getFirstResult() > 0) {
			builder.setOption(Constants.OPTION_FIRST_RESULT, Integer.valueOf(page.getFirstResult()));
		}
		if (page.getMaxResults() > 0) {
			builder.setOption(Constants.OPTION_MAX_RESULT, Integer.valueOf(page.getMaxResults()));
		}
		if (ConditionUtils.isNotEmpty(filter.getOrder())) {
			builder.setOption(Constants.OPTION_ORDER_BY, filter.getOrder());
		}
		Query q = builder.getQuery();
		Collection c = q.list();
		return c;
	}

	@Override
	protected Collection getCollectionFromJPQLFilter(final JPQLFilter filter, final Page page) {
		Query q = this.session.createQuery(filter.getJpql());
		if (page.getFirstResult() > 0) {
			q.setFirstResult(page.getFirstResult());
		}
		if (page.getMaxResults() > 0) {
			q.setMaxResults(page.getMaxResults());
		}
		if (ConditionUtils.isNotEmpty(filter.getParams())) {
			for (Entry<String, Object> entry : filter.getParams().entrySet()) {
				q.setParameter(entry.getKey(), entry.getValue());
			}
		}
		Collection c = q.list();
		return c;
	}
}
