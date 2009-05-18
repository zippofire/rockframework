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
package net.woodstock.rockframework.domain.persistence.impl;

import net.woodstock.rockframework.domain.persistence.Repository;
import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.StringUtils;

import org.apache.commons.logging.Log;

abstract class AbstractRepository implements Repository {

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	protected static String getListAllSql(Class<?> clazz, String order) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT o FROM ");
		builder.append(clazz.getCanonicalName());
		builder.append(" AS o");
		if (!StringUtils.isEmpty(order)) {
			builder.append(" ORDER BY ");
			builder.append(order);
		}
		return builder.toString();
	}

}
