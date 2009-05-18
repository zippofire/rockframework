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

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.jsp.JspException;

import net.woodstock.rockframework.web.jsp.taglib.common.CommonTag;

public class ReplyToTag extends CommonTag {

	private static final long	serialVersionUID	= 1L;

	private String				address;

	public ReplyToTag() {
		super();
	}

	@Override
	public void doTag() throws JspException, IOException {
		if ((this.getParent() == null) || (!(this.getParent() instanceof MessageTag))) {
			throw new JspException("ReplyTo must appers inside a email tag");
		}
		try {
			new InternetAddress(this.address);
		} catch (AddressException e) {
			throw new JspException(e);
		}
		MessageTag parent = (MessageTag) this.getParent();
		parent.getReplyTo().add(this.address);
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
