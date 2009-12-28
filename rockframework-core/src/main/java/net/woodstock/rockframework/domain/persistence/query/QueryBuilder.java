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
package net.woodstock.rockframework.domain.persistence.query;

import net.woodstock.rockframework.domain.Entity;

public interface QueryBuilder<T> {

	// String OPTION_COLLECTION_MODE = "COLLECTION_MODE";

	String	OPTION_FIRST_RESULT			= "FIRST_RESULT";

	String	OPTION_IGNORE_CASE			= "IGNORE_CASE";

	String	OPTION_LIKE_MODE			= "LIKE_MODE";

	String	OPTION_MAX_RESULT			= "MAX_RESULT";

	String	OPTION_ORDER_BY				= "ORDER_BY";

	// Disable
	String	OPTION_DISABLE_CHILD		= "DISABLE_CHILDS";

	String	OPTION_DISABLE_COLLECTION	= "DISABLE_COLLECTION";

	// Builder
	void build();
	
	// Set
	void setEntity(Entity<?> entity);
	
	void setOption(String name, Object value);

	// Get
	String getQueryString();

	T getQuery();

}
