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
	protected void doSend(final Mail mail, final MailSenderConfig config) {
		Runnable runnable = new RunnableMailSenderHelper(mail, config);
		Thread thread = new Thread(runnable);
		thread.start();
	}

	public static class RunnableMailSenderHelper implements Runnable {

		private Mail				mail;

		private MailSenderConfig	config;

		public RunnableMailSenderHelper(final Mail mail, final MailSenderConfig config) {
			super();
			this.mail = mail;
			this.config = config;
		}

		@Override
		public void run() {
			MailHelper.send(this.mail, this.config);
		}

	}

}
