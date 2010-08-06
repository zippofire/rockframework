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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import net.woodstock.rockframework.jdbc.CallableClient;
import net.woodstock.rockframework.jdbc.ParameterList;
import net.woodstock.rockframework.jdbc.Type;

public class CommonCallableClient extends CommonClient implements CallableClient {

	public CommonCallableClient(final Connection connection) {
		super(connection);
	}

	@Override
	public Object callFunction(final Type outType, final String functionName, final ParameterList args) throws SQLException {
		CallableStatement cs = this.createFuncionStatement(this.getType(outType), functionName, this.getConnection(), args);
		cs.execute();
		Object o = this.getParameter(1, outType, cs);
		cs.close();
		return o;
	}

	@Override
	public void callProcedure(final String procedureName, final ParameterList args) throws SQLException {
		CallableStatement cs = this.createProcedureStatement(procedureName, this.getConnection(), args);
		cs.execute();
		cs.close();
	}

	// Utils
	private CallableStatement createFuncionStatement(final int outType, final String name, final Connection c, final ParameterList args) throws SQLException {
		CallableStatement cs = null;
		StringBuilder sql = new StringBuilder("{ ? = call " + name + "(");
		if (args != null) {
			for (int cont = 0; cont < args.size(); cont++) {
				sql.append("?");
				if (cont + 1 < args.size()) {
					sql.append(",");
				}
			}
		}
		sql.append(") }");
		cs = c.prepareCall(sql.toString());
		cs.registerOutParameter(1, outType);

		if (args != null) {
			this.setParameters(2, cs, args);
		}
		return cs;
	}

	private CallableStatement createProcedureStatement(final String name, final Connection c, final ParameterList args) throws SQLException {
		CallableStatement cs = null;
		StringBuilder sql = new StringBuilder("{ call " + name + "(");
		if (args != null) {
			for (int cont = 0; cont < args.size(); cont++) {
				sql.append("?");
				if (cont + 1 < args.size()) {
					sql.append(",");
				}
			}
		}
		sql.append(") }");
		cs = c.prepareCall(sql.toString());
		if (args != null) {
			this.setParameters(1, cs, args);
		}
		return cs;
	}

	private Object getParameter(final int index, final Type outType, final CallableStatement cs) throws SQLException {
		Object o = null;
		switch (outType) {
			case ARRAY:
				o = cs.getArray(index);
				break;
			case BIGINT:
				o = cs.getBigDecimal(index);
				break;
			case BLOB:
				o = cs.getBlob(index);
				break;
			case BOOLEAN:
				o = Boolean.valueOf(cs.getBoolean(index));
				break;
			case CHAR:
				o = cs.getString(index);
				break;
			case CLOB:
				o = cs.getClob(index);
				break;
			case DATE:
				o = cs.getDate(index);
				break;
			case DECIMAL:
				o = Integer.valueOf(cs.getInt(index));
				break;
			case DOUBLE:
				o = Double.valueOf(cs.getDouble(index));
				break;
			case FLOAT:
				o = Float.valueOf(cs.getFloat(index));
				break;
			case INTEGER:
				o = Integer.valueOf(cs.getInt(index));
				break;
			case NUMERIC:
				o = cs.getBigDecimal(index);
				break;
			case OBJECT:
				o = cs.getObject(index);
				break;
			case OTHER:
				o = cs.getObject(index);
				break;
			case REAL:
				o = Float.valueOf(cs.getFloat(index));
				break;
			case REF:
				o = cs.getRef(index);
				break;
			case RESULTSET:
				o = cs.getObject(index);
				break;
			case SMALLINT:
				o = Short.valueOf(cs.getShort(index));
				break;
			case STRUCT:
				o = cs.getObject(index);
				break;
			case TIME:
				o = cs.getTime(index);
				break;
			case TIMESTAMP:
				o = cs.getTimestamp(index);
				break;
			case TINYINT:
				o = Short.valueOf(cs.getShort(index));
				break;
			case VARCHAR:
				o = cs.getString(index);
				break;
			default:
				throw new SQLException("Type not supported " + outType);
		}
		return o;
	}
}
