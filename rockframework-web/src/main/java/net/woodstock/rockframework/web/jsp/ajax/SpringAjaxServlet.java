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
package net.woodstock.rockframework.web.jsp.ajax;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class SpringAjaxServlet extends BaseAjaxServlet {

	private static final long	serialVersionUID	= 5068996528149640640L;

	public static final String	SPRING_MODE			= "SPRING_MODE";

	public static final String	MODE_BY_TYPE		= "byType";

	public static final String	MODE_BY_NAME		= "byName";

	private ApplicationContext	context;

	private boolean				byType;

	@Override
	protected Object loadClass(String name) throws ClassNotFoundException {
		if (this.context == null) {
			this.context = ContextLoader.getCurrentWebApplicationContext();
			String springMode = this.getInitParameter(SPRING_MODE);
			if (springMode != null) {
				if (springMode.equals(MODE_BY_TYPE)) {
					this.byType = true;
				} else if (springMode.equals(MODE_BY_NAME)) {
					this.byType = false;
				} else {
					this.byType = true;
				}
			}
		}

		if (this.byType) {
			this.getByType(name);
		}

		return this.getByName(name);
	}

	@SuppressWarnings("unchecked")
	private Object getByType(String name) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(name);
		Map map = this.context.getBeansOfType(clazz);

		if (map.size() == 0) {
			throw new IllegalArgumentException(clazz.getCanonicalName());
		}

		if (map.size() > 1) {
			throw new IllegalArgumentException(clazz.getCanonicalName());
		}

		Object obj = map.values().iterator().next();
		return obj;
	}

	private Object getByName(String name) {
		Object obj = this.context.getBean(name);
		return obj;
	}

}
