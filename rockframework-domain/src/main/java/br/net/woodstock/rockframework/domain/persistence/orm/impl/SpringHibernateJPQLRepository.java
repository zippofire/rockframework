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
package br.net.woodstock.rockframework.domain.persistence.orm.impl;

import java.util.Collection;

import br.net.woodstock.rockframework.domain.persistence.orm.JPQLRepository;
import br.net.woodstock.rockframework.domain.persistence.orm.QueryMetadata;


public class SpringHibernateJPQLRepository extends SpringHibernateRepository implements JPQLRepository {

	public SpringHibernateJPQLRepository() {
		super();
	}

	@Override
	public void executeUpdate(final QueryMetadata query) {
		new CommonHibernateJPQLRepository(this.getSession()).executeUpdate(query);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Collection getCollection(final QueryMetadata query) {
		return new CommonHibernateJPQLRepository(this.getSession()).getCollection(query);
	}

	@Override
	public Object getSingle(final QueryMetadata query) {
		return new CommonHibernateJPQLRepository(this.getSession()).getSingle(query);
	}

}
