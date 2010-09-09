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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import net.woodstock.rockframework.jdbc.Parameter;
import net.woodstock.rockframework.jdbc.ParameterList;
import net.woodstock.rockframework.jdbc.Type;
import net.woodstock.rockframework.jdbc.TypeHandler;

abstract class ClientHelper {

	private ClientHelper() {
		//
	}

	public static PreparedStatement createStatement(final String sql, final Connection c, final ParameterList args, final TypeHandler typeHandler) throws SQLException {
		PreparedStatement ps = null;
		ps = c.prepareStatement(sql);
		if (args != null) {
			ClientHelper.setParameters(1, ps, args, typeHandler);
		}
		return ps;
	}

	public static CallableStatement createFuncionStatement(final Type outType, final String name, final Connection c, final ParameterList args, final TypeHandler typeHandler) throws SQLException {
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
		cs.registerOutParameter(1, typeHandler.getType(outType));

		if (args != null) {
			ClientHelper.setParameters(2, cs, args, typeHandler);
		}
		return cs;
	}

	public static CallableStatement createProcedureStatement(final String name, final Connection c, final ParameterList args, final TypeHandler typeHandler) throws SQLException {
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
			ClientHelper.setParameters(1, cs, args, typeHandler);
		}
		return cs;
	}

	public static Object getParameter(final int index, final Type outType, final CallableStatement cs) throws SQLException {
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

	public static void setParameter(final int index, final PreparedStatement cs, final Parameter param, final TypeHandler typeHandler) throws SQLException {
		Object value = param.getValue();
		Type type = param.getType();
		if (value == null) {
			cs.setNull(index, typeHandler.getType(type));
			return;
		}
		switch (type) {
			case ARRAY:
				cs.setArray(index, (Array) value);
				break;
			case BIGINT:
				if (value instanceof String) {
					value = Long.valueOf((String) value);
				}
				cs.setLong(index, ((Long) value).longValue());
				break;
			case BLOB:
				try {
					if (value instanceof File) {
						value = new FileInputStream((File) value);
					}
					cs.setBinaryStream(index, (InputStream) value, ((InputStream) value).available());
				} catch (IOException e) {
					throw new SQLException(e.getMessage());
				}
				break;
			case BOOLEAN:
				if (value instanceof String) {
					value = Boolean.valueOf((String) value);
				}
				cs.setBoolean(index, ((Boolean) value).booleanValue());
				break;
			case CHAR:
				if (value instanceof Character) {
					value = new String(((Character) value).toString());
				}
				cs.setString(index, (String) value);
				break;
			case CLOB:
				try {
					if (value instanceof File) {
						value = new FileInputStream((File) value);
					}
					int size = ((InputStream) value).available();
					value = new InputStreamReader((InputStream) value);
					cs.setCharacterStream(index, (Reader) value, size);
				} catch (IOException e) {
					throw new SQLException(e.getMessage());
				}
				break;
			case DATE:
				if (value instanceof Long) {
					value = new Date(((Long) value).longValue());
				} else if ((value instanceof java.util.Date) && (!(value instanceof Date))) {
					value = new Date(((java.util.Date) value).getTime());
				}
				cs.setDate(index, (Date) value);
				break;
			case DECIMAL:
				if (value instanceof String) {
					value = Integer.valueOf((String) value);
				}
				cs.setInt(index, ((Integer) value).intValue());
				break;
			case DOUBLE:
				if (value instanceof String) {
					value = Double.valueOf((String) value);
				}
				cs.setDouble(index, ((Double) value).doubleValue());
				break;
			case FLOAT:
				if (value instanceof String) {
					value = Float.valueOf((String) value);
				}
				cs.setFloat(index, ((Float) value).floatValue());
				break;
			case INTEGER:
				if (value instanceof String) {
					value = Integer.valueOf((String) value);
				}
				cs.setInt(index, ((Integer) value).intValue());
				break;
			case NUMERIC:
				if (value instanceof String) {
					value = new BigDecimal(Double.parseDouble((String) value));
				} else if (value instanceof Integer) {
					value = new BigDecimal(((Integer) value).intValue());
				}
				cs.setBigDecimal(index, (BigDecimal) value);
				break;
			case OBJECT:
				cs.setObject(index, value);
				break;
			case OTHER:
				cs.setObject(index, value);
				break;
			case REAL:
				if (value instanceof String) {
					value = Float.valueOf((String) value);
				}
				cs.setFloat(index, ((Float) value).floatValue());
				break;
			case REF:
				cs.setRef(index, (Ref) value);
				break;
			case STRUCT:
				cs.setObject(index, value);
				break;
			case SMALLINT:
				if (value instanceof String) {
					value = Short.valueOf((String) value);
				}
				cs.setShort(index, ((Short) value).shortValue());
				break;
			case TIME:
				if (value instanceof Long) {
					value = new Date(((Long) value).longValue());
				}
				cs.setTime(index, (Time) value);
				break;
			case TIMESTAMP:
				if (value instanceof Long) {
					value = new Timestamp(((Long) value).longValue());
				} else if ((value instanceof java.util.Date) && (!(value instanceof Timestamp))) {
					value = new Timestamp(((java.util.Date) value).getTime());
				}
				cs.setTimestamp(index, (Timestamp) value);
				break;
			case TINYINT:
				if (value instanceof String) {
					value = Byte.valueOf((String) value);
				}
				cs.setByte(index, ((Byte) value).byteValue());
				break;
			case VARCHAR:
				if (!(value instanceof String)) {
					value = value.toString();
				}
				cs.setString(index, (String) value);
				break;
			default:
				throw new SQLException("Type not supported " + type);
		}

	}

	public static void setParameters(final int index, final PreparedStatement ps, final ParameterList args, final TypeHandler typeHandler) throws SQLException {
		int i = index;
		if (args != null) {
			for (Parameter arg : args) {
				ClientHelper.setParameter(i++, ps, arg, typeHandler);
			}
		}
	}
}
