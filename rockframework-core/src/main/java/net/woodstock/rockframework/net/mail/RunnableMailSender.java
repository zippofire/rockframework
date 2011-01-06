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

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class RunnableMailSender extends AbstractMailSender {

	public RunnableMailSender() {
		super();
	}

	public RunnableMailSender(final String smtpServer) {
		super(smtpServer);
	}

	public RunnableMailSender(final String smtpServer, final int smtpPort) {
		super(smtpServer, smtpPort);
	}

	public RunnableMailSender(final String smtpServer, final int smtpPort, final String user, final String password) {
		super(smtpServer, smtpPort, user, password);
	}

	@Override
	protected void send(final Mail message, final Session session) {
		try {
			MimeMessage mimeMessage = MailHelper.toMimeMessage(message, session);
			Transport transport = MailHelper.getTransport(session, this.getSmtpServer(), this.getSmtpPort(), this.getUser(), this.getPassword());
			Runnable runnable = new RunnableMailSenderHelper(mimeMessage, transport);
			Thread thread = new Thread(runnable);
			thread.start();
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}

	public static class RunnableMailSenderHelper implements Runnable {

		private MimeMessage	mimeMessage;

		private Transport	transport;

		public RunnableMailSenderHelper(final MimeMessage mimeMessage, final Transport transport) {
			super();
			this.mimeMessage = mimeMessage;
			this.transport = transport;
		}

		@Override
		public void run() {
			try {
				MailHelper.send(this.mimeMessage, this.transport);
			} catch (MessagingException e) {
				throw new MailException(e);
			}
		}

	}

}
