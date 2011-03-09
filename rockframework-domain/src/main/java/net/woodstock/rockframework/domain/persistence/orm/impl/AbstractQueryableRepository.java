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

import net.woodstock.rockframework.domain.persistence.orm.QueryableRepository;
import net.woodstock.rockframework.domain.query.Constants;

abstract class AbstractQueryableRepository implements QueryableRepository {

	protected boolean isValidParameter(final String name) {
		if (name.equals(Constants.OPTION_CACHE_MODE)) {
			return false;
		}
		if (name.equals(Constants.OPTION_FIRST_RESULT)) {
			return false;
		}
		if (name.equals(Constants.OPTION_MAX_RESULT)) {
			return false;
		}
		if (name.equals(Constants.OPTION_READ_ONLY)) {
			return false;
		}
		if (name.equals(Constants.OPTION_TARGET_ENTITY)) {
			return false;
		}
		return true;
	}

}
