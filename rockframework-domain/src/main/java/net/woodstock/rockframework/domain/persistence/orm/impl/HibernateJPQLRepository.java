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
import java.util.Map;

import net.woodstock.rockframework.domain.persistence.orm.JPQLRepository;

public class HibernateJPQLRepository extends AbstractHibernateRepository implements JPQLRepository {

	public HibernateJPQLRepository() {
		super();
	}

	@Override
	public void executeUpdate(final String sql, final Map<String, Object> parameters) {
		new CommonHibernateJPQLRepository(this.getSession()).executeUpdate(sql, parameters);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Collection getCollection(final String sql, final Map<String, Object> parameters) {
		return new CommonHibernateJPQLRepository(this.getSession()).getCollection(sql, parameters);
	}

	@Override
	public Object getSingle(final String sql, final Map<String, Object> parameters) {
		return new CommonHibernateJPQLRepository(this.getSession()).getSingle(sql, parameters);
	}

}