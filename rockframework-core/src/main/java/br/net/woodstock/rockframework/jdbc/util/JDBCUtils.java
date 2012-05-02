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
package br.net.woodstock.rockframework.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.IOUtils;

public abstract class JDBCUtils {

	private JDBCUtils() {
		//
	}

	// Blob
	public static Blob createBlob(final Connection connection, final Reader reader) throws SQLException, IOException {
		Assert.notNull(connection, "connection");
		Assert.notNull(reader, "reader");

		byte[] data = IOUtils.toByteArray(reader);

		return JDBCUtils.createBlob(connection, data);
	}

	public static Blob createBlob(final Connection connection, final InputStream inputStream) throws SQLException, IOException {
		Assert.notNull(connection, "connection");
		Assert.notNull(inputStream, "inputStream");

		byte[] data = IOUtils.toByteArray(inputStream);

		return JDBCUtils.createBlob(connection, data);
	}

	public static Blob createBlob(final Connection connection, final byte[] data) throws SQLException, IOException {
		Assert.notNull(connection, "connection");
		Assert.notEmpty(data, "data");

		Blob blob = connection.createBlob();
		OutputStream outputStream = blob.setBinaryStream(0);
		outputStream.write(data);

		return blob;
	}

	// Clob
	public static Clob createClob(final Connection connection, final String data) throws SQLException, IOException {
		Assert.notNull(connection, "connection");
		Assert.notEmpty(data, "data");

		Clob clob = connection.createClob();
		Writer writer = clob.setCharacterStream(0);
		writer.write(data);

		return clob;
	}

	public static Clob createClob(final Connection connection, final Reader reader) throws SQLException, IOException {
		Assert.notNull(connection, "connection");
		Assert.notNull(reader, "reader");

		byte[] data = IOUtils.toByteArray(reader);

		return JDBCUtils.createClob(connection, data);
	}

	public static Clob createClob(final Connection connection, final InputStream inputStream) throws SQLException, IOException {
		Assert.notNull(connection, "connection");
		Assert.notNull(inputStream, "inputStream");

		byte[] data = IOUtils.toByteArray(inputStream);

		return JDBCUtils.createClob(connection, data);
	}

	public static Clob createClob(final Connection connection, final byte[] data) throws SQLException, IOException {
		Assert.notNull(connection, "connection");
		Assert.notEmpty(data, "data");

		Clob clob = connection.createClob();
		OutputStream outputStream = clob.setAsciiStream(0);
		outputStream.write(data);

		return clob;
	}

	// Get
	public static Boolean getBoolean(final ResultSet rs, final String name) throws SQLException {
		Boolean value = Boolean.valueOf(rs.getBoolean(name));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Boolean getBoolean(final ResultSet rs, final int index) throws SQLException {
		Boolean value = Boolean.valueOf(rs.getBoolean(index));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Byte getByte(final ResultSet rs, final String name) throws SQLException {
		Byte value = Byte.valueOf(rs.getByte(name));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Byte getByte(final ResultSet rs, final int index) throws SQLException {
		Byte value = Byte.valueOf(rs.getByte(index));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Double getDouble(final ResultSet rs, final String name) throws SQLException {
		Double value = Double.valueOf(rs.getDouble(name));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Double getDouble(final ResultSet rs, final int index) throws SQLException {
		Double value = Double.valueOf(rs.getDouble(index));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Float getFloat(final ResultSet rs, final String name) throws SQLException {
		Float value = Float.valueOf(rs.getFloat(name));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Float getFloat(final ResultSet rs, final int index) throws SQLException {
		Float value = Float.valueOf(rs.getFloat(index));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Integer getInteger(final ResultSet rs, final String name) throws SQLException {
		Integer value = Integer.valueOf(rs.getInt(name));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Integer getInteger(final ResultSet rs, final int index) throws SQLException {
		Integer value = Integer.valueOf(rs.getInt(index));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Long getLong(final ResultSet rs, final String name) throws SQLException {
		Long value = Long.valueOf(rs.getLong(name));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Long getLong(final ResultSet rs, final int index) throws SQLException {
		Long value = Long.valueOf(rs.getLong(index));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Short getShort(final ResultSet rs, final String name) throws SQLException {
		Short value = Short.valueOf(rs.getShort(name));
		return JDBCUtils.getNullValue(rs, value);
	}

	public static Short getShort(final ResultSet rs, final int index) throws SQLException {
		Short value = Short.valueOf(rs.getShort(index));
		return JDBCUtils.getNullValue(rs, value);
	}

	private static <T> T getNullValue(final ResultSet rs, final T value) throws SQLException {
		if (rs.wasNull()) {
			return null;
		}
		return value;
	}

}
