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

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.orm.Filter;

@SuppressWarnings("rawtypes")
public class EntityFilter implements Filter {

	private static final long	serialVersionUID	= 5628711582746588279L;

	private Entity				entity;

	private String				order;

	public EntityFilter(final Entity entity, final String order) {
		super();
		this.entity = entity;
		this.order = order;
	}

	public Entity getEntity() {
		return this.entity;
	}

	public String getOrder() {
		return this.order;
	}

}
