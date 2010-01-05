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

import java.util.Map;
import java.util.Map.Entry;

public class QueryBuilderAdapter extends EJBQLQueryBuilder<Object> {

	@Override
	protected Object getQuery(final String sql) {
		this.getLog().warn("getQueryLocal(" + sql + ")");
		return null;
	}

	@Override
	protected void setQueryOptions(final Object query, final Map<String, Object> options) {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		builder.append("setQueryOptions(");
		for (Entry<String, Object> entry : options.entrySet()) {
			if (!first) {
				builder.append(", ");
			}
			builder.append(entry.getKey() + " =>" + entry.getValue());
			if (first) {
				first = false;
			}
		}
		builder.append(")");
		this.getLog().warn(builder.toString());
	}

	@Override
	protected void setQueryParameter(final Object query, final String name, final Object value) {
		this.getLog().warn("setQueryParameter[" + name + "] => " + value);
	}

}
