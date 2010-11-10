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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
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
				cs.setLong(index, ClientHelper.toLong(value));
				break;
			case BLOB:
				InputStream inputStream = ClientHelper.toInputStream(value);
				if (inputStream == null) {
					throw new IllegalArgumentException("A Blob parameter must be setted with an InputStream, File or Clob value");
				}
				cs.setBinaryStream(index, inputStream);
				break;
			case BOOLEAN:
				cs.setBoolean(index, ClientHelper.toBoolean(value));
				break;
			case CHAR:
				cs.setString(index, ClientHelper.toString(value));
				break;
			case CLOB:
				Reader reader = ClientHelper.toReader(value);
				if (reader == null) {
					throw new IllegalArgumentException("A CLOB parameter must be setted with an InputStream, Reader, File or Clob value");
				}
				cs.setCharacterStream(index, (Reader) value);
				break;
			case DATE:
				cs.setDate(index, ClientHelper.toDate(value));
				break;
			case DECIMAL:
				cs.setFloat(index, ClientHelper.toFloat(value));
				break;
			case DOUBLE:
				cs.setDouble(index, ClientHelper.toDouble(value));
				break;
			case FLOAT:
				cs.setFloat(index, ClientHelper.toFloat(value));
				break;
			case INTEGER:
				cs.setInt(index, ClientHelper.toInt(value));
				break;
			case NUMERIC:
				cs.setBigDecimal(index, ClientHelper.toBigDecimal(value));
				break;
			case OBJECT:
				cs.setObject(index, value);
				break;
			case OTHER:
				cs.setObject(index, value);
				break;
			case REAL:
				cs.setFloat(index, ClientHelper.toFloat(value));
				break;
			case REF:
				cs.setRef(index, (Ref) value);
				break;
			case STRUCT:
				cs.setObject(index, value);
				break;
			case SMALLINT:
				cs.setShort(index, ClientHelper.toShort(value));
				break;
			case TIME:
				cs.setTime(index, ClientHelper.toTime(value));
				break;
			case TIMESTAMP:
				cs.setTimestamp(index, ClientHelper.toTimestamp(value));
				break;
			case TINYINT:
				cs.setByte(index, ClientHelper.toByte(value));
				break;
			case VARCHAR:
				cs.setString(index, ClientHelper.toString(value));
				break;
			default:
				throw new SQLException("Type not supported " + type);
		}

	}

	private static BigDecimal toBigDecimal(final Object value) {
		if (value instanceof BigDecimal) {
			BigDecimal bd = (BigDecimal) value;
			return bd;
		}
		if (value instanceof Number) {
			Number n = (Number) value;
			return new BigDecimal(n.doubleValue());
		}
		return new BigDecimal(value.toString());
	}

	private static boolean toBoolean(final Object value) {
		if (value instanceof Boolean) {
			Boolean b = (Boolean) value;
			return b.booleanValue();
		}
		return Boolean.parseBoolean(value.toString());
	}

	private static byte toByte(final Object value) {
		if (value instanceof Number) {
			Number n = (Number) value;
			return n.byteValue();
		}
		return Byte.parseByte(value.toString());
	}

	private static Date toDate(final Object value) {
		if (value instanceof Date) {
			return (Date) value;
		}
		if (value instanceof Long) {
			Long l = (Long) value;
			return new Date(l.longValue());
		}
		long l = Long.parseLong(value.toString());
		return new Date(l);
	}

	private static double toDouble(final Object value) {
		if (value instanceof Number) {
			Number n = (Number) value;
			return n.doubleValue();
		}
		return Double.parseDouble(value.toString());
	}

	private static float toFloat(final Object value) {
		if (value instanceof Number) {
			Number n = (Number) value;
			return n.floatValue();
		}
		return Float.parseFloat(value.toString());
	}

	private static InputStream toInputStream(final Object value) throws SQLException {
		if (value instanceof InputStream) {
			return (InputStream) value;
		}
		if (value instanceof File) {
			try {
				return new FileInputStream((File) value);
			} catch (FileNotFoundException e) {
				throw new SQLException(e);
			}
		}
		if (value instanceof Blob) {
			return ((Blob) value).getBinaryStream();
		}
		return null;
	}

	private static int toInt(final Object value) {
		if (value instanceof Number) {
			Number n = (Number) value;
			return n.intValue();
		}
		return Integer.parseInt(value.toString());
	}

	private static long toLong(final Object value) {
		if (value instanceof Number) {
			Number n = (Number) value;
			return n.longValue();
		}
		return Long.parseLong(value.toString());
	}

	private static Reader toReader(final Object value) throws SQLException {
		if (value instanceof Reader) {
			return (Reader) value;
		}
		if (value instanceof InputStream) {
			return new InputStreamReader((InputStream) value);
		}
		if (value instanceof File) {
			try {
				return new FileReader((File) value);
			} catch (FileNotFoundException e) {
				throw new SQLException(e);
			}
		}
		if (value instanceof Clob) {
			return ((Clob) value).getCharacterStream();
		}

		return null;
	}

	private static short toShort(final Object value) {
		if (value instanceof Number) {
			Number n = (Number) value;
			return n.shortValue();
		}
		return Short.parseShort(value.toString());
	}

	private static String toString(final Object value) {
		if (value instanceof String) {
			return (String) value;
		}
		return value.toString();
	}

	private static Time toTime(final Object value) {
		if (value instanceof Time) {
			return (Time) value;
		}
		if (value instanceof Date) {
			Date d = (Date) value;
			return new Time(d.getTime());
		}
		if (value instanceof Long) {
			Long l = (Long) value;
			return new Time(l.longValue());
		}
		long l = Long.parseLong(value.toString());
		return new Time(l);
	}

	private static Timestamp toTimestamp(final Object value) {
		if (value instanceof Timestamp) {
			return (Timestamp) value;
		}
		if (value instanceof Date) {
			Date d = (Date) value;
			return new Timestamp(d.getTime());
		}
		if (value instanceof Long) {
			Long l = (Long) value;
			return new Timestamp(l.longValue());
		}
		long l = Long.parseLong(value.toString());
		return new Timestamp(l);
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
