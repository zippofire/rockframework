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
package net.woodstock.rockframework.web.jsp.taglib.mail;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;

import net.woodstock.rockframework.web.jsp.taglib.BaseTag;

public class TextTag extends BaseTag {

	private static final long	serialVersionUID	= 1L;

	private String				html				= "false";

	public TextTag() {
		super();
	}

	@Override
	public void doTag() throws JspException, IOException {
		if ((this.getParent() == null) || (!(this.getParent() instanceof MessageTag))) {
			throw new JspException("Text must appers inside a email tag");
		}
		StringWriter writer = new StringWriter();
		this.getJspBody().invoke(writer);
		String text = writer.getBuffer().toString();
		MessageTag parent = (MessageTag) this.getParent();
		parent.setText(text);
		parent.setHtml(Boolean.parseBoolean(this.html));
	}

	public String getHtml() {
		return this.html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
