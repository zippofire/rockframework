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

import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.persistence.orm.Constants;
import br.net.woodstock.rockframework.domain.persistence.orm.NativeSQLRepository;
import br.net.woodstock.rockframework.utils.ConditionUtils;

class CommonJPANativeSQLRepository extends AbstractJPAQueryableRepository implements NativeSQLRepository {

	private EntityManager	entityManager;

	public CommonJPANativeSQLRepository(final EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Query getQuery(final br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata query) {
		EntityManager entityManager = this.entityManager;
		Query q = null;

		Map<String, Object> parameters = query.getParameters();
		Map<String, Object> options = query.getOptions();

		if (ConditionUtils.isNotEmpty(options)) {
			if (options.containsKey(Constants.OPTION_TARGET_ENTITY)) {
				Class<Entity> clazz = (Class<Entity>) options.get(Constants.OPTION_TARGET_ENTITY);
				q = entityManager.createNativeQuery(query.getQuery(), clazz);
			} else {
				q = entityManager.createNativeQuery(query.getQuery());
			}

			if (options.containsKey(Constants.OPTION_FIRST_RESULT)) {
				Integer firstResult = (Integer) options.get(Constants.OPTION_FIRST_RESULT);
				q.setFirstResult(firstResult.intValue());
			}
			if (options.containsKey(Constants.OPTION_MAX_RESULT)) {
				Integer maxResult = (Integer) options.get(Constants.OPTION_MAX_RESULT);
				q.setMaxResults(maxResult.intValue());
			}
		} else {
			q = entityManager.createNativeQuery(query.getQuery());
		}

		if (ConditionUtils.isNotEmpty(parameters)) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (this.isValidParameter(name)) {
					q.setParameter(name, value);
				}
			}
		}

		return q;
	}

}
