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
package br.net.woodstock.rockframework.net.mail;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.utils.IOUtils;

public abstract class MailHelper {

	private static final String	MULTIPART_TYPE		= "related";

	private static final String	HTML_CONTENT_TYPE	= "text/html";

	private static final String	PLAIN_CONTENT_TYPE	= "text/plain";

	private MailHelper() {
		//
	}

	public static void send(final Mail mail, final MailSenderConfig config) {
		Session session = null;
		Transport transport = null;
		try {
			session = MailHelper.getSMTPSession(config);
			transport = MailHelper.getTransport(session, config);
			MimeMessage mimeMessage = MailHelper.toMimeMessage(mail, session);
			if (transport != null) {
				mimeMessage.saveChanges();
				transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			} else {
				Transport.send(mimeMessage);
			}
		} catch (MessagingException e) {
			throw new MailException(e);
		} finally {
			if (transport != null) {
				if (transport.isConnected()) {
					try {
						transport.close();
					} catch (MessagingException e) {
						CoreLog.getInstance().getLog().log(Level.WARNING, e.getMessage(), e);
					}
				}
			}
		}
	}

	public static Mail[] read(final String folder, final MailReaderConfig config) {
		Store store = null;
		Folder mailFolder = null;
		try {
			store = MailHelper.getStore(config);
			if (ConditionUtils.isNotEmpty(folder)) {
				mailFolder = store.getDefaultFolder();
			} else {
				mailFolder = store.getFolder(folder);
			}

			mailFolder.open(Folder.READ_ONLY);

			Message[] messages = mailFolder.getMessages();

			Mail[] mails = new Mail[messages.length];

			for (int i = 0; i < messages.length; i++) {
				mails[i] = MailHelper.toMail(messages[i]);
			}

			return mails;
		} catch (MessagingException e) {
			throw new MailException(e);
		} catch (IOException e) {
			throw new MailException(e);
		} finally {
			if (mailFolder != null) {
				if (mailFolder.isOpen()) {
					try {
						mailFolder.close(false);
					} catch (MessagingException e) {
						CoreLog.getInstance().getLog().log(Level.WARNING, e.getMessage(), e);
					}
				}
			}
			if (store != null) {
				if (store.isConnected()) {
					try {
						store.close();
					} catch (MessagingException e) {
						CoreLog.getInstance().getLog().log(Level.WARNING, e.getMessage(), e);
					}
				}
			}
		}
	}

	private static MimeMessage toMimeMessage(final Mail message, final Session session) throws MessagingException {
		MimeMessage mimeMessage = new MimeMessage(session);
		MimeMultipart multipart = new MimeMultipart(MailHelper.MULTIPART_TYPE);

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
			part.setContent(message.getText(), MailHelper.HTML_CONTENT_TYPE);
			multipart.addBodyPart(part);

		} else {
			MimeBodyPart part = new MimeBodyPart();
			part.setContent(message.getText(), MailHelper.PLAIN_CONTENT_TYPE);
			multipart.addBodyPart(part);
		}

		if (message.getAttach().size() > 0) {
			for (Attachment a : message.getAttach()) {
				if (a.getDisposition() == Disposition.INLINE) {
					MimeBodyPart part = new MimeBodyPart();
					part.setContent(a.getContentAsString(), a.getContentType());
					part.setContentID(MailHelper.getContentId(a));
					part.setDisposition(Part.INLINE);
					multipart.addBodyPart(part);
				} else {
					MimeBodyPart part = new MimeBodyPart();
					part.setContent(a.getContentAsString(), a.getContentType());
					part.setFileName(a.getName());
					part.setDisposition(Part.ATTACHMENT);
					multipart.addBodyPart(part);
				}
			}
		}

		mimeMessage.setContent(multipart);

		return mimeMessage;
	}

	private static Mail toMail(final Message message) throws MessagingException, IOException {
		SimpleMail mail = new SimpleMail();
		mail.setFrom(((InternetAddress) message.getFrom()[0]).getAddress());
		Address[] bccs = message.getRecipients(RecipientType.BCC);
		Address[] ccs = message.getRecipients(RecipientType.CC);
		Address[] tos = message.getRecipients(RecipientType.TO);

		if (ConditionUtils.isNotEmpty(bccs)) {
			for (Address address : bccs) {
				mail.getBcc().add(((InternetAddress) address).getAddress());
			}
		}

		if (ConditionUtils.isNotEmpty(ccs)) {
			for (Address address : ccs) {
				mail.getCc().add(((InternetAddress) address).getAddress());
			}
		}

		if (ConditionUtils.isNotEmpty(tos)) {
			for (Address address : tos) {
				mail.getTo().add(((InternetAddress) address).getAddress());
			}
		}

		mail.setSubject(message.getSubject());

		if (message instanceof MimeMessage) {
			MimeMessage mimeMessage = (MimeMessage) message;
			Object content = mimeMessage.getContent();
			Part part = message;

			if (content instanceof Multipart) {
				Multipart multipart = (Multipart) content;
				part = multipart.getBodyPart(0);

				if (multipart.getCount() > 1) {
					for (int i = 1; i < multipart.getCount(); i++) {
						Part subPart = multipart.getBodyPart(i);
						byte[] bytes = IOUtils.toByteArray(subPart.getInputStream());
						String name = subPart.getFileName();
						Attachment attachment = new ByteArrayAttachment(name, subPart.getContentType(), bytes);
						mail.getAttach().add(attachment);
					}
				}
			}

			if (part.getContentType().equals(MailHelper.HTML_CONTENT_TYPE)) {
				mail.setHtml(true);
			}

			String body = IOUtils.toString(part.getInputStream());
			mail.setText(body);
		}
		return mail;
	}

	private static Transport getTransport(final Session session, final MailSenderConfig mailSenderConfig) throws MessagingException {
		if (ConditionUtils.isEmpty(mailSenderConfig.getUser())) {
			return null;
		}

		Transport transport = session.getTransport(Constants.SMTP_PROTOCOL);
		transport.connect(mailSenderConfig.getSmtpServer(), mailSenderConfig.getSmtpPort(), mailSenderConfig.getUser(), mailSenderConfig.getPassword());
		return transport;
	}

	private static Store getStore(final MailReaderConfig config) throws MessagingException {
		Session session = Session.getDefaultInstance(new Properties());
		Store store = session.getStore(config.getType().getStore());
		store.connect(config.getServer(), config.getPort(), config.getUser(), config.getPassword());
		return store;
	}

	private static Session getSMTPSession(final MailSenderConfig mailSenderConfig) {
		Properties properties = new Properties();
		if (mailSenderConfig instanceof AbstractMailSender) {
			AbstractMailSender asm = (AbstractMailSender) mailSenderConfig;
			properties.put("mail.debug", Boolean.toString(asm.isDebug()));
		}
		properties.put("mail.smtp.host", mailSenderConfig.getSmtpServer());
		properties.put("mail.smtp.port", Integer.toString(mailSenderConfig.getSmtpPort()));
		properties.put("mail.smtp.timeout", Long.toString(Constants.DEFAULT_TIMEOUT));
		properties.put("mail.smtp.connectiontimeout", Long.toString(Constants.DEFAULT_TIMEOUT));
		if (ConditionUtils.isNotEmpty(mailSenderConfig.getUser())) {
			properties.put("mail.smtp.auth", "true");
		}
		return Session.getDefaultInstance(properties);
	}

	private static String getContentId(final Attachment a) {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(a.getName());
		builder.append(">");
		return builder.toString();
	}

}
