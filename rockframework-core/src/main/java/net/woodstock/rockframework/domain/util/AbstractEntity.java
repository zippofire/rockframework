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
package net.woodstock.rockframework.domain.util;

import java.io.Serializable;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.utils.EntityUtils;

public abstract class AbstractEntity<ID extends Serializable> implements Entity<ID> {

	private static final long	serialVersionUID	= 7167657368775342614L;

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (!(obj instanceof Entity)) {
			return false;
		}
		return EntityUtils.equals(this, (Entity<?>) obj);
	}

	@Override
	public int hashCode() {
		return EntityUtils.hashCode(this);
	}

	@Override
	public String toString() {
		return EntityUtils.toString(this);
	}

}
