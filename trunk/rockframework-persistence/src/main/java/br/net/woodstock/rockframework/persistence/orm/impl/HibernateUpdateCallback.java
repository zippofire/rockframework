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
package br.net.woodstock.rockframework.persistence.orm.impl;

import java.sql.SQLException;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import br.net.woodstock.rockframework.domain.Entity;

public class HibernateUpdateCallback implements HibernateCallback<Object> {

	private Entity<?>	entity;

	public HibernateUpdateCallback(final Entity<?> entity) {
		super();
		this.entity = entity;
	}

	@Override
	public Object doInHibernate(final Session session) throws SQLException {
		new HibernateGenericRepository(session).update(this.entity);
		return null;
	}

}
