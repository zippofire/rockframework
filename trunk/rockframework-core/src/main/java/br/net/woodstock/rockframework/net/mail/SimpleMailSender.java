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

public class SimpleMailSender extends AbstractMailSender {

	public SimpleMailSender() {
		super();
	}

	public SimpleMailSender(final String smtpServer) {
		super(smtpServer);
	}

	public SimpleMailSender(final String smtpServer, final int smtpPort) {
		super(smtpServer, smtpPort);
	}

	public SimpleMailSender(final String smtpServer, final int smtpPort, final String user, final String password) {
		super(smtpServer, smtpPort, user, password);
	}

	@Override
	protected void doSend(final Mail mail, final MailSenderConfig config) {
		MailHelper.send(mail, config);
	}

}
