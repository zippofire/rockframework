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
package net.woodstock.rockframework.domain.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessException;

public interface GenericRepository extends Repository {

	void save(Entity<?> e) throws PersistenceException;

	void update(Entity<?> e) throws PersistenceException;

	void delete(Entity<?> e) throws PersistenceException;

	<ID extends Serializable, E extends Entity<ID>> E get(Class<E> clazz, ID id) throws PersistenceException, BusinessException, PersistenceException;

	<E extends Entity<?>> Collection<E> listAll(Class<E> clazz, String order) throws PersistenceException;

	<E extends Entity<?>> Collection<E> listByExample(E e, Map<String, Object> options) throws PersistenceException;

}
