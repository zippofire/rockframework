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

import br.net.woodstock.rockframework.persistence.orm.QueryMetadata;
import br.net.woodstock.rockframework.persistence.orm.QueryResult;
import br.net.woodstock.rockframework.persistence.orm.QueryableRepository;

public class SpringHibernateQueryableRepository extends SpringHibernateRepository implements QueryableRepository {

	private static final long	serialVersionUID	= -4416940021542485066L;

	public SpringHibernateQueryableRepository() {
		super();
	}

	@Override
	public void executeUpdate(final QueryMetadata query) {
		new HibernateQueryableRepository(this.getSession()).executeUpdate(query);
	}

	@Override
	public QueryResult getCollection(final QueryMetadata query) {
		return new HibernateQueryableRepository(this.getSession()).getCollection(query);
	}

	@Override
	public <E> E getSingle(final QueryMetadata query) {
		return new HibernateQueryableRepository(this.getSession()).getSingle(query);
	}

}
