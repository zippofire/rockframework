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
import java.util.LinkedHashSet;

import net.woodstock.rockframework.utils.StringUtils;

public class SimpleMail implements Serializable {

	private static final long	serialVersionUID	= -7704634381340126487L;

	private String				text				= StringUtils.BLANK;

	private String				subject				= StringUtils.BLANK;

	private String				from				= StringUtils.BLANK;

	private boolean				html				= false;

	private Collection<String>	replyTo				= new LinkedHashSet<String>();

	private Collection<String>	to					= new LinkedHashSet<String>();

	private Collection<String>	bcc					= new LinkedHashSet<String>();

	private Collection<String>	cc					= new LinkedHashSet<String>();

	private Collection<File>	attach				= new LinkedHashSet<File>();

	public SimpleMail() {
		super();
	}

	public void addAttach(final File attach) {
		this.attach.add(attach);
	}

	public Collection<File> getAttach() {
		return this.attach;
	}

	public void setAttach(final Collection<File> attach) {
		this.attach = attach;
	}

	public void addBcc(final String bcc) {
		this.bcc.add(bcc);
	}

	public Collection<String> getBcc() {
		return this.bcc;
	}

	public void setBcc(final Collection<String> bcc) {
		this.bcc = bcc;
	}

	public void addCc(final String cc) {
		this.cc.add(cc);
	}

	public Collection<String> getCc() {
		return this.cc;
	}

	public void setCc(final Collection<String> cc) {
		this.cc = cc;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(final String from) {
		this.from = from;
	}

	public boolean isHtml() {
		return this.html;
	}

	public void setHtml(final boolean html) {
		this.html = html;
	}

	public void addReplyTo(final String replyTo) {
		this.replyTo.add(replyTo);
	}

	public Collection<String> getReplyTo() {
		return this.replyTo;
	}

	public void setReplyTo(final Collection<String> replyTo) {
		this.replyTo = replyTo;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public void addTo(final String to) {
		this.to.add(to);
	}

	public Collection<String> getTo() {
		return this.to;
	}

	public void setTo(final Collection<String> to) {
		this.to = to;
	}

}
