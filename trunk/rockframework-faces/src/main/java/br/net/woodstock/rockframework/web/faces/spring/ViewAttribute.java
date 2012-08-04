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
package br.net.woodstock.rockframework.web.faces.spring;

import java.io.Serializable;

class ViewAttribute implements Serializable {

	private static final long	serialVersionUID	= 1734820659448540052L;

	private String[]			views;

	private String				name;

	private Object				value;

	public ViewAttribute() {
		super();
	}

	public ViewAttribute(final String[] views, final String name, final Object value) {
		super();
		this.views = views;
		this.name = name;
		this.value = value;
	}

	public String[] getViews() {
		return this.views;
	}

	public void setViews(final String[] views) {
		this.views = views;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

}
