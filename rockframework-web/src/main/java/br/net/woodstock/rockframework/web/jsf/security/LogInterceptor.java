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
package br.net.woodstock.rockframework.web.jsf.security;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.config.WebLog;

@Interceptor
@Log
public class LogInterceptor implements Serializable {

	private static final long	serialVersionUID	= -6252629924004023556L;

	private static final char	METHOD_SEPARATOR	= '.';

	private static final char	METHOD_START		= '(';

	private static final char	METHOD_END			= ')';

	private static final char	PARAMETER_SEPARATOR	= ',';

	@AroundInvoke
	public Object intercept(final InvocationContext context) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(context.getTarget().getClass().getCanonicalName());
		builder.append(LogInterceptor.METHOD_SEPARATOR);
		builder.append(context.getMethod().getName());
		builder.append(LogInterceptor.METHOD_START);
		if (ConditionUtils.isNotEmpty(context.getParameters())) {
			boolean first = true;
			for (Object o : context.getParameters()) {
				if (!first) {
					builder.append(LogInterceptor.PARAMETER_SEPARATOR);
				}
				builder.append(o);
				if (first) {
					first = false;
				}
			}
		}
		builder.append(LogInterceptor.METHOD_END);
		WebLog.getInstance().getLog().info(builder.toString());
		return context.proceed();
	}
}
