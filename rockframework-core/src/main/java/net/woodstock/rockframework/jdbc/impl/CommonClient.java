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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.woodstock.rockframework.jdbc.Client;
import net.woodstock.rockframework.jdbc.ParameterList;
import net.woodstock.rockframework.jdbc.TypeHandler;
import net.woodstock.rockframework.util.Assert;

public class CommonClient implements Client {

	private Connection	connection;

	private TypeHandler	typeHandler;

	public CommonClient(final Connection connection) {
		this(connection, new CommonTypeHandler());
	}

	public CommonClient(final Connection connection, final TypeHandler typeHandler) {
		super();
		Assert.notNull(connection, "connection");
		Assert.notNull(typeHandler, "typeHandler");
		this.connection = connection;
		this.typeHandler = typeHandler;
	}

	protected Connection getConnection() {
		return this.connection;
	}

	protected TypeHandler getTypeHandler() {
		return this.typeHandler;
	}

	@Override
	public boolean execute(final String sql, final ParameterList args) throws SQLException {
		PreparedStatement ps = ClientHelper.createStatement(sql, this.connection, args, this.typeHandler);
		boolean b = ps.execute();
		ps.close();
		return b;
	}

	@Override
	public ResultSet query(final String query, final ParameterList args) throws SQLException {
		PreparedStatement ps = ClientHelper.createStatement(query, this.connection, args, this.typeHandler);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	@Override
	public int update(final String update, final ParameterList args) throws SQLException {
		PreparedStatement ps = ClientHelper.createStatement(update, this.connection, args, this.typeHandler);
		int i = ps.executeUpdate();
		ps.close();
		return i;
	}

}
