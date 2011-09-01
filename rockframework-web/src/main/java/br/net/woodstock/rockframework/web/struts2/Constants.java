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
package br.net.woodstock.rockframework.web.struts2;

import com.opensymphony.xwork2.Action;

public abstract class Constants {

	public static final String	ERROR			= Action.ERROR;

	public static final String	FORWARD			= "forward";

	public static final String	INDEX			= "index";

	public static final String	INPUT			= Action.INPUT;

	public static final String	INVALID_METHOD	= "invalid-method";

	public static final String	INVALID_REFERER	= "invalid-referer";

	public static final String	LOGIN			= Action.LOGIN;

	public static final String	LOGOUT			= "logout";

	public static final String	NONE			= Action.NONE;

	public static final String	NO_ACCESS		= "no-access";

	public static final String	NO_LOGIN		= "no-login";

	public static final String	NO_REFERER		= "no-referer";

	public static final String	REDIRECT		= "redirect";

	public static final String	SUCCESS			= Action.SUCCESS;

	private Constants() {
		//
	}
}
