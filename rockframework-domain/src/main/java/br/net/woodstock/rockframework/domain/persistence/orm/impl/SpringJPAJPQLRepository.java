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

public class SpringJPAJPQLRepository extends SpringJPARepository implements JPQLRepository {

	public SpringJPAJPQLRepository() {
		super();
	}

	@Override
	public void executeUpdate(final QueryMetadata query) {
		new CommonJPAJPQLRepository(this.getEntityManager()).executeUpdate(query);
	}

	@Override
	public <E> Collection<E> getCollection(final QueryMetadata query) {
		return new CommonJPAJPQLRepository(this.getEntityManager()).getCollection(query);
	}

	@Override
	public <E> E getSingle(final QueryMetadata query) {
		return new CommonJPAJPQLRepository(this.getEntityManager()).getSingle(query);
	}

}
