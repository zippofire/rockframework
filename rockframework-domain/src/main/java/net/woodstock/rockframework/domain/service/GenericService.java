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
package net.woodstock.rockframework.domain.service;

import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;

public interface GenericService extends Service {

	void save(Entity<?> entity);

	void update(Entity<?> entity);

	void delete(Entity<?> entity);

	// Retrieve
	<E extends Entity<?>> E get(E entity);

	// List
	<E extends Entity<?>> Collection<E> listAll(E entity, Map<String, Object> options);

	<E extends Entity<?>> Collection<E> listByExample(E entity, Map<String, Object> options);

}
