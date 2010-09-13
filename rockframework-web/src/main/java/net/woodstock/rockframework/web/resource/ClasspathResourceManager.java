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
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.web.utils.ResponseUtils;

public class ClasspathResourceManager extends PathBasedResourceManager {

	public static final String	PATH_PARAM	= "path";

	private String				chroot;

	public ClasspathResourceManager(final String chroot) {
		super();
		this.chroot = chroot;
	}

	@Override
	public void manage(final HttpServletRequest request, final HttpServletResponse response, final String path) throws ServletException, IOException {
		URL url = this.getClass().getClassLoader().getResource(path);
		if (url == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		String fullPath = this.chroot + path;
		ResponseUtils.downloadFile(response, fullPath);
	}

}
