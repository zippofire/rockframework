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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.SQLRepository;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

class CommonHibernateSQLRepository implements SQLRepository {

	public static final String	TARGET_ENTITY_PARAMETER	= "__TARGET_ENTITY__";

	private Session				session;

	public CommonHibernateSQLRepository(final Session session) {
		super();
		this.session = session;
	}

	@Override
	public void executeUpdate(final String sql, final Map<String, Object> parameters) {
		SQLQuery query = this.getQuery(sql, parameters);
		query.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection getCollection(final String sql, final Map<String, Object> parameters) {
		SQLQuery query = this.getQuery(sql, parameters);
		List list = query.list();
		return list;
	}

	@Override
	public Object getSingle(final String sql, final Map<String, Object> parameters) {
		SQLQuery query = this.getQuery(sql, parameters);
		Object obj = query.uniqueResult();
		return obj;
	}

	@SuppressWarnings("unchecked")
	private SQLQuery getQuery(final String sql, final Map<String, Object> parameters) {
		Session session = this.session;
		SQLQuery query = session.createSQLQuery(sql);
		if ((parameters != null) && (parameters.size() > 0)) {
			if (parameters.containsKey(CommonHibernateSQLRepository.TARGET_ENTITY_PARAMETER)) {
				Class<Entity> clazz = (Class<Entity>) parameters.get(CommonHibernateSQLRepository.TARGET_ENTITY_PARAMETER);
				query.addEntity(clazz);
			}

			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (!name.equals(CommonHibernateSQLRepository.TARGET_ENTITY_PARAMETER)) {
					query.setParameter(name, value);
				}
			}
		}
		return query;
	}

}
