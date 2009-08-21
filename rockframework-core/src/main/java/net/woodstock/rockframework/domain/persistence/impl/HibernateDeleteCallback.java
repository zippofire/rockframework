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

import java.sql.SQLException;

import net.woodstock.rockframework.domain.Entity;

import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class HibernateDeleteCallback implements HibernateCallback {

	private Entity<?>	entity;

	public HibernateDeleteCallback(Entity<?> entity) {
		super();
		this.entity = entity;
	}

	public Object doInHibernate(Session session) throws HibernateException, SQLException {
		try {
			session.delete(this.entity);
		} catch (PropertyValueException pve) {
			session.refresh(this.entity);
			session.delete(this.entity);
		} catch (NonUniqueObjectException nuoe) {
			Entity<?> e = (Entity<?>) session.get(this.entity.getClass(), this.entity.getId());
			session.delete(e);
		}
		return null;
	}

}
