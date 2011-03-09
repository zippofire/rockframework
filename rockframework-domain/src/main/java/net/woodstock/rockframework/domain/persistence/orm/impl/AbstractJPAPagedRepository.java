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

import javax.persistence.EntityManager;

import net.woodstock.rockframework.domain.persistence.orm.Page;

@SuppressWarnings("rawtypes")
abstract class AbstractJPAPagedRepository extends AbstractPagedRepository {

	public AbstractJPAPagedRepository() {
		super();
	}

	@Override
	protected Collection getCollectionFromEntityFilter(final EntityFilter filter, final Page page) {
		return new CommonJPAPagedRepository(this.getEntityManager()).getCollection(filter, page);
	}

	@Override
	protected Collection getCollectionFromJPQLFilter(final JPQLFilter filter, final Page page) {
		return new CommonJPAPagedRepository(this.getEntityManager()).getCollection(filter, page);
	}

	protected abstract EntityManager getEntityManager();
}
