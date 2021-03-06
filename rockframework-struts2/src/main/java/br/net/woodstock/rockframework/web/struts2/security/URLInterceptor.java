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
package br.net.woodstock.rockframework.web.struts2.security;

import javax.servlet.http.HttpServletRequest;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.web.struts2.Struts2Constants;
import br.net.woodstock.rockframework.web.struts2.utils.Struts2Utils;

import com.opensymphony.xwork2.ActionInvocation;

public class URLInterceptor extends AccessInterceptor<String> {

	private static final long		serialVersionUID	= -1142678626424407060L;

	private static final String		HISTORY_PARAMETER	= "br.net.woodstock.rockframework.web.struts2.security.URLInterceptor.HISTORY_PARAMETER";

	private static final String		NO_ACCESS_PARAMETER	= "br.net.woodstock.rockframework.web.struts2.security.URLInterceptor.NO_ACCESS_PARAMETER";

	private transient URLValidator	validator;

	public URLInterceptor() {
		super(URLInterceptor.HISTORY_PARAMETER, URLInterceptor.NO_ACCESS_PARAMETER);
	}

	public URLInterceptor(final URLValidator validator) {
		super(URLInterceptor.HISTORY_PARAMETER, URLInterceptor.NO_ACCESS_PARAMETER);
		this.validator = validator;
	}

	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		Assert.notNull(this.validator, "validator");
		WebLog.getInstance().getLogger().debug("Intercepting " + invocation);
		HttpServletRequest request = this.getRequest();
		String url = Struts2Utils.getRequestPath(request);

		boolean hasAccess = false;
		boolean skipTest = false;

		if (this.existOnHistory(url)) {
			hasAccess = true;
			skipTest = true;
		} else if (this.existOnNoAccess(url)) {
			hasAccess = false;
			skipTest = true;
		}

		if (!skipTest) {
			hasAccess = this.validator.isAvailable(request, url);
		}

		if (hasAccess) {
			this.addToHistory(url);
		} else {
			this.addToNoAccess(url);
			WebLog.getInstance().getLogger().info("Invalid privileges to open url " + url);
			return Struts2Constants.NO_ACCESS;
		}

		return invocation.invoke();
	}

	// Setters
	public void setValidator(final String validator) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		this.validator = (URLValidator) Class.forName(validator).newInstance();
	}

}
