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
package net.woodstock.rockframework.web.struts2.history;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.struts2.ConditionalInterceptor;
import net.woodstock.rockframework.web.struts2.utils.Struts2Utils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class FavoriteInterceptor extends ConditionalInterceptor<String> {

	private static final long	serialVersionUID	= -444338319191950385L;

	private FavoriteManager		manager;

	public FavoriteInterceptor() {
		super();
	}

	public FavoriteInterceptor(final FavoriteManager manager) {
		super();
		this.manager = manager;
	}

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		WebLog.getInstance().getLog().debug("Intercepting " + invocation);
		Assert.notNull(this.manager, "manager");
		ActionProxy proxy = invocation.getProxy();
		Object action = proxy.getAction();
		Class<?> clazz = action.getClass();
		Method method = clazz.getMethod(proxy.getMethod(), new Class[] {});
		String rule = clazz.getCanonicalName() + "." + method.getName() + "()";

		boolean favorite = false;

		if (this.containsRule(rule)) {
			favorite = this.getRule(rule);
		} else {
			if (method.isAnnotationPresent(Favorite.class)) {
				Favorite annotation = method.getAnnotation(Favorite.class);
				favorite = !annotation.skip();
			} else if (clazz.isAnnotationPresent(Favorite.class)) {
				Favorite annotation = method.getAnnotation(Favorite.class);
				favorite = !annotation.skip();
			}
			this.addRule(rule, favorite);
		}

		if (favorite) {
			HttpServletRequest request = this.getRequest();
			String url = Struts2Utils.getRequestPath(request);
			FavoriteData data = new FavoriteData(url, request);

			this.manager.save(data);
		}

		return invocation.invoke();
	}

	public void setManager(final String manager) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.manager = (FavoriteManager) Class.forName(manager).newInstance();
	}

}
