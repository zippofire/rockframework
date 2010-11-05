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

import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

abstract class MailHelper {

	public static final String	PROTOCOL			= "smtp";

	public static final int		DEFAULT_SMTP_PORT	= 25;

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

	public static MimeMessage toMimeMessage(final SimpleMail message, final Session session) throws MessagingException {
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
			}
		}

		mimeMessage.setContent(body);

		return mimeMessage;
	}

}
