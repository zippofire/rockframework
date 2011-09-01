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

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.net.woodstock.rockframework.utils.ConditionUtils;


abstract class MailHelper {

	private static final String	MULTIPART_TYPE		= "related";

	private static final String	HTML_CONTENT_TYPE	= "text/html";

	private static final String	PLAIN_CONTENT_TYPE	= "text/plain";

	private MailHelper() {
		//
	}

	public static void send(final MimeMessage mimeMessage, final Transport transport) throws MessagingException {
		if (transport != null) {
			mimeMessage.saveChanges();
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		} else {
			Transport.send(mimeMessage);
		}
	}

	public static MimeMessage toMimeMessage(final Mail message, final Session session) throws MessagingException {
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

	public static Transport getTransport(final Session session, final String smtpServer, final int port, final String user, final String password) throws MessagingException {
		if (ConditionUtils.isEmpty(user)) {
			return null;
		}

		Transport transport = session.getTransport(AbstractMailSender.PROTOCOL);
		transport.connect(smtpServer, port, user, password);
		return transport;
	}

	private static String getContentId(final Attachment a) {
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(a.getName());
		builder.append(">");
		return builder.toString();
	}

}
