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
package net.woodstock.rockframework.web.filter.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.ClassLoaderUtils;
import net.woodstock.rockframework.utils.IOUtils;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.filter.HttpFilter;

public class ResourceFilter extends HttpFilter {

	private String[]	deny;

	@Override
	public void doInit() {
		String deny = this.getInitParameter("DENY");
		if (!StringUtils.isEmpty(deny)) {
			this.deny = deny.split(",");
		}
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException {
		String context = request.getContextPath();
		String resource = request.getRequestURI().replace(context, "");
		resource = resource.replaceAll("^/\\w*/", "");

		if ((StringUtils.isEmpty(resource)) || (resource.endsWith("/"))) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		URL url = ClassLoaderUtils.getResource(resource);

		if (url == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if ((this.deny != null) && (this.deny.length > 0)) {
			for (String s : this.deny) {
				s = s.trim();
				if (s.indexOf('*') != -1) {
					s = s.replaceAll("\\*", ".*");
				}
				if (Pattern.matches(s, resource)) {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					return;
				}
			}
		}

		InputStream input = url.openStream();
		OutputStream output = response.getOutputStream();

		URLConnection connection = url.openConnection();

		String contentEncoding = connection.getContentEncoding();
		int contentLength = connection.getContentLength();
		String contentType = connection.getContentType();

		if (contentEncoding != null) {
			response.setCharacterEncoding(contentEncoding);
		}

		response.setContentLength(contentLength);
		response.setContentType(contentType);

		IOUtils.copy(input, output, true);
	}

}
