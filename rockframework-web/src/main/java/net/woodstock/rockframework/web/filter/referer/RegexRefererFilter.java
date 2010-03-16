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
package net.woodstock.rockframework.web.filter.referer;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.utils.RequestUtils;

public class RegexRefererFilter extends NoRefererFilter {

	public static final String	REGEX_PARAMETER	= "regex";

	private String				regex;

	@Override
	protected boolean validateReferer(final HttpServletRequest request) {
		if (this.regex == null) {
			this.regex = this.getInitParameter(RegexRefererFilter.REGEX_PARAMETER);
		}
		if (StringUtils.isEmpty(this.regex)) {
			throw new RuntimeException("Parameter '" + RegexRefererFilter.REGEX_PARAMETER + "' must be set");
		}
		if (super.validateReferer(request)) {
			String referer = RequestUtils.getReferer(request);
			if (Pattern.matches(this.regex, referer)) {
				return true;
			}
		}
		return false;
	}

}
