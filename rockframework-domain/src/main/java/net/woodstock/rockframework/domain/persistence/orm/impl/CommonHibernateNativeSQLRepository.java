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

import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.orm.NativeSQLRepository;
import net.woodstock.rockframework.domain.query.Constants;
import net.woodstock.rockframework.utils.ConditionUtils;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

class CommonHibernateNativeSQLRepository extends AbstractHibernateQueryableRepository implements NativeSQLRepository {

	private Session	session;

	public CommonHibernateNativeSQLRepository(final Session session) {
		super();
		this.session = session;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected SQLQuery getQuery(final String sql, final Map<String, Object> parameters) {
		Session session = this.session;
		SQLQuery query = session.createSQLQuery(sql);
		if (ConditionUtils.isNotEmpty(parameters)) {
			if (parameters.containsKey(Constants.OPTION_TARGET_ENTITY)) {
				Class<Entity> clazz = (Class<Entity>) parameters.get(Constants.OPTION_TARGET_ENTITY);
				query.addEntity(clazz);
			}

			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (this.isValidParameter(name)) {
					query.setParameter(name, value);
				}
			}
		}
		return query;
	}

}
