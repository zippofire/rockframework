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

import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;

public interface GenericRepository extends Repository {

	void save(Entity<?> e);

	void update(Entity<?> e);

	void delete(Entity<?> e);

	<E extends Entity<?>> E get(E entity);

	<E extends Entity<?>> Collection<E> listAll(E e, String order);

	<E extends Entity<?>> Collection<E> listByExample(E e, Map<String, Object> options);

}
