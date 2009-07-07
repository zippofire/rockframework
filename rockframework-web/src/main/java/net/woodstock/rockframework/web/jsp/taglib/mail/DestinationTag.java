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

import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.BaseTag;

public class DestinationTag extends BaseTag {

	private static final long	serialVersionUID	= 1L;

	private String				type;

	private String				address;

	public DestinationTag() {
		super();
	}

	@Override
	public void doTag() throws JspException, IOException {
		if ((this.getParent() == null) || (!(this.getParent() instanceof MessageTag))) {
			throw new JspException("Destination must appers inside a mail tag");
		}
		try {
			new InternetAddress(this.address);
		} catch (AddressException e) {
			throw new JspException(e);
		}
		MessageTag parent = (MessageTag) this.getParent();
		if (!StringUtils.isEmpty(this.type)) {
			if (this.type.equalsIgnoreCase("BCC")) {
				parent.getBcc().add(this.address);
			} else if (this.type.equalsIgnoreCase("CC")) {
				parent.getCc().add(this.address);
			} else if (this.type.equalsIgnoreCase("TO")) {
				parent.getTo().add(this.address);
			} else {
				throw new JspException("Invalid destination type: " + this.type);
			}
		} else {
			parent.getTo().add(this.address);
		}
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
