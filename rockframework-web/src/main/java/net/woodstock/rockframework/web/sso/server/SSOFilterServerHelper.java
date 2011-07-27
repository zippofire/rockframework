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
package net.woodstock.rockframework.web.sso.server;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.web.sso.RequestToken;
import net.woodstock.rockframework.web.sso.ResponseToken;
import net.woodstock.rockframework.web.sso.SSOConstants;

abstract class SSOFilterServerHelper {

	private static final String	REQUEST_TOKEN_PARAMETER		= "net.woodstock.rockframework.web.sso.server.SSOFilterServerHelper.REQUEST_TOKEN_PARAMETER";

	private static final String	RESPONSE_TOKEN_PARAMETER	= "net.woodstock.rockframework.web.sso.server.SSOFilterServerHelper.RESPONSE_TOKEN_PARAMETER";

	private static final String	COOKIE_PATH					= "/";

	private static final int	COOKIE_MAX_AGE				= 0;

	public SSOFilterServerHelper() {
		//
	}

	public static void setRequestToken(final HttpServletRequest request, final RequestToken token) {
		Assert.notNull(request, "request");
		Assert.notNull(token, "token");
		SSOFilterServerHelper.setRequestToken(request.getSession(), token);
	}

	public static void setRequestToken(final HttpSession session, final RequestToken token) {
		Assert.notNull(session, "session");
		Assert.notNull(token, "token");
		session.setAttribute(SSOFilterServerHelper.REQUEST_TOKEN_PARAMETER, token);
	}

	public static RequestToken getRequestToken(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		return SSOFilterServerHelper.getRequestToken(request.getSession());
	}

	public static RequestToken getRequestToken(final HttpSession session) {
		Assert.notNull(session, "session");
		return (RequestToken) session.getAttribute(SSOFilterServerHelper.REQUEST_TOKEN_PARAMETER);
	}

	public static void setResponseToken(final HttpServletRequest request, final ResponseToken token) {
		Assert.notNull(request, "request");
		Assert.notNull(token, "token");
		SSOFilterServerHelper.setResponseToken(request.getSession(), token);
	}

	public static void setResponseToken(final HttpSession session, final ResponseToken token) {
		Assert.notNull(session, "session");
		Assert.notNull(token, "token");
		session.setAttribute(SSOFilterServerHelper.RESPONSE_TOKEN_PARAMETER, token);
	}

	public static ResponseToken getResponseToken(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		return SSOFilterServerHelper.getResponseToken(request.getSession());
	}

	public static ResponseToken getResponseToken(final HttpSession session) {
		Assert.notNull(session, "session");
		return (ResponseToken) session.getAttribute(SSOFilterServerHelper.RESPONSE_TOKEN_PARAMETER);
	}

	public static void setCookie(final HttpServletResponse response, final String domain, final String hash) {
		Assert.notNull(response, "response");
		Assert.notEmpty(hash, "hash");
		Cookie cookie = new Cookie(SSOConstants.SSO_COOKIE_NAME, hash);
		cookie.setDomain(domain);
		cookie.setMaxAge(SSOFilterServerHelper.COOKIE_MAX_AGE);
		cookie.setPath(SSOFilterServerHelper.COOKIE_PATH);
		response.addCookie(cookie);
	}
}
