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

	private static final String	UNDEFINED_ID		= "undefined";

	private static final String	BEGIN_ID			= "[";

	private static final String	END_ID				= "]";

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		try {
			if (!(obj instanceof Entity)) {
				return false;
			}
			return EntityUtils.equals(this, (Entity<?>) obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int hashCode() {
		try {
			return EntityUtils.hashCode(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		ID id = this.getId();
		builder.append(this.getClass().getSimpleName());
		builder.append(AbstractEntity.BEGIN_ID);
		if (id != null) {
			builder.append(id);
		} else {
			builder.append(AbstractEntity.UNDEFINED_ID);
		}
		builder.append(AbstractEntity.END_ID);
		return builder.toString();
	}

}
