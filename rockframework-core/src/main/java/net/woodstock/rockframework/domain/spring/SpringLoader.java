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

import java.util.Map;

import net.woodstock.rockframework.domain.ConfigurationNotFoundException;
import net.woodstock.rockframework.domain.Loader;
import net.woodstock.rockframework.domain.ObjectNotFoundException;
import net.woodstock.rockframework.domain.TooManyObjectsException;
import net.woodstock.rockframework.utils.StringUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

public final class SpringLoader implements Loader {

	public static final String	APPLICATION_CONFIGURATION	= "applicationContext.xml";

	private static SpringLoader	instance					= new SpringLoader();

	private ApplicationContext	context;

	private SpringLoader() {
		super();
		if (this.isWebApplication()) {
			this.context = this.getWebApplicationContext();
		} else {
			this.context = this.getApplicationContext();
		}
	}

	private boolean isWebApplication() {
		return ContextLoader.getCurrentWebApplicationContext() != null;
	}

	private ApplicationContext getWebApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}

	private ApplicationContext getApplicationContext() {
		ClassLoader classLoader = SpringLoader.class.getClassLoader();
		if (classLoader.getResource(SpringLoader.APPLICATION_CONFIGURATION) == null) {
			throw new ConfigurationNotFoundException("File " + SpringLoader.APPLICATION_CONFIGURATION + " not found");
		}

		return new ClassPathXmlApplicationContext(new String[] { SpringLoader.APPLICATION_CONFIGURATION });
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject(final Class<T> clazz) {
		if (clazz == null) {
			throw new IllegalArgumentException("Class must be not null");
		}
		Map<String, Object> map = this.context.getBeansOfType(clazz);
		if (map.size() == 0) {
			throw new ObjectNotFoundException("Object not found for type " + clazz.getCanonicalName());
		}
		if (map.size() > 1) {
			throw new TooManyObjectsException("To many objects found for type " + clazz.getCanonicalName());
		}

		return (T) map.values().iterator().next();
	}

	public Object getObject(final String name) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("Name must be not empty");
		}
		return this.context.getBean(name);
	}

	public static SpringLoader getInstance() {
		return SpringLoader.instance;
	}
}
