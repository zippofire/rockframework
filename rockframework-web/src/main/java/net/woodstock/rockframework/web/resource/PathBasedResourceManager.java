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
package net.woodstock.rockframework.web.resource;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.StringUtils;

public abstract class PathBasedResourceManager implements ResourceManager {

	public static final String	PATH_PARAMETER	= "path";

	public PathBasedResourceManager() {
		super();
	}

	@Override
	public final void manage(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter(PathBasedResourceManager.PATH_PARAMETER);
		if (StringUtils.isEmpty(path)) {
			throw new ServletException("Invalid path");
		}

		this.manage(request, response, path);
	}

	protected abstract void manage(final HttpServletRequest request, final HttpServletResponse response, String path) throws ServletException, IOException;

}
