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
package net.woodstock.rockframework.web.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.util.Assert;

public abstract class SSOHelper {

	public static final String	USER_PARAMETER	= "net.woodstock.rockframework.web.sso.USER";

	public static final String	SSO_COOKIE_NAME	= "rockframework.sso.cookie";

	private SSOHelper() {
		//
	}

	public static User getUser(final HttpServletRequest request) {
		Assert.notNull(request, "request");
		return SSOHelper.getUser(request.getSession());
	}

	public static User getUser(final HttpSession session) {
		Assert.notNull(session, "session");
		User user = (User) session.getAttribute(SSOHelper.USER_PARAMETER);
		return user;
	}

	public static void setUser(final HttpServletRequest request, final User user) {
		Assert.notNull(request, "request");
		Assert.notNull(user, "user");
		SSOHelper.setUser(request.getSession(), user);
	}

	public static void setUser(final HttpSession session, final User user) {
		Assert.notNull(session, "session");
		Assert.notNull(user, "user");
		session.setAttribute(SSOHelper.USER_PARAMETER, user);
	}

}
