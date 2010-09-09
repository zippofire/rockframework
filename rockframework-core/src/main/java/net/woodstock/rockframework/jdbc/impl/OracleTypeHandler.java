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
package net.woodstock.rockframework.jdbc.impl;

import net.woodstock.rockframework.jdbc.Type;
import net.woodstock.rockframework.jdbc.TypeHandler;

public class OracleTypeHandler implements TypeHandler {

	private static final int	CURSOR	= -10;

	@Override
	public int getType(final Type type) {
		switch (type) {
			case RESULTSET:
				return OracleTypeHandler.CURSOR;
			default:
				return type.getType();
		}
	}

}