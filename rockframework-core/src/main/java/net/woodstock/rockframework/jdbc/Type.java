/*
 * This file is part of rockapi.
 * 
 * rockapi is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockapi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package net.woodstock.rockframework.jdbc;

import java.sql.Types;

public enum Type {

	ARRAY(Types.ARRAY),
	BIGINT(Types.BIGINT),
	BLOB(Types.BLOB),
	BOOLEAN(Types.BOOLEAN),
	CHAR(Types.CHAR),
	CLOB(Types.CLOB),
	DATE(Types.DATE),
	DECIMAL(Types.DECIMAL),
	DOUBLE(Types.DOUBLE),
	FLOAT(Types.FLOAT),
	INTEGER(Types.INTEGER),
	NUMERIC(Types.NUMERIC),
	OBJECT(Types.OTHER),
	OTHER(Types.OTHER),
	REAL(Types.REAL),
	REF(Types.REF),
	RESULTSET(Types.OTHER),
	SMALLINT(Types.SMALLINT),
	STRUCT(Types.STRUCT),
	TIME(Types.TIME),
	TIMESTAMP(Types.TIMESTAMP),
	TINYINT(Types.TINYINT),
	VARCHAR(Types.VARCHAR);

	private int	type;

	private Type(final int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

}
