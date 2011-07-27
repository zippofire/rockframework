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
package net.woodstock.rockframework.web.sso.client;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.web.filter.AbstractHttpFilter;
import net.woodstock.rockframework.web.sso.RequestToken;
import net.woodstock.rockframework.web.sso.ResponseToken;
import net.woodstock.rockframework.web.sso.SSOConstants;
import net.woodstock.rockframework.web.sso.SSOHelper;
import net.woodstock.rockframework.web.sso.SSOUserManager;
import net.woodstock.rockframework.web.sso.User;

public abstract class AbstractSSOClientFilter extends AbstractHttpFilter {

	public static final String			APP_PARAMETER			= "app";

	public static final String			SERVER_PARAMETER		= "server";

	public static final String			ERROR_PAGE_PARAMETER	= "errorPage";

	public static final long			MAX_TIME				= 300000;

	private String						app;

	private String						server;

	private String						errorPage;

	private Map<String, RequestToken>	mapToken;

	private Map<String, String>			mapURL;

	protected abstract SSOUserManager getUserManager();

	@Override
	public void init() {
		super.init();
		this.app = this.getInitParameter(AbstractSSOClientFilter.APP_PARAMETER);
		this.server = this.getInitParameter(AbstractSSOClientFilter.SERVER_PARAMETER);
		this.errorPage = this.getInitParameter(AbstractSSOClientFilter.ERROR_PAGE_PARAMETER);
		this.mapToken = new HashMap<String, RequestToken>();
		this.mapURL = new HashMap<String, String>();
	}

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		User user = SSOHelper.getUser(request);
		if (user == null) {
			Cookie cookie = SSOFilterClientHelper.getSSOCookie(request);
			if (cookie != null) {
				this.doRestoreCookie(request, response, cookie, chain);
			} else if (request.getParameter(SSOConstants.HASH_PARAMETER) != null) {
				this.doValidateHash(request, response, chain);
			} else {
				this.doFirstAccess(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	private void doFirstAccess(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		String id = SSOFilterClientHelper.getRequestId();
		String url = SSOFilterClientHelper.getRequestPath(request);
		String localServer = SSOFilterClientHelper.getServerPath(request);

		RequestToken token = new RequestToken(id, new Date(), this.app, localServer);

		StringBuilder builder = new StringBuilder();
		builder.append(this.server);
		builder.append("?");
		builder.append(SSOConstants.HASH_PARAMETER);
		builder.append(token.toString());

		String redirectPath = builder.toString();

		this.mapToken.put(id, token);
		this.mapURL.put(id, url);
		response.sendRedirect(redirectPath);
	}

	private void doRestoreCookie(final HttpServletRequest request, final HttpServletResponse response, final Cookie cookie, final FilterChain chain) throws IOException, ServletException {
		User user = this.getUserManager().getUserByHash(cookie.getValue(), this.app);

		if (user == null) {
			response.sendRedirect(this.errorPage);
			return;
		}

		SSOHelper.setUser(request, user);
		chain.doFilter(request, response);
	}

	private void doValidateHash(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		String hash = request.getParameter(SSOConstants.HASH_PARAMETER);

		ResponseToken responseToken = ResponseToken.fromString(hash);
		RequestToken requestToken = this.mapToken.get(responseToken.getId());

		if (responseToken.getDate().getTime() - requestToken.getDate().getTime() > AbstractSSOClientFilter.MAX_TIME) {
			response.sendRedirect(this.errorPage);
			return;
		}

		User user = this.getUserManager().getUserByHash(responseToken.getHash(), this.app);

		if (user == null) {
			response.sendRedirect(this.errorPage);
			return;
		}

		SSOHelper.setUser(request, user);
		chain.doFilter(request, response);
	}
}
