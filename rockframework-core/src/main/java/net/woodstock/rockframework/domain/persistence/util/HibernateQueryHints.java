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
package net.woodstock.rockframework.domain.persistence.util;

public abstract class HibernateQueryHints {

	public static final String	CACHEABLE		= "org.hibernate.cacheable";

	public static final String	CACHE_MODE		= "org.hibernate.cacheMode";

	public static final String	CACHE_REGION	= "org.hibernate.cacheRegion";

	public static final String	FETCH_SIZE		= "org.hibernate.fetchSize";

	public static final String	FLUSH_MODE		= "org.hibernate.flushMode";

	public static final String	READ_ONLY		= "org.hibernate.readOnly";

	public static final String	TIMEOUT			= "org.hibernate.timeout";

	private HibernateQueryHints() {
		//
	}

}
