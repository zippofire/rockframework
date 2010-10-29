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
package net.woodstock.rockframework.web.captcha;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.NumberUtils;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.servlet.AbstractHttpServlet;

public class CaptchaServlet extends AbstractHttpServlet {

	private static final long	serialVersionUID		= 1L;

	public static final String	RANDOM_CHARS_PARAMETER	= "RANDOM_CHARS";

	public static final String	CAPTCHA_PARAMETER		= "net.woodstock.rockframework.web.captcha.CaptchaServlet.CAPTCHA_PARAMETER";

	private static final String	CONTENT_TYPE			= "image/jpeg";

	private static final String	RANDOM_CHARS			= "abcdefghijklmnopqrstuvwxyz123456789";

	private char[]				chars;

	@Override
	public void init() {
		String randomChars = this.getInitParameter(CaptchaServlet.RANDOM_CHARS_PARAMETER);
		if (StringUtils.isNotEmpty(randomChars)) {
			this.chars = randomChars.toCharArray();
		} else {
			this.chars = CaptchaServlet.RANDOM_CHARS.toCharArray();
		}
	}

	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			int index = NumberUtils.random(this.chars.length - 1);
			char c = this.chars[index];
			builder.append(c);
		}
		String text = builder.toString();
		CaptchaImage captchaImage = new CaptchaImage(text);
		byte[] bytes = captchaImage.getBytes();

		request.getSession().setAttribute(CaptchaServlet.CAPTCHA_PARAMETER, text);
		response.setContentType(CaptchaServlet.CONTENT_TYPE);
		response.setContentLength(bytes.length);
		response.getOutputStream().write(bytes);
	}

	public static String getSessionCaptcha(final HttpServletRequest request) {
		return (String) request.getSession().getAttribute(CaptchaServlet.CAPTCHA_PARAMETER);
	}

}
