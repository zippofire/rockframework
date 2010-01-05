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
package net.woodstock.rockframework.test.domain.test;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.logging.SysLogger;

public final class ProxyEntity<ID extends Serializable> implements Entity<ID>, InvocationHandler {

	private static final long	serialVersionUID	= 5134816048976402977L;

	private Entity<ID>			entity;

	private ProxyEntity(final Entity<ID> entity) {
		super();
		this.entity = entity;
	}

	public ID getId() {
		return this.entity.getId();
	}

	public void setId(final ID id) {
		this.entity.setId(id);
	}

	public Object invoke(final Object proxy, final Method method, final Object[] args) throws InvocationTargetException, IllegalAccessException {
		SysLogger.getLog().info("Calling method " + method.getName());
		return method.invoke(this.entity, args);
	}

	@SuppressWarnings("unchecked")
	public static <E extends Entity<? extends Serializable>> E newInstance(final E entity) {
		Object obj = Proxy.newProxyInstance(entity.getClass().getClassLoader(), entity.getClass().getInterfaces(), new ProxyEntity(entity));
		return (E) obj;
	}

	@SuppressWarnings("unchecked")
	public static Object newInstance2(final Object obj) {
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new ProxyEntity((Entity) obj));
	}

}
