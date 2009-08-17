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
package net.woodstock.rockframework.domain.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.woodstock.rockframework.sys.SysLogger;

public class ProxyObject implements InvocationHandler {

	private static final long	serialVersionUID	= 5134816048976402977L;

	private Object				object;

	private ProxyObject(Object object) {
		super();
		this.object = object;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
		SysLogger.getLogger().info("Calling method " + method.getName());
		return method.invoke(this.object, args);
	}

	public static Object newInstance(Object obj) {
		ClassLoader classLoader = obj.getClass().getClassLoader();
		Class<?>[] classes = obj.getClass().getInterfaces();
		return Proxy.newProxyInstance(classLoader, classes, new ProxyObject(obj));
	}

}
