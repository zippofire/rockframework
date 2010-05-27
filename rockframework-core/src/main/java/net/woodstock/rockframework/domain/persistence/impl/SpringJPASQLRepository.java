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
import java.util.Map;

import net.woodstock.rockframework.domain.persistence.EJBQLRepository;

public class SpringJPASQLRepository extends SpringJPARepository implements EJBQLRepository {

	public SpringJPASQLRepository() {
		super();
	}

	@Override
	public void executeUpdate(final String sql, final Map<String, Object> parameters) {
		new CommonJPASQLRepository(this.getJpaTemplate().getEntityManager()).executeUpdate(sql, parameters);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection getCollection(final String sql, final Map<String, Object> parameters) {
		return new CommonJPASQLRepository(this.getJpaTemplate().getEntityManager()).getCollection(sql, parameters);
	}

	@Override
	public Object getSingle(final String sql, final Map<String, Object> parameters) {
		return new CommonJPASQLRepository(this.getJpaTemplate().getEntityManager()).getSingle(sql, parameters);
	}

}
