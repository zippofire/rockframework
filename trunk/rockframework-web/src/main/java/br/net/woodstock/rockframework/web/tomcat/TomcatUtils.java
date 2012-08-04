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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class TomcatUtils {

	private static final String	CONTEXT_NAME	= "java:comp/env";

	private static Context		context;

	private TomcatUtils() {
		//
	}

	public static Context getContext() throws NamingException {
		if (TomcatUtils.context == null) {
			Context c = new InitialContext();
			TomcatUtils.context = (Context) c.lookup(TomcatUtils.CONTEXT_NAME);
		}
		return TomcatUtils.context;
	}

}
