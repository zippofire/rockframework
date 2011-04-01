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
package net.woodstock.rockframework.web.filter.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.util.CharsetTransform;
import net.woodstock.rockframework.utils.ConditionUtils;
import net.woodstock.rockframework.utils.IOUtils;
import net.woodstock.rockframework.web.filter.AbstractHttpFilter;
import net.woodstock.rockframework.web.util.CachedHttpServletResponse;
import net.woodstock.rockframework.web.util.CachedServletOutputStream;

public class CharsetFilter extends AbstractHttpFilter {

	public static final String	FROM_PARAMETER	= "from";

	public static final String	TO_PARAMETER	= "to";

	private Charset				charsetFrom;

	private Charset				charsetTo;

	private CharsetTransform	charsetTransform;

	@Override
	public void init() {
		String from = this.getInitParameter(CharsetFilter.FROM_PARAMETER);
		String to = this.getInitParameter(CharsetFilter.FROM_PARAMETER);
		this.charsetFrom = Charset.forName(from);
		if (ConditionUtils.isEmpty(to)) {
			this.charsetTo = Charset.defaultCharset();
		} else {
			this.charsetTo = Charset.forName(to);
		}
		this.charsetTransform = new CharsetTransform(this.charsetFrom, this.charsetTo);
	}

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		CachedHttpServletResponse responseWrapper = new CachedHttpServletResponse(response);

		chain.doFilter(request, responseWrapper);

		CachedServletOutputStream wrapper = (CachedServletOutputStream) responseWrapper.getOutputStream();

		InputStream cache = wrapper.getCache();
		String text = IOUtils.toString(cache, this.charsetFrom);
		String content = this.charsetTransform.transform(text);

		response.getOutputStream().write(content.getBytes());
	}
}
