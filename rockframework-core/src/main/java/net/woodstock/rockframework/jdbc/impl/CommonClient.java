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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import net.woodstock.rockframework.jdbc.Client;
import net.woodstock.rockframework.jdbc.Parameter;
import net.woodstock.rockframework.jdbc.ParameterList;
import net.woodstock.rockframework.jdbc.Type;
import net.woodstock.rockframework.utils.IOUtils;

public class CommonClient implements Client {

	private Connection	connection;

	public CommonClient(final Connection connection) {
		super();
		this.connection = connection;
	}

	protected Connection getConnection() {
		return this.connection;
	}

	public int getType(final Type type) {
		return type.type();
	}

	public boolean execute(final String sql, final ParameterList args) throws SQLException {
		PreparedStatement ps = this.createStatement(sql, this.connection, args);
		boolean b = ps.execute();
		ps.close();
		return b;
	}

	public ResultSet query(final String query, final ParameterList args) throws SQLException {
		PreparedStatement ps = this.createStatement(query, this.connection, args);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public int update(final String update, final ParameterList args) throws SQLException {
		PreparedStatement ps = this.createStatement(update, this.connection, args);
		int i = ps.executeUpdate();
		ps.close();
		return i;
	}

	// Utils
	protected PreparedStatement createStatement(final String sql, final Connection c, final ParameterList args) throws SQLException {
		PreparedStatement ps = null;
		ps = c.prepareStatement(sql);
		if (args != null) {
			this.setParameters(1, ps, args);
		}
		return ps;
	}

	protected void setParameter(final int index, final PreparedStatement cs, final Parameter param) throws SQLException {
		Object value = param.getValue();
		Type type = param.getType();
		if (value == null) {
			cs.setNull(index, this.getType(type));
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
					} else if (value instanceof Reader) {
						value = IOUtils.toInputStream((Reader) value);
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
					} else if (value instanceof Reader) {
						value = IOUtils.toInputStream((Reader) value);
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

	protected void setParameters(final int index, final PreparedStatement ps, final ParameterList args) throws SQLException {
		int i = index;
		if (args != null) {
			for (Parameter arg : args) {
				this.setParameter(i++, ps, arg);
			}
		}
	}
}
