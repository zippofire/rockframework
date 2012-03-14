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
package br.net.woodstock.rockframework.persistence.jdbc.impl;

import java.io.Serializable;
import java.sql.Connection;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.persistence.jdbc.JDBCRepository;

public abstract class AbstractJDBCRepository<E extends Entity<ID>, ID extends Serializable> implements JDBCRepository<E, ID> {

	private static final long	serialVersionUID	= 7899320254008914731L;

	protected abstract Connection getConnection();

}
