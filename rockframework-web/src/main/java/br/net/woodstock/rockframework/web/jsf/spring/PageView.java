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
package br.net.woodstock.rockframework.web.jsf.spring;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PageView implements Serializable {

	private static final long	serialVersionUID	= 4243010108100828052L;

	private String				viewId;

	private Map<String, Object>	attributes;

	public PageView(final String viewId) {
		super();
		this.viewId = viewId;
		this.attributes = new HashMap<String, Object>();
	}

	public String getViewId() {
		return this.viewId;
	}

	public void setViewId(final String viewId) {
		this.viewId = viewId;
	}

	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(final Map<String, Object> attributes) {
		this.attributes = attributes;
	}

}
