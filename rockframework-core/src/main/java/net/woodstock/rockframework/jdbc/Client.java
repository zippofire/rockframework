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

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Client {

	// Default type
	int getType(SqlType type);

	// DML
	boolean execute(String sql, ParameterList args) throws SQLException;

	ResultSet query(String query, ParameterList args) throws SQLException;

	int update(String update, ParameterList args) throws SQLException;

	// Stored
	Object callFunction(SqlType outType, String functionName, ParameterList args) throws SQLException;

	void callProcedure(String procedureName, ParameterList args) throws SQLException;

}
