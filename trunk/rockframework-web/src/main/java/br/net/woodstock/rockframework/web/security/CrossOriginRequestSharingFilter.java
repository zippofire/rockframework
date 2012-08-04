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
package br.net.woodstock.rockframework.web.security;

public class CrossOriginRequestSharingFilter extends ResponseHeaderValueFilter {

	public static final String	ACCESS_CONTROL_ALLOW_CREDENTIALS	= "Access-Control-Allow-Credentials";

	public static final String	ACCESS_CONTROL_ALLOW_HEADERS		= "Access-Control-Allow-Headers";

	public static final String	ACCESS_CONTROL_ALLOW_METHODS		= "Access-Control-Allow-Methods";

	public static final String	ACCESS_CONTROL_ALLOW_ORIGIN			= "Access-Control-Allow-Origin";

	public static final String	ACCESS_CONTROL_EXPOSE_HEADERS		= "Access-Control-Expose-Headers";

	public static final String	ACCESS_CONTROL_MAX_AGE				= "Access-Control-Max-Age";

}
