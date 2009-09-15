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
package net.woodstock.rockframework.domain.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessException;
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.service.GenericService;
import net.woodstock.rockframework.domain.service.ServiceException;

public class GenericServiceAdapter extends AbstractService implements GenericService {

	public GenericServiceAdapter() {
		super();
	}

	public void save(Entity<?> e) throws ServiceException, BusinessException, PersistenceException {
		this.getLogger().info("Save: " + e.getClass());
	}

	public <E extends Entity<?>> E get(E e) throws ServiceException, BusinessException, PersistenceException {
		this.getLogger().info("Retrieve: " + e);
		return null;
	}

	public void update(Entity<?> e) throws ServiceException, BusinessException, PersistenceException {
		this.getLogger().info("Update: " + e.getClass());
	}

	public void delete(Entity<?> e) throws ServiceException, BusinessException, PersistenceException {
		this.getLogger().info("Delete: " + e.getClass());
	}

	public <E extends Entity<?>> void delete(Class<E> clazz, Serializable id) throws ServiceException, BusinessException, PersistenceException {
		this.getLogger().info("Delete: " + clazz.getCanonicalName() + " " + id);
	}

	public <E extends Entity<?>> Collection<E> listAll(E e, String order) throws ServiceException, BusinessException, PersistenceException {
		this.getLogger().info("Query: " + e + " Order: " + order);
		return null;
	}

	public <E extends Entity<?>> Collection<E> listByExample(E e, Map<String, Object> options) throws ServiceException, BusinessException, PersistenceException {
		this.getLogger().info("Query: " + e + " Options: " + options);
		return null;
	}

}
