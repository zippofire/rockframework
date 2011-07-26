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

import javax.servlet.http.HttpServletRequest;

import net.woodstock.rockframework.util.RandomGenerator;
import net.woodstock.rockframework.web.utils.RequestUtils;

abstract class SSOFilterHelper {

	private static final int	ID_SIZE	= 16;

	private SSOFilterHelper() {
		//
	}

	public static String getRequestId() {
		return new RandomGenerator(SSOFilterHelper.ID_SIZE).generate();
	}

	public static String getServerPath(final HttpServletRequest request) {
		return RequestUtils.getApplicationUrl(request);
	}
	
	public static String getRequestPath(final HttpServletRequest request) {
		return RequestUtils.getFullRequestPath(request);
	}

}
