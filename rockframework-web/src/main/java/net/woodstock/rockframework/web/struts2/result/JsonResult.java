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
package net.woodstock.rockframework.web.struts2.result;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.json.JsonConverterFactory;
import net.woodstock.rockframework.utils.StringUtils;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class JsonResult extends BaseResult {

	private static final long	serialVersionUID	= 1508203812611801564L;

	public static final String	JSON_CONTENT_TYPE	= "text/plain";

	public static final String	JSON_CHARSET		= "utf-8";

	public static final String	IGNORE_SEPARATOR	= ",";

	private Collection<String>	ignored;

	private String				charset				= JsonResult.JSON_CHARSET;

	private String				root;

	@Override
	public void execute(final ActionInvocation invocation) throws Exception {
		if (StringUtils.isEmpty(this.root)) {
			throw new IllegalArgumentException("Root must be not empty");
		}
		ActionContext actionContext = invocation.getInvocationContext();
		ValueStack stack = invocation.getStack();
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		PrintWriter writer = response.getWriter();
		Object rootObject = stack.findValue(this.root);
		String json = null;

		if (rootObject != null) {
			ConverterContext context = new BeanConverterContext(null, this.root, rootObject.getClass());

			if (this.ignored != null) {
				context.getIgnored().addAll(this.ignored);
			}

			json = JsonConverterFactory.getInstance().getConverter().to(context, rootObject);
		} else {
			this.getLogger().warn("Root object is empty");
			json = "";
		}

		response.setContentType(JsonResult.JSON_CONTENT_TYPE);
		response.setCharacterEncoding(this.charset);
		writer.write(json);
	}

	public void setIgnore(final String ignore) {
		if (!StringUtils.isEmpty(ignore)) {
			String[] ignores = ignore.split(JsonResult.IGNORE_SEPARATOR);
			this.ignored = new ArrayList<String>();
			for (String s : ignores) {
				if (!StringUtils.isEmpty(s)) {
					this.ignored.add(s.trim());
				}
			}
		}
	}

	public void setCharset(final String charset) {
		if (!StringUtils.isEmpty(charset)) {
			this.charset = charset;
		}
	}

	public void setRoot(final String root) {
		this.root = root;
	}

}
