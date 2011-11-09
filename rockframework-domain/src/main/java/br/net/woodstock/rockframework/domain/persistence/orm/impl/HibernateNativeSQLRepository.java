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

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.persistence.orm.Constants;
import br.net.woodstock.rockframework.domain.persistence.orm.NativeSQLRepository;
import br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public class HibernateNativeSQLRepository extends AbstractHibernateQueryableRepository implements NativeSQLRepository {

	private static final long	serialVersionUID	= -3219784477734752368L;

	private Session				session;

	public HibernateNativeSQLRepository(final Session session) {
		super();
		this.session = session;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected SQLQuery getQuery(final QueryMetadata query) {
		Session session = this.session;
		SQLQuery sqlQuery = session.createSQLQuery(query.getQuery());

		Map<String, Object> parameters = query.getParameters();
		Map<String, Object> options = query.getOptions();

		if (ConditionUtils.isNotEmpty(options)) {
			if (options.containsKey(Constants.OPTION_TARGET_ENTITY)) {
				Class<Entity> clazz = (Class<Entity>) options.get(Constants.OPTION_TARGET_ENTITY);
				sqlQuery.addEntity(clazz);
			}
		}

		if (ConditionUtils.isNotEmpty(parameters)) {
			for (Entry<String, Object> entry : parameters.entrySet()) {
				String name = entry.getKey();
				Object value = entry.getValue();
				if (RepositoryHelper.isValidParameter(name)) {
					sqlQuery.setParameter(name, value);
				}
			}
		}
		return sqlQuery;
	}

}
