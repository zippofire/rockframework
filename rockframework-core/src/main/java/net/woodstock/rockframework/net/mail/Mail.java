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

import java.io.File;
import java.io.Serializable;
import java.util.Collection;

import javax.mail.MessagingException;

public class Mail implements Serializable {

	private static final long	serialVersionUID	= -735854083683211982L;

	public static final int		DEFAULT_SMTP_PORT	= SimpleMailSender.DEFAULT_SMTP_PORT;

	private SimpleMailSender	mailSender;

	private SimpleMail			mail;

	public Mail() {
		super();
		this.mailSender = new SimpleMailSender();
		this.mail = new SimpleMail();
	}

	public void send() throws MessagingException {
		this.mailSender.send(this.mail);
	}

	public void addAttach(File attach) {
		this.mail.addAttach(attach);
	}

	public Collection<File> getAttach() {
		return this.mail.getAttach();
	}

	public void setAttach(Collection<File> attach) {
		this.mail.setAttach(attach);
	}

	public void addBcc(String bcc) {
		this.mail.addBcc(bcc);
	}

	public Collection<String> getBcc() {
		return this.mail.getBcc();
	}

	public void setBcc(Collection<String> bcc) {
		this.mail.setBcc(bcc);
	}

	public void addCc(String cc) {
		this.mail.addCc(cc);
	}

	public Collection<String> getCc() {
		return this.mail.getCc();
	}

	public void setCc(Collection<String> cc) {
		this.mail.setCc(cc);
	}

	public String getFrom() {
		return this.mail.getFrom();
	}

	public void setFrom(String from) {
		this.mail.setFrom(from);
	}

	public boolean isHtml() {
		return this.mail.isHtml();
	}

	public void setHtml(boolean html) {
		this.mail.setHtml(html);
	}

	public String getPassword() {
		return this.mailSender.getPassword();
	}

	public void setPassword(String password) {
		this.mailSender.setPassword(password);
	}

	public void addReplyTo(String replyTo) {
		this.addReplyTo(replyTo);
	}

	public Collection<String> getReplyTo() {
		return this.mail.getReplyTo();
	}

	public void setReplyTo(Collection<String> replyTo) {
		this.mail.setReplyTo(replyTo);
	}

	public int getSmtpPort() {
		return this.mailSender.getSmtpPort();
	}

	public void setSmtpPort(int smtpPort) {
		this.mailSender.setSmtpPort(smtpPort);
	}

	public String getSmtpServer() {
		return this.mailSender.getSmtpServer();
	}

	public void setSmtpServer(String smtpServer) {
		this.mailSender.setSmtpServer(smtpServer);
	}

	public String getSubject() {
		return this.mail.getSubject();
	}

	public void setSubject(String subject) {
		this.mail.setSubject(subject);
	}

	public String getText() {
		return this.mail.getText();
	}

	public void setText(String text) {
		this.mail.setText(text);
	}

	public void addTo(String to) {
		this.mail.addTo(to);
	}

	public Collection<String> getTo() {
		return this.mail.getTo();
	}

	public void setTo(Collection<String> to) {
		this.mail.setTo(to);
	}

	public String getUser() {
		return this.mailSender.getUser();
	}

	public void setUser(String user) {
		this.mailSender.setUser(user);
	}

}
