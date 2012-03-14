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
package br.net.woodstock.rockframework.web.ws.wss4j;

import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

@SuppressWarnings("rawtypes")
public class WSS4JHandlerResolver implements HandlerResolver {

	private List<Handler>	handlers;

	public WSS4JHandlerResolver(final List<Handler> handlers) {
		super();
		this.handlers = handlers;
	}

	@Override
	public List<Handler> getHandlerChain(final PortInfo portInfo) {
		return this.handlers;
	}

}
