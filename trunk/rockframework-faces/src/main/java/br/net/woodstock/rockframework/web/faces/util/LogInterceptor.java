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
package br.net.woodstock.rockframework.web.faces.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.net.woodstock.rockframework.web.config.WebLog;

@Interceptor
@Log
public class LogInterceptor implements Serializable {

	private static final long	serialVersionUID	= -6252629924004023556L;

	private static final String	GET_METHOD_REGEX	= "get[A-Z].*";

	private static final char	METHOD_SEPARATOR	= '.';

	private static final char	METHOD_START		= '(';

	private static final char	METHOD_END			= ')';

	@AroundInvoke
	public Object intercept(final InvocationContext context) throws Exception {
		StringBuilder builder = new StringBuilder();
		Method method = context.getMethod();
		if (method.getParameterTypes().length == 0) {
			if (method.getReturnType() == String.class) {
				if (!Pattern.matches(LogInterceptor.GET_METHOD_REGEX, method.getName())) {
					builder.append(method.getDeclaringClass().getName());
					builder.append(LogInterceptor.METHOD_SEPARATOR);
					builder.append(method.getName());
					builder.append(LogInterceptor.METHOD_START);
					builder.append(LogInterceptor.METHOD_END);
					WebLog.getInstance().getLog().info(builder.toString());
				}
			}
		}

		return context.proceed();
	}
}
