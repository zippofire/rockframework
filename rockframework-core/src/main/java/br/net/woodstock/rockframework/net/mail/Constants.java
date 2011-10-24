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

public abstract class Constants {

	public static final String	IMAP_STORE			= "imap";

	public static final String	POP3_STORE			= "pop3";

	public static final String	SMTP_PROTOCOL		= "smtp";

	public static final int		DEFAULT_IMAP_PORT	= 143;

	public static final int		DEFAULT_POP3_PORT	= 110;

	public static final int		DEFAULT_SMTP_PORT	= 25;

	public static final long	DEFAULT_TIMEOUT		= 15000;

	private Constants() {
		//
	}

}
