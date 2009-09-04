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
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import net.woodstock.rockframework.utils.JsonUtils;
import net.woodstock.rockframework.utils.StringUtils;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

public class JsonResult extends BaseResult {

	private static final long	serialVersionUID	= 1508203812611801564L;

	public static final String	JSON_CONTENT_TYPE	= "text/plain";

	public static final String	JSON_CHARSET		= "utf-8";

	private String[]			ignoreProperties;

	private String				charset				= JsonResult.JSON_CHARSET;

	private int					maxLevel			= JsonUtils.ALL_LEVELS;

	private String				root;

	@Override
	@SuppressWarnings("unchecked")
	public void execute(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();
		ValueStack stack = invocation.getStack();
		HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
		PrintWriter writer = response.getWriter();
		Object rootObject = stack.findValue(this.root);

		String json = null;

		if (rootObject instanceof Collection) {
			json = JsonUtils.toJson((Collection<?>) rootObject, this.ignoreProperties, this.maxLevel);
		} else {
			json = JsonUtils.toJson(rootObject, this.ignoreProperties, this.maxLevel);
		}

		response.setContentType(JsonResult.JSON_CONTENT_TYPE);
		response.setCharacterEncoding(this.charset);
		writer.write(json);
	}

	public void setCharset(String charset) {
		if (!StringUtils.isEmpty(charset)) {
			this.charset = charset;
		}
	}

	public void setIgnoreProperties(String ignoreProperties) {
		if (!StringUtils.isEmpty(ignoreProperties)) {
			this.ignoreProperties = ignoreProperties.split(",");
		}
	}

	public void setMaxLevel(String maxLevel) {
		if (StringUtils.isEmpty(maxLevel)) {
			this.maxLevel = Integer.parseInt(maxLevel);
		}
	}

	public void setRoot(String root) {
		this.root = root;
	}

}
