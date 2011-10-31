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
package br.net.woodstock.rockframework.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class WSSecurityUsernameTokenHandlerResolver implements HandlerResolver {

	private String	username;

	private String	password;

	private boolean	digest;

	public WSSecurityUsernameTokenHandlerResolver(final String username, final String password, final boolean digest) {
		super();
		this.username = username;
		this.password = password;
		this.digest = digest;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Handler> getHandlerChain(final PortInfo portInfo) {
		List<Handler> handlerChain = new ArrayList<Handler>();
		WSSecurityUsernameTokenHandler handler = new WSSecurityUsernameTokenHandler(this.username, this.password, this.digest);
		handlerChain.add(handler);
		return handlerChain;
	}
}
