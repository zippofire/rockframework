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

import javax.sql.DataSource;

import br.net.woodstock.rockframework.jdbc.DataSourceFactory;
import br.net.woodstock.rockframework.jdbc.impl.BasicDataSourceFactory;
import br.net.woodstock.rockframework.persistence.orm.PersistenceException;
import br.net.woodstock.rockframework.util.PropertiesReader;
import br.net.woodstock.rockframework.utils.ClassLoaderUtils;

public final class JDBCPersistenceHelper implements PersistenceHelper<Connection> {

	public static final String				PROPERTIES_FILE	= "jdbc.properties";

	private static JDBCPersistenceHelper	instance		= new JDBCPersistenceHelper();

	private static ThreadLocal<Connection>	connection		= new ThreadLocal<Connection>();

	private DataSource						dataSource;

	private JDBCPersistenceHelper() {
		super();
		try {
			InputStream inputStream = ClassLoaderUtils.getResourceAsStream(JDBCPersistenceHelper.PROPERTIES_FILE);
			if (inputStream == null) {
				throw new PersistenceException("File " + JDBCPersistenceHelper.PROPERTIES_FILE + " not found in classpath");
			}

			Properties properties = PropertiesReader.getPlainTextInstance().getProperties(inputStream);
			DataSourceFactory factory = new BasicDataSourceFactory();
			this.dataSource = factory.getDataSource(properties);
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void close() {
		try {
			Connection c = JDBCPersistenceHelper.connection.get();
			if (c != null) {
				if (!c.isClosed()) {
					c.commit();
					c.close();
				}
				JDBCPersistenceHelper.connection.set(null);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Connection get() {
		try {
			Connection c = JDBCPersistenceHelper.connection.get();
			if (c == null) {
				c = this.dataSource.getConnection();
				JDBCPersistenceHelper.connection.set(c);
			}
			return c;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	public static JDBCPersistenceHelper getInstance() {
		return JDBCPersistenceHelper.instance;
	}
}
