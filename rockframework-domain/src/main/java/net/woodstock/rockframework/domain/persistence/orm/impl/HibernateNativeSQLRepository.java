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

import net.woodstock.rockframework.domain.persistence.orm.NativeSQLRepository;
import net.woodstock.rockframework.domain.persistence.orm.QueryMetadata;

public class HibernateNativeSQLRepository extends AbstractHibernateRepository implements NativeSQLRepository {

	public HibernateNativeSQLRepository() {
		super();
	}

	@Override
	public void executeUpdate(QueryMetadata query) {
		new CommonHibernateNativeSQLRepository(this.getSession()).executeUpdate(query);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Collection getCollection(QueryMetadata query) {
		return new CommonHibernateNativeSQLRepository(this.getSession()).getCollection(query);
	}

	@Override
	public Object getSingle(QueryMetadata query) {
		return new CommonHibernateNativeSQLRepository(this.getSession()).getSingle(query);
	}

}
