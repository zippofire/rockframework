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

import net.woodstock.rockframework.domain.persistence.orm.Filter;
import net.woodstock.rockframework.domain.persistence.orm.Page;
import net.woodstock.rockframework.domain.persistence.orm.PagedRepository;
import net.woodstock.rockframework.util.Assert;

@SuppressWarnings("rawtypes")
public abstract class AbstractPagedRepository implements PagedRepository {

	@Override
	public Collection getCollection(final Filter filter, final Page page) {
		Assert.notNull(filter, "filter");
		Assert.notNull(page, "page");

		if (filter instanceof EntityFilter) {
			return this.getCollectionFromEntityFilter((EntityFilter) filter, page);
		}
		if (filter instanceof JPQLFilter) {
			return this.getCollectionFromJPQLFilter((JPQLFilter) filter, page);
		}
		throw new IllegalArgumentException("Could not handle filters of type " + filter.getClass());
	}

	protected abstract Collection getCollectionFromEntityFilter(EntityFilter filter, Page page);

	protected abstract Collection getCollectionFromJPQLFilter(JPQLFilter filter, Page page);

}