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
package br.net.woodstock.rockframework.web.config;

import br.net.woodstock.rockframework.config.AbstractMessage;

public final class WebMessage extends AbstractMessage {

	private static final String	WEB_MESSAGES	= "rockframework-web-messages";

	private static WebMessage	instance		= new WebMessage();

	private WebMessage() {
		super(WebMessage.WEB_MESSAGES);
	}

	public static WebMessage getInstance() {
		return WebMessage.instance;
	}

}
