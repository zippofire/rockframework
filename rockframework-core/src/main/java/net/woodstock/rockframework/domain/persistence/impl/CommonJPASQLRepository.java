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
package net.woodstock.rockframework.domain.persistence.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.SQLRepository;
import net.woodstock.rockframework.domain.persistence.util.Constants;

class CommonJPASQLRepository extends AbstractJPAQueryableRepository implements SQLRepository {

	private EntityManager	entityManager;

	public CommonJPASQLRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected Query getQuery(final String sql, final Map<String, Object> parameters) {
		EntityManager entityManager = this.entityManager;
		Query query = null;

		if ((parameters != null) && (parameters.size() > 0)) {
			if (parameters.containsKey(Constants.OPTION_TARGET_ENTITY)) {
				Class<Entity> clazz = (Class<Entity>) parameters.get(Constants.OPTION_TARGET_ENTITY);
				query = entityManager.createNativeQuery(sql, clazz);
			} else {
				query = entityManager.createNativeQuery(sql);
			}

			if (parameters.containsKey(Constants.OPTION_FIRST_RESULT)) {
				Integer firstResult = (Integer) parameters.get(Constants.OPTION_FIRST_RESULT);
				query.setFirstResult(firstResult.intValue());
			}
			if (parameters.containsKey(Constants.OPTION_MAX_RESULT)) {
				Integer maxResult = (Integer) parameters.get(Constants.OPTION_MAX_RESULT);
				query.setMaxResults(maxResult.intValue());
			}

			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (this.isValidParameter(name)) {
					query.setParameter(name, value);
				}
			}
		} else {
			query = entityManager.createNativeQuery(sql);
		}

		return query;
	}

}
