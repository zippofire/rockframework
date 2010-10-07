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

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.woodstock.rockframework.utils.StringUtils;

public class SimpleMailSender {

	public static final String	PROTOCOL			= "smtp";

	public static final int		DEFAULT_SMTP_PORT	= 25;

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

	public void send(final SimpleMail message) throws MessagingException {
		this.send(message, this.getSession());
	}

	public void send(final SimpleMail[] messages) throws MessagingException {
		for (SimpleMail message : messages) {
			this.send(message, this.getSession());
		}
	}

	public void send(final Collection<SimpleMail> messages) throws MessagingException {
		for (SimpleMail message : messages) {
			this.send(message, this.getSession());
		}
	}

	private void send(final SimpleMail message, final Session session) throws MessagingException {
		MimeMessage mimeMessage = new MimeMessage(session);
		MimeMultipart body = new MimeMultipart();

		mimeMessage.setSubject(message.getSubject());
		mimeMessage.setFrom(new InternetAddress(message.getFrom()));

		if (message.getReplyTo().size() > 0) {
			InternetAddress[] address = new InternetAddress[message.getReplyTo().size()];
			int index = 0;
			for (String s : message.getReplyTo()) {
				address[index++] = new InternetAddress(s);
			}
			mimeMessage.setReplyTo(address);
		}

		if (message.getBcc().size() > 0) {
			for (String s : message.getBcc()) {
				mimeMessage.addRecipient(RecipientType.BCC, new InternetAddress(s));
			}
		}

		if (message.getCc().size() > 0) {
			for (String s : message.getCc()) {
				mimeMessage.addRecipient(RecipientType.CC, new InternetAddress(s));
			}
		}

		if (message.getTo().size() > 0) {
			for (String s : message.getTo()) {
				mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(s));
			}
		}

		if (message.isHtml()) {
			MimeBodyPart part = new MimeBodyPart();
			part.setContent(message.getText(), "text/html");
			body.addBodyPart(part);
		} else {
			MimeBodyPart part = new MimeBodyPart();
			part.setContent(message.getText(), "text/plain");
			body.addBodyPart(part);
		}

		if (message.getAttach().size() > 0) {
			for (Attachment a : message.getAttach()) {
				MimeBodyPart part = new MimeBodyPart();

				part.setContent(a.getContentAsString(), a.getContentType());
				part.setFileName(a.getName());
				body.addBodyPart(part);

				// DataSource source = a.getDataSource();
				// part.setDataHandler(new DataHandler(source));
				// part.setFileName(source.getName());
				// body.addBodyPart(part);
			}
		}

		mimeMessage.setContent(body);

		if (this.hasAuthentication()) {
			Transport transport = this.getTransport(session);
			mimeMessage.saveChanges();
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		} else {
			Transport.send(mimeMessage);
		}
	}

	private boolean hasAuthentication() {
		return !StringUtils.isEmpty(this.user);
	}

	private Session getSession() {
		Properties properties = new Properties();
		if (this.isDebug()) {
			properties.put("mail.debug", "true");
		}
		properties.put("mail.smtp.host", this.smtpServer);
		properties.put("mail.smtp.port", Integer.toString(this.smtpPort));
		properties.put("mail.smtp.timeout", "15000");
		properties.put("mail.smtp.connectiontimeout", "15000");
		if (!StringUtils.isEmpty(this.user)) {
			properties.put("mail.smtp.auth", "true");
		}
		return Session.getDefaultInstance(properties);
	}

	private Transport getTransport(final Session session) throws MessagingException {
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
