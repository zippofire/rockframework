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
package net.woodstock.rockframework.web.struts2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.woodstock.rockframework.web.struts2.utils.Struts2Utils;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public abstract class Result implements com.opensymphony.xwork2.Result {

	private static final long	serialVersionUID	= -4756169112128368396L;

	protected Object findValue(final ActionInvocation invocation, final String expr) {
		ValueStack stack = invocation.getStack();
		return stack.findValue(expr);
	}

	protected HttpServletRequest getRequest() {
		return Struts2Utils.getRequest();
	}

	protected HttpSession getSession() {
		return Struts2Utils.getSession();
	}

}
