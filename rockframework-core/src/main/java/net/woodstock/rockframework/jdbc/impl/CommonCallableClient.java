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
package net.woodstock.rockframework.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import net.woodstock.rockframework.jdbc.CallableClient;
import net.woodstock.rockframework.jdbc.ParameterList;
import net.woodstock.rockframework.jdbc.Type;
import net.woodstock.rockframework.jdbc.TypeHandler;

public class CommonCallableClient extends CommonClient implements CallableClient {

	public CommonCallableClient(final Connection connection) {
		super(connection);
	}

	public CommonCallableClient(final Connection connection, final TypeHandler typeHandler) {
		super(connection, typeHandler);
	}

	@Override
	public Object callFunction(final Type outType, final String function, final ParameterList args) throws SQLException {
		CallableStatement cs = ClientHelper.createFuncionStatement(outType, function, this.getConnection(), args, this.getTypeHandler());
		cs.execute();
		Object o = ClientHelper.getParameter(1, outType, cs);
		cs.close();
		return o;
	}

	@Override
	public void callProcedure(final String procedure, final ParameterList args) throws SQLException {
		CallableStatement cs = ClientHelper.createProcedureStatement(procedure, this.getConnection(), args, this.getTypeHandler());
		cs.execute();
		cs.close();
	}
}
