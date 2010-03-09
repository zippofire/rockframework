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
package net.woodstock.rockframework.web.jsp.taglib.creator;

public enum BodyContent {
	EMPTY("empty"), JSP("JSP"), SCRIPTLESS("scriptless"), TAG_DEPENDENT("tagdependent");

	private String	name;

	private BodyContent(final String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
