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
package net.woodstock.rockframework.web.struts2.interceptor;

import java.util.Enumeration;
import java.util.Formatter;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.logging.LogLevel;
import net.woodstock.rockframework.utils.ArrayUtils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;

public class DebugInterceptor extends BaseInterceptor {

	private static final long	serialVersionUID	= 657049342110588586L;

	private static final String	NEW_LINE			= "\n";

	private LogLevel			level;

	private boolean				printParameters;

	private boolean				printSession;

	public DebugInterceptor() {
		super();
		this.level = LogLevel.INFO;
		this.printParameters = true;
		this.printSession = true;
	}

	@SuppressWarnings("unchecked")
	public String intercept(final ActionInvocation invocation) throws Exception {
		HttpServletRequest request = this.getRequest();
		ActionProxy proxy = invocation.getProxy();

		if (this.printParameters) {
			Set set = request.getParameterMap().keySet();
			StringBuilder builder = new StringBuilder();
			builder.append(DebugInterceptor.NEW_LINE);
			builder.append("==================== Begin Parameters ====================");
			builder.append(DebugInterceptor.NEW_LINE);
			for (Object o : set) {
				String key = o.toString();
				String value = ArrayUtils.toString(request.getParameterValues(key));
				String s = new Formatter().format("%50s => %s", key, value).toString();
				builder.append(s);
				builder.append(DebugInterceptor.NEW_LINE);
			}
			builder.append("===================== End Parameters =====================");
			builder.append(DebugInterceptor.NEW_LINE);
			this.log(builder.toString());
		}

		if (this.printSession) {
			HttpSession session = request.getSession();
			Enumeration enumeration = session.getAttributeNames();
			StringBuilder builder = new StringBuilder();
			builder.append(DebugInterceptor.NEW_LINE);
			builder.append("==================== Begin Session ====================");
			builder.append(DebugInterceptor.NEW_LINE);
			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement().toString();
				Object value = session.getAttribute(key);
				String s = new Formatter().format("%50s => %s", key, value).toString();
				builder.append(s);
				builder.append(DebugInterceptor.NEW_LINE);
			}
			builder.append("===================== End Session =====================");
			builder.append(DebugInterceptor.NEW_LINE);
			this.log(builder.toString());
		}

		this.log("Invoking " + proxy.getAction().getClass().getCanonicalName() + "." + proxy.getMethod() + "()");
		String s = invocation.invoke();
		this.log("Return of " + proxy.getAction().getClass().getCanonicalName() + "." + proxy.getMethod() + "(): '" + s + "'");
		return s;
	}

	private void log(final String message) {
		switch (this.level) {
			case TRACE:
				this.getLogger().trace(message);
				break;
			case DEBUG:
				this.getLogger().debug(message);
				break;
			case INFO:
				this.getLogger().info(message);
				break;
			case WARN:
				this.getLogger().warn(message);
				break;
			case ERROR:
				this.getLogger().error(message);
				break;
			case FATAL:
				this.getLogger().fatal(message);
				break;
			default:
				break;
		}
	}

	// Params
	public void setLevel(final String level) {
		this.level = LogLevel.valueOf(level);
	}

	public void setPrintParameters(final String printParameters) {
		this.printParameters = Boolean.parseBoolean(printParameters);
	}

	public void setPrintSession(final String printSession) {
		this.printSession = Boolean.parseBoolean(printSession);
	}

}
