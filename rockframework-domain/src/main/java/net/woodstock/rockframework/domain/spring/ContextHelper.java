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
package net.woodstock.rockframework.domain.spring;

import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.config.CoreLog;
import net.woodstock.rockframework.domain.ConfigurationNotFoundException;
import net.woodstock.rockframework.domain.ObjectNotFoundException;
import net.woodstock.rockframework.domain.TooManyObjectsException;
import net.woodstock.rockframework.util.Assert;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class ContextHelper {

	public static final String		APPLICATION_CONFIGURATION	= "applicationContext.xml";

	private static final String		WEB_CONTEXT_LOADER_CLASS	= "org.springframework.web.context.ContextLoader";

	private static ContextHelper	instance					= new ContextHelper();

	private ApplicationContext		context;

	private ContextHelper() {
		super();
		if (this.isWebApplication()) {
			this.context = this.getWebApplicationContext();
		} else {
			this.context = this.getApplicationContext();
		}
	}

	private synchronized boolean isWebApplication() {
		try {
			Class.forName(ContextHelper.WEB_CONTEXT_LOADER_CLASS);
			return WebContextHelper.isWebApplication();
		} catch (ClassNotFoundException e) {
			CoreLog.getInstance().getLog().info(e.getMessage(), e);
			return false;
		}
	}

	private synchronized ApplicationContext getWebApplicationContext() {
		return WebContextHelper.getWebApplicationContext();
	}

	private synchronized ApplicationContext getApplicationContext() {
		ClassLoader classLoader = ContextHelper.class.getClassLoader();
		if (classLoader.getResource(ContextHelper.APPLICATION_CONFIGURATION) == null) {
			throw new ConfigurationNotFoundException("File " + ContextHelper.APPLICATION_CONFIGURATION + " not found");
		}

		return new ClassPathXmlApplicationContext(new String[] { ContextHelper.APPLICATION_CONFIGURATION });
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject(final Class<T> clazz) {
		Assert.notNull(clazz, "clazz");

		Map<String, Object> map = this.context.getBeansOfType(clazz);
		if (map.size() == 0) {
			throw new ObjectNotFoundException("Object not found for type " + clazz.getCanonicalName());
		}
		if (map.size() > 1) {
			throw new TooManyObjectsException("To many objects found for type " + clazz.getCanonicalName());
		}

		return (T) map.values().iterator().next();
	}

	@SuppressWarnings("unchecked")
	public <T> Collection<T> getObjects(final Class<T> clazz) {
		Assert.notNull(clazz, "clazz");

		Map<String, Object> map = this.context.getBeansOfType(clazz);

		return (Collection<T>) map.values();
	}

	public Object getObject(final String name) {
		Assert.notEmpty(name, "name");
		return this.context.getBean(name);
	}

	public static ContextHelper getInstance() {
		return ContextHelper.instance;
	}
}
