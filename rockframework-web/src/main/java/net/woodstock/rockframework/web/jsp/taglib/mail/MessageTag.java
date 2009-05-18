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
import java.util.LinkedHashSet;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.jsp.JspException;

import net.woodstock.rockframework.net.mail.Mail;
import net.woodstock.rockframework.web.jsp.taglib.common.CommonTag;

public class MessageTag extends CommonTag {

	private static final long	serialVersionUID	= 1L;

	private String				server				= "";

	private String				port				= "25";

	private String				from				= "";

	private String				subject				= "";

	private boolean				html				= false;

	private String				text				= "";

	private Set<String>			replyTo				= new LinkedHashSet<String>();

	private Set<String>			to					= new LinkedHashSet<String>();

	private Set<String>			bcc					= new LinkedHashSet<String>();

	private Set<String>			cc					= new LinkedHashSet<String>();

	private Set<File>			attach				= new LinkedHashSet<File>();

	public MessageTag() {
		super();
	}

	@Override
	public void doTag() throws JspException, IOException {
		try {
			Integer.parseInt(this.port);
		} catch (NumberFormatException e) {
			throw new JspException(e);
		}

		this.getJspBody().invoke(null);

		Mail mail = new Mail();
		mail.setFrom(this.from);
		mail.setSmtpPort(Integer.parseInt(this.port));
		mail.setSmtpServer(this.server);
		mail.setSubject(this.subject);
		mail.setText(this.text);
		mail.setHtml(this.html);

		for (File f : this.attach) {
			mail.addAttach(f);
		}
		for (String s : this.bcc) {
			mail.addBcc(s);
		}
		for (String s : this.cc) {
			mail.addCc(s);
		}
		for (String s : this.replyTo) {
			mail.addReplyTo(s);
		}
		for (String s : this.to) {
			mail.addTo(s);
		}

		try {
			mail.send();
		} catch (AddressException e) {
			throw new JspException(e);
		} catch (MessagingException e) {
			throw new JspException(e);
		}
	}

	public Set<File> getAttach() {
		return this.attach;
	}

	public void setAttach(Set<File> attach) {
		this.attach = attach;
	}

	public Set<String> getBcc() {
		return this.bcc;
	}

	public void setBcc(Set<String> bcc) {
		this.bcc = bcc;
	}

	public Set<String> getCc() {
		return this.cc;
	}

	public void setCc(Set<String> cc) {
		this.cc = cc;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public boolean getHtml() {
		return this.html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public Set<String> getReplyTo() {
		return this.replyTo;
	}

	public void setReplyTo(Set<String> replyTo) {
		this.replyTo = replyTo;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Set<String> getTo() {
		return this.to;
	}

	public void setTo(Set<String> to) {
		this.to = to;
	}

}
