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
package br.net.woodstock.rockframework.jdbc.impl;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.sql.DataSource;


import org.apache.commons.dbcp.BasicDataSource;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.jdbc.DataSourceFactory;
import br.net.woodstock.rockframework.util.Assert;

public class BasicDataSourceFactory implements DataSourceFactory {

	public static final String	PROPERTY_ACCESS_TO_UNDERLYING_CONNECTION_ALLOWED	= "accessToUnderlyingConnectionAllowed";

	public static final String	PROPERTY_DEFAULT_AUTO_COMMIT						= "defaultAutoCommit";

	public static final String	PROPERTY_DEFAULT_CATALOG							= "defaultCatalog";

	public static final String	PROPERTY_DEFAULT_READ_ONLY							= "defaultReadOnly";

	public static final String	PROPERTY_DEFAULT_TRANSACTION_ISOLATION				= "defaultTransactionIsolation";

	public static final String	PROPERTY_DRIVER_CLASS_NAME							= "driverClassName";

	public static final String	PROPERTY_INITIAL_SIZE								= "initialSize";

	public static final String	PROPERTY_LOGIN_TIMEOUT								= "loginTimeout";

	public static final String	PROPERTY_MAX_ACTIVE									= "maxActive";

	public static final String	PROPERTY_MAX_IDLE									= "maxIdle";

	public static final String	PROPERTY_MAX_OPEN_PREPARED_STATEMENTS				= "maxOpenPreparedStatements";

	public static final String	PROPERTY_MAX_WAIT									= "maxWait";

	public static final String	PROPERTY_MIN_EVICTABLE_IDLE_TIME_MILLIS				= "minEvictableIdleTimeMillis";

	public static final String	PROPERTY_MIN_IDLE									= "minIdle";

	public static final String	PROPERTY_NUM_TESTS_PER_EVICTION_RUN					= "numTestsPerEvictionRun";

	public static final String	PROPERTY_PASSWORD									= "password";

	public static final String	PROPERTY_POOL_PREPARED_STATEMENTS					= "poolPreparedStatements";

	public static final String	PROPERTY_TEST_ON_RETURN								= "testOnReturn";

	public static final String	PROPERTY_TEST_WHILE_IDLE							= "testWhileIdle";

	public static final String	PROPERTY_TIME_BETWEEN_EVICTION_RUNS_MILLIS			= "timeBetweenEvictionRunsMillis";

	public static final String	PROPERTY_URL										= "url";

	public static final String	PROPERTY_USERNAME									= "username";

	public static final String	PROPERTY_VALIDATION_QUERY							= "validationQuery";

	@Override
	@SuppressWarnings("rawtypes")
	public Object getObjectInstance(final Object object, final Name name, final Context context, final Hashtable table) throws Exception {
		if ((object == null) || !(object instanceof Reference)) {
			CoreLog.getInstance().getLog().warn("Object is null or not is a Reference");
			return null;
		}

		Reference ref = (Reference) object;

		if (!(ref.getClassName().equals(DataSource.class.getName()))) {
			CoreLog.getInstance().getLog().warn("Object ins't a Data Source '" + ref.getClassName() + "'");
			return null;
		}

		Properties properties = new Properties();
		Enumeration<RefAddr> p = ref.getAll();
		while (p.hasMoreElements()) {
			RefAddr r = p.nextElement();
			String s = r.getContent().toString();
			properties.put(r.getType(), s);
		}

		return this.getDataSource(properties);
	}

	@Override
	public DataSource getDataSource(final Properties properties) throws SQLException {
		Assert.notNull(properties, "properties");

		BasicDataSource dataSource = new BasicDataSource();

		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_ACCESS_TO_UNDERLYING_CONNECTION_ALLOWED)) {
			dataSource.setAccessToUnderlyingConnectionAllowed(Boolean.parseBoolean(properties.getProperty(BasicDataSourceFactory.PROPERTY_ACCESS_TO_UNDERLYING_CONNECTION_ALLOWED)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_DEFAULT_AUTO_COMMIT)) {
			dataSource.setDefaultAutoCommit(Boolean.parseBoolean(properties.getProperty(BasicDataSourceFactory.PROPERTY_DEFAULT_AUTO_COMMIT)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_DEFAULT_CATALOG)) {
			dataSource.setDefaultCatalog(properties.getProperty(BasicDataSourceFactory.PROPERTY_DEFAULT_CATALOG));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_DEFAULT_READ_ONLY)) {
			dataSource.setDefaultReadOnly(Boolean.parseBoolean(properties.getProperty(BasicDataSourceFactory.PROPERTY_DEFAULT_READ_ONLY)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_DEFAULT_TRANSACTION_ISOLATION)) {
			dataSource.setDefaultTransactionIsolation(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_DEFAULT_TRANSACTION_ISOLATION)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_DRIVER_CLASS_NAME)) {
			dataSource.setDriverClassName(properties.getProperty(BasicDataSourceFactory.PROPERTY_DRIVER_CLASS_NAME));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_INITIAL_SIZE)) {
			dataSource.setInitialSize(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_INITIAL_SIZE)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_LOGIN_TIMEOUT)) {
			dataSource.setLoginTimeout(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_LOGIN_TIMEOUT)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_MAX_ACTIVE)) {
			dataSource.setMaxActive(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_MAX_ACTIVE)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_MAX_IDLE)) {
			dataSource.setMaxIdle(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_MAX_IDLE)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_MAX_OPEN_PREPARED_STATEMENTS)) {
			dataSource.setMaxOpenPreparedStatements(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_MAX_OPEN_PREPARED_STATEMENTS)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_MAX_WAIT)) {
			dataSource.setMaxWait(Long.parseLong(properties.getProperty(BasicDataSourceFactory.PROPERTY_MAX_WAIT)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_MIN_EVICTABLE_IDLE_TIME_MILLIS)) {
			dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(properties.getProperty(BasicDataSourceFactory.PROPERTY_MIN_EVICTABLE_IDLE_TIME_MILLIS)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_MIN_IDLE)) {
			dataSource.setMinIdle(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_MIN_IDLE)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_NUM_TESTS_PER_EVICTION_RUN)) {
			dataSource.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty(BasicDataSourceFactory.PROPERTY_NUM_TESTS_PER_EVICTION_RUN)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_PASSWORD)) {
			dataSource.setPassword(properties.getProperty(BasicDataSourceFactory.PROPERTY_PASSWORD));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_POOL_PREPARED_STATEMENTS)) {
			dataSource.setPoolPreparedStatements(Boolean.parseBoolean(properties.getProperty(BasicDataSourceFactory.PROPERTY_POOL_PREPARED_STATEMENTS)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_TEST_ON_RETURN)) {
			dataSource.setTestOnReturn(Boolean.parseBoolean(properties.getProperty(BasicDataSourceFactory.PROPERTY_TEST_ON_RETURN)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_TEST_WHILE_IDLE)) {
			dataSource.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty(BasicDataSourceFactory.PROPERTY_TEST_WHILE_IDLE)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_TIME_BETWEEN_EVICTION_RUNS_MILLIS)) {
			dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties.getProperty(BasicDataSourceFactory.PROPERTY_TIME_BETWEEN_EVICTION_RUNS_MILLIS)));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_URL)) {
			dataSource.setUrl(properties.getProperty(BasicDataSourceFactory.PROPERTY_URL));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_USERNAME)) {
			dataSource.setUsername(properties.getProperty(BasicDataSourceFactory.PROPERTY_USERNAME));
		}
		if (properties.containsKey(BasicDataSourceFactory.PROPERTY_VALIDATION_QUERY)) {
			dataSource.setValidationQuery(properties.getProperty(BasicDataSourceFactory.PROPERTY_VALIDATION_QUERY));
		}

		return dataSource;
	}

}
