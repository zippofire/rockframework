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
package br.net.woodstock.rockframework.persistence.orm.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import br.net.woodstock.rockframework.persistence.orm.PersistenceException;
import br.net.woodstock.rockframework.util.PropertiesReader;
import br.net.woodstock.rockframework.utils.ClassLoaderUtils;

public final class JDBCWithJNDIPersistenceHelper implements PersistenceHelper<Connection> {

	public static final String						PROPERTIES_FILE		= "datasource.properties";

	public static final String						PROPERTY_DATASOURCE	= "datasource";

	private static JDBCWithJNDIPersistenceHelper	instance			= new JDBCWithJNDIPersistenceHelper();

	private static ThreadLocal<Connection>			connection			= new ThreadLocal<Connection>();

	private DataSource								dataSource;

	private JDBCWithJNDIPersistenceHelper() {
		super();
		try {
			InputStream inputStream = ClassLoaderUtils.getResourceAsStream(JDBCWithJNDIPersistenceHelper.PROPERTIES_FILE);
			if (inputStream == null) {
				throw new PersistenceException("File " + JDBCWithJNDIPersistenceHelper.PROPERTIES_FILE + " not found in classpath");
			}

			Properties properties = PropertiesReader.getPlainTextInstance().getProperties(inputStream);
			String dataSource = properties.getProperty(JDBCWithJNDIPersistenceHelper.PROPERTY_DATASOURCE);
			Context context = new InitialContext();
			this.dataSource = (DataSource) context.lookup(dataSource);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void close() {
		try {
			Connection c = JDBCWithJNDIPersistenceHelper.connection.get();
			if (c != null) {
				if (!c.isClosed()) {
					c.commit();
					c.close();
				}
				JDBCWithJNDIPersistenceHelper.connection.set(null);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Connection get() {
		try {
			Connection c = JDBCWithJNDIPersistenceHelper.connection.get();
			if (c == null) {
				c = this.dataSource.getConnection();
				JDBCWithJNDIPersistenceHelper.connection.set(c);
			}
			return c;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	public static JDBCWithJNDIPersistenceHelper getInstance() {
		return JDBCWithJNDIPersistenceHelper.instance;
	}
}
