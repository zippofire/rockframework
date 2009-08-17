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
import java.util.LinkedHashSet;
import java.util.Map;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.DomainException;
import net.woodstock.rockframework.domain.Loader;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringLoader implements Loader {

	public static final String		APPLICATION_CONFIGURATION		= "applicationContext.xml";

	public static final String		WEBAPPLICATION_CONFIGURATION	= "WEB-INF/applicationContext.xml";

	private static final String[]	configFiles						= { SpringLoader.APPLICATION_CONFIGURATION, SpringLoader.WEBAPPLICATION_CONFIGURATION };

	private static SpringLoader		loader;

	private ApplicationContext		context;

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

	private ApplicationContext getApplicationContext() throws DomainException {
		Collection<String> configs = new LinkedHashSet<String>();
		ClassLoader classLoader = SpringLoader.class.getClassLoader();
		for (String config : SpringLoader.configFiles) {
			if (classLoader.getResource(config) != null) {
				configs.add(config);
			}
		}

		if (configs.size() == 0) {
			throw new DomainException(CoreMessage.getInstance().getMessage(Loader.MESSAGE_ERROR_CONFIG_NOT_FOUND));
		}

		return new ClassPathXmlApplicationContext(configs.toArray(new String[configs.size()]));
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject(Class<T> clazz) throws DomainException {
		Map<String, Object> map = this.context.getBeansOfType(clazz);
		if (map.size() == 0) {
			throw new DomainException(CoreMessage.getInstance().getMessage(Loader.MESSAGE_ERROR_NOT_FOUND, clazz.getCanonicalName()));
		}
		if (map.size() > 1) {
			throw new DomainException(CoreMessage.getInstance().getMessage(Loader.MESSAGE_ERROR_MANY_FOUND, clazz.getCanonicalName()));
		}

		return (T) map.values().iterator().next();
	}

	public Object getObject(String name) throws DomainException {
		return this.context.getBean(name);
	}

	public static SpringLoader getInstance() {
		if (SpringLoader.loader == null) {
			synchronized (SpringLoader.class) {
				if (SpringLoader.loader == null) {
					SpringLoader.loader = new SpringLoader();
				}
			}
		}
		return SpringLoader.loader;
	}
}
