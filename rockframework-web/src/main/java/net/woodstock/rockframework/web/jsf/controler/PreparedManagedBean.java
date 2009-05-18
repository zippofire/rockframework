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
package net.woodstock.rockframework.web.jsf.controler;

import java.util.Collection;

public abstract class PreparedManagedBean extends ManagedBean {

	private static final long	serialVersionUID	= -3948681022744521609L;

	public static final String	PREPARE_ERROR		= "prepare-error";

	public static final String	DELETE_SUCCESS		= "delete-success";

	public static final String	DELETE_ERROR		= "delete-error";

	public static final String	QUERY_SUCCESS		= "query-success";

	public static final String	QUERY_ERROR			= "query-error";

	public static final String	SAVE_SUCCESS		= "save-success";

	public static final String	SAVE_ERROR			= "save-error";

	public static final String	UPDATE_SUCCESS		= "update-success";

	public static final String	UPDATE_ERROR		= "update-error";

	public static final String	VIEW_SUCCESS		= "view-success";

	public static final String	VIEW_ERROR			= "view-error";

	// List
	public Collection<?> getItems() {
		return null;
	}

	// Beans
	public String delete() {
		return null;
	}

	public String query() {
		return null;
	}

	public String save() {
		return null;
	}

	public String update() {
		return null;
	}

	public String view() {
		return null;
	}

	// Prepare
	public String prepareDelete() {
		return null;
	}

	public String prepareQuery() {
		return null;
	}

	public String prepareSave() {
		return null;
	}

	public String prepareUpdate() {
		return null;
	}

	public String prepareView() {
		return null;
	}

}
