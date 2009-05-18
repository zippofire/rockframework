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
package net.woodstock.rockframework.domain.persistence.query.impl;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.query.BuilderException;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;

public class QueryBuilderAdapter extends AbstractQueryBuilder {

	public Object getQuery(Object manager) throws BuilderException {
		this.getLogger().warn("getQuery " + manager);
		return null;
	}

	public String getQueryString() throws BuilderException {
		this.getLogger().warn("getQueryString");
		return null;
	}

	public QueryBuilder parse(Entity<?> e) throws BuilderException {
		this.getLogger().warn("parse " + e);
		return null;
	}

	public QueryBuilder setOption(String name, Object value) {
		this.getLogger().warn("setOption [" + name + "] = " + value);
		return null;
	}

}
