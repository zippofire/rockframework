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
package br.net.woodstock.rockframework.domain.test.test1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import br.net.woodstock.rockframework.utils.LogUtils;


public final class ProxyObject implements InvocationHandler {

	private static final long	serialVersionUID	= 5134816048976402977L;

	private Object				object;

	private ProxyObject(final Object object) {
		super();
		this.object = object;
	}

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws InvocationTargetException, IllegalAccessException {
		LogUtils.getSharedLog().info("Calling method " + method.getName());
		return method.invoke(this.object, args);
	}

	public static Object newInstance(final Object obj) {
		ClassLoader classLoader = obj.getClass().getClassLoader();
		Class<?>[] classes = obj.getClass().getInterfaces();
		return Proxy.newProxyInstance(classLoader, classes, new ProxyObject(obj));
	}

}
