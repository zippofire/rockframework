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
package br.net.woodstock.rockframework.domain.security;

import java.io.Serializable;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.utils.ConditionUtils;

public abstract class EncodedEntity<T extends Serializable> implements Entity<T> {

	private static final long	serialVersionUID	= 4544693200518455843L;

	public String getCid() {
		String id = this.getIdAsString();
		if (ConditionUtils.isNotEmpty(id)) {
			return EncodedEntityHelper.encodeId(id);
		}
		return null;
	}

	public void setCid(final String cid) {
		if (ConditionUtils.isNotEmpty(cid)) {
			String id = EncodedEntityHelper.decodeId(cid);
			this.setIdAsString(id);
		}
	}

	protected abstract String getIdAsString();

	protected abstract void setIdAsString(String id);

}
