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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.web.struts2.ConditionalInterceptor;

public abstract class AccessInterceptor<R> extends ConditionalInterceptor<R> {

	private static final long	serialVersionUID	= -1770300413724957767L;

	private String				historyAttribute;

	private String				noAccessAttribute;

	public AccessInterceptor(final String historyAttribute, final String noAccessAttribute) {
		super();
		Assert.notEmpty(historyAttribute, "historyAttribue");
		Assert.notEmpty(noAccessAttribute, "noAccessAttribute");
		this.historyAttribute = historyAttribute;
		this.noAccessAttribute = noAccessAttribute;
	}

	@SuppressWarnings("unchecked")
	private List<String> getHistory() {
		HttpSession session = this.getSession();
		List<String> history = (List<String>) session.getAttribute(this.historyAttribute);
		if (history == null) {
			history = new ArrayList<String>();
			session.setAttribute(this.historyAttribute, history);
		}
		return history;
	}

	@SuppressWarnings("unchecked")
	private List<String> getNoAccess() {
		HttpSession session = this.getSession();
		List<String> noAccess = (List<String>) session.getAttribute(this.noAccessAttribute);
		if (noAccess == null) {
			noAccess = new ArrayList<String>();
			session.setAttribute(this.noAccessAttribute, noAccess);
		}
		return noAccess;
	}

	protected void addToHistory(final String name) {
		List<String> history = this.getHistory();
		history.add(name);
	}

	protected void addToNoAccess(final String name) {
		List<String> noAccess = this.getNoAccess();
		noAccess.add(name);
	}

	protected boolean existOnHistory(final String name) {
		List<String> history = this.getHistory();
		return history.contains(name);
	}

	protected boolean existOnNoAccess(final String name) {
		List<String> noAccess = this.getNoAccess();
		return noAccess.contains(name);
	}

}
