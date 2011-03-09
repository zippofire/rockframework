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
package net.woodstock.rockframework.domain.persistence.orm;

import java.io.Serializable;
import java.util.Collection;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.Repository;

public interface EntityRepository<E extends Entity<ID>, ID extends Serializable> extends Repository {

	void save(E e);

	void update(E e);

	void delete(E e);

	E get(ID id);

	Collection<E> getCollection(Filter filter, Page page);

}
