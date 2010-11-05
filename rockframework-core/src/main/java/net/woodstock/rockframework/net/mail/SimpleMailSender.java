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
package net.woodstock.rockframework.net.mail;

import java.util.Collection;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import net.woodstock.rockframework.utils.ConditionUtils;

public class SimpleMailSender {

	public static final String	PROTOCOL			= "smtp";

	public static final int		DEFAULT_SMTP_PORT	= 25;

	private static final long	TIMEOUT				= 15000;

	private String				smtpServer;

	private int					smtpPort			= SimpleMailSender.DEFAULT_SMTP_PORT;

	private String				user;

	private String				password;

	private boolean				debug;

	public SimpleMailSender() {
		super();
	}

	public SimpleMailSender(final String smtpServer) {
		super();
		this.smtpServer = smtpServer;
	}

	public SimpleMailSender(final String smtpServer, final int smtpPort) {
		super();
		this.smtpServer = smtpServer;
		this.smtpPort = smtpPort;
	}

	public SimpleMailSender(final String smtpServer, final int smtpPort, final String user, final String password) {
		super();
		this.smtpServer = smtpServer;
		this.smtpPort = smtpPort;
		this.user = user;
		this.password = password;
	}

	public void send(final SimpleMail message) {
		this.send(message, this.getSession());
	}

	public void send(final SimpleMail[] messages) {
		for (SimpleMail message : messages) {
			this.send(message, this.getSession());
		}
	}

	public void send(final Collection<SimpleMail> messages) {
		for (SimpleMail message : messages) {
			this.send(message, this.getSession());
		}
	}

	private void send(final SimpleMail message, final Session session) {
		try {
			MimeMessage mimeMessage = MailHelper.toMimeMessage(message, session);
			Transport transport = this.getTransport(session);
			this.send(mimeMessage, transport);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	protected void send(final MimeMessage mimeMessage, final Transport transport) {
		try {
			MailHelper.send(mimeMessage, transport);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	private Session getSession() {
		Properties properties = new Properties();
		properties.put("mail.debug", Boolean.toString(this.debug));
		properties.put("mail.smtp.host", this.smtpServer);
		properties.put("mail.smtp.port", Integer.toString(this.smtpPort));
		properties.put("mail.smtp.timeout", Long.toString(SimpleMailSender.TIMEOUT));
		properties.put("mail.smtp.connectiontimeout", Long.toString(SimpleMailSender.TIMEOUT));
		if (ConditionUtils.isNotEmpty(this.user)) {
			properties.put("mail.smtp.auth", "true");
		}
		return Session.getDefaultInstance(properties);
	}

	private Transport getTransport(final Session session) throws MessagingException {
		if (ConditionUtils.isEmpty(this.user)) {
			return null;
		}

		Transport transport = session.getTransport(SimpleMailSender.PROTOCOL);
		transport.connect(this.smtpServer, this.user, this.password);
		return transport;
	}

	// Getters and Setters
	public String getSmtpServer() {
		return this.smtpServer;
	}

	public void setSmtpServer(final String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public int getSmtpPort() {
		return this.smtpPort;
	}

	public void setSmtpPort(final int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public boolean isDebug() {
		return this.debug;
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

}
