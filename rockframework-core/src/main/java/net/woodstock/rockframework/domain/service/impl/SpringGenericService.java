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

import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class SpringGenericService extends AbstractGenericService {

	public SpringGenericService() {
		super();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(final Entity<?> entity) {
		super.save(entity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public <E extends Entity<?>> E get(final E entity) {
		return super.get(entity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public <E extends Entity<?>> Collection<E> listAll(final E entity, final Map<String, Object> options) {
		return super.listAll(entity, options);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public <E extends Entity<?>> Collection<E> listByExample(final E entity, final Map<String, Object> options) {
		return super.listByExample(entity, options);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(final Entity<?> entity) {
		super.update(entity);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(final Entity<?> entity) {
		super.delete(entity);
	}

}
