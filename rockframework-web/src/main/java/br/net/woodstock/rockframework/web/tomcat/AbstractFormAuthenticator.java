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
package br.net.woodstock.rockframework.web.tomcat;

import java.io.IOException;
import java.security.Principal;


import org.apache.catalina.authenticator.Constants;
import org.apache.catalina.authenticator.FormAuthenticator;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.LoginConfig;

import br.net.woodstock.rockframework.utils.ConditionUtils;

public abstract class AbstractFormAuthenticator extends FormAuthenticator {

	public static final String	USERNAME_PARAMETER	= Constants.FORM_USERNAME;

	public static final String	PASSWORD_PARAMETER	= Constants.FORM_PASSWORD;

	@Override
	public boolean authenticate(final Request request, final Response response, final LoginConfig config) throws IOException {
		Principal principal = request.getUserPrincipal();
		String username = request.getParameter(AbstractFormAuthenticator.USERNAME_PARAMETER);
		String password = request.getParameter(AbstractFormAuthenticator.PASSWORD_PARAMETER);
		if (principal != null) {
			return true;
		} else if (ConditionUtils.isNotEmpty(username)) {
			principal = this.validate(username, password);
			if (principal != null) {
				this.register(request, response, principal, Constants.FORM_METHOD, username, password);
				return true;
			}
		}
		return super.authenticate(request, response, config);
	}

	protected abstract Principal validate(String username, String password);
}
