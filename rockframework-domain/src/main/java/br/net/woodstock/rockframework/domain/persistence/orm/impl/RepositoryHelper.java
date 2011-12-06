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
import java.util.Map;

import br.net.woodstock.rockframework.domain.persistence.orm.Constants;
import br.net.woodstock.rockframework.utils.ConditionUtils;

abstract class RepositoryHelper {

	private RepositoryHelper() {
		//
	}

	public static String getListAllSql(final Class<?> clazz, final Map<String, Object> options, final boolean canonicalName) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT o FROM ");
		if (canonicalName) {
			builder.append(clazz.getCanonicalName());
		} else {
			builder.append(clazz.getSimpleName());
		}
		builder.append(" AS o");
		if (ConditionUtils.isNotEmpty(options)) {
			if (options.containsKey(Constants.OPTION_ORDER_BY)) {
				Object o = options.get(Constants.OPTION_ORDER_BY);
				if (o != null) {
					String order = o.toString();
					if (ConditionUtils.isNotEmpty(order)) {
						builder.append(" ORDER BY ");
						builder.append(order);
					}
				}
			}
		}

		return builder.toString();
	}

	public static String getDeleteSql(final Class<?> clazz, final boolean canonicalName) {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM ");
		if (canonicalName) {
			builder.append(clazz.getCanonicalName());
		} else {
			builder.append(clazz.getSimpleName());
		}
		builder.append(" WHERE id = :id");
		return builder.toString();
	}

	public static boolean isCollection(final Object value) {
		if (value instanceof Collection) {
			return true;
		}
		return false;
	}

	public static boolean isArray(final Object value) {
		if ((value != null) && (value.getClass().isArray())) {
			return true;
		}
		return false;
	}

}
