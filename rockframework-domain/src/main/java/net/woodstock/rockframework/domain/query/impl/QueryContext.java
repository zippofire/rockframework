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
package net.woodstock.rockframework.domain.query.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class QueryContext implements Serializable {

	private static final long					serialVersionUID	= 6837306971039414604L;

	private String								realName;

	private String								name;

	private String								alias;

	private QueryContext						parent;

	private boolean								joinNeeded;

	private Collection<QueryContextParameter>	parameters;

	private Collection<QueryContext>			childs;

	private String								queryString;

	public QueryContext(final String realName, final String name, final String alias, final QueryContext parent) {
		super();
		this.realName = realName;
		this.name = name;
		this.alias = alias;
		this.parent = parent;
		this.parameters = new LinkedList<QueryContextParameter>();
		this.childs = new LinkedList<QueryContext>();
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(final String realName) {
		this.realName = realName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(final String alias) {
		this.alias = alias;
	}

	public QueryContext getParent() {
		return this.parent;
	}

	public void setParent(final QueryContext parent) {
		this.parent = parent;
	}

	public boolean isJoinNeeded() {
		return this.joinNeeded;
	}

	public void setJoinNeeded(final boolean joinNeeded) {
		this.joinNeeded = joinNeeded;
	}

	public Collection<QueryContextParameter> getParameters() {
		return this.parameters;
	}

	public void setParameters(final Collection<QueryContextParameter> parameters) {
		this.parameters = parameters;
	}

	public Collection<QueryContext> getChilds() {
		return this.childs;
	}

	public void setChilds(final Collection<QueryContext> childs) {
		this.childs = childs;
	}

	public String getQueryString() {
		return this.queryString;
	}

	public void setQueryString(final String queryString) {
		this.queryString = queryString;
	}

	// Aux
	public Collection<QueryContext> getChildsRecursive() {
		List<QueryContext> list = new LinkedList<QueryContext>();
		for (QueryContext context : this.childs) {
			list.add(context);
			list.addAll(context.getChildsRecursive());
		}
		return list;
	}

	public Collection<QueryContextParameter> getParametersRecursive() {
		List<QueryContextParameter> list = new LinkedList<QueryContextParameter>();
		for (QueryContextParameter parameter : this.parameters) {
			list.add(parameter);
		}
		for (QueryContext context : this.childs) {
			list.addAll(context.getParametersRecursive());
		}
		return list;
	}

	public boolean hasParametersRecursive() {
		if (this.parameters.size() > 0) {
			return true;
		}
		for (QueryContext context : this.childs) {
			if (context.hasParametersRecursive()) {
				return true;
			}
		}
		return false;
	}
}
