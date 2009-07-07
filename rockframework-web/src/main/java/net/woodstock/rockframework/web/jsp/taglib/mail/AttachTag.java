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

import java.io.File;
import java.io.IOException;

import javax.servlet.jsp.JspException;

import net.woodstock.rockframework.web.jsp.taglib.BaseTag;

public class AttachTag extends BaseTag {

	private static final long	serialVersionUID	= 1L;

	private String				file;

	public AttachTag() {
		super();
	}

	@Override
	public void doTag() throws JspException, IOException {
		if ((this.getParent() == null) || (!(this.getParent() instanceof MessageTag))) {
			throw new JspException("File must appers inside a attach tag");
		}

		File f = new File(this.file);
		if (!f.exists()) {
			throw new JspException("Attachment not found " + this.file);
		}

		MessageTag parent = (MessageTag) this.getParent();
		parent.getAttach().add(f);
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
