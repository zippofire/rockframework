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

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.woodstock.rockframework.domain.persistence.orm.Page;
import net.woodstock.rockframework.domain.query.Constants;
import net.woodstock.rockframework.domain.query.impl.JPAQueryBuilder;
import net.woodstock.rockframework.utils.ConditionUtils;

@SuppressWarnings("rawtypes")
class CommonJPAPagedRepository extends AbstractPagedRepository {

	private EntityManager	entityManager;

	public CommonJPAPagedRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	protected Collection getCollectionFromEntityFilter(final EntityFilter filter, final Page page) {
		JPAQueryBuilder builder = new JPAQueryBuilder(this.entityManager);
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
		Collection c = q.getResultList();
		return c;
	}

	@Override
	protected Collection getCollectionFromJPQLFilter(final JPQLFilter filter, final Page page) {
		Query q = this.entityManager.createQuery(filter.getJpql());
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
		Collection c = q.getResultList();
		return c;
	}
}
