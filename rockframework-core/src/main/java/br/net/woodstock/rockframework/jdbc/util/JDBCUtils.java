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
import java.sql.SQLException;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.IOUtils;

public abstract class JDBCUtils {

	private JDBCUtils() {
		//
	}

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

}
