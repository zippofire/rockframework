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
package net.woodstock.rockframework.web.jsp.taglib.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.jsp.PageContext;

import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.jsp.taglib.BaseTag;

public class StackTraceTag extends BaseTag {

	public StackTraceTag() {
		super();
	}

	@Override
	public void doTag() throws IOException {
		PageContext context = (PageContext) this.getJspContext();
		Exception exception = context.getException();

		if (exception == null) {
			return;
		}

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);

		pw.close();
		sw.close();

		String str = sw.getBuffer().toString();

		WebLog.getInstance().getLog().info(exception.getMessage(), exception);
		context.getOut().write(str);
	}

}
