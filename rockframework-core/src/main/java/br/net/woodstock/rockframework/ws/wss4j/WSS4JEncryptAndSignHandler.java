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
package br.net.woodstock.rockframework.ws.wss4j;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.ws.security.WSConstants;

import br.net.woodstock.rockframework.util.Assert;

public class WSS4JEncryptAndSignHandler implements SOAPHandler<SOAPMessageContext> {

	private WSS4JConfig	config;

	public WSS4JEncryptAndSignHandler(final WSS4JConfig config) {
		super();
		Assert.notNull(config, "config");
		this.config = config;
	}

	@Override
	public boolean handleMessage(final SOAPMessageContext smc) {
		try {
			SOAPMessage message = smc.getMessage();
			Boolean isOutGoing = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

			if (isOutGoing.booleanValue()) {
				WSS4JHelper.timestamp(message);
				WSS4JHelper.encrypt(message, this.config);
				WSS4JHelper.sign(message, this.config);
			} else {
				WSS4JHelper.decryptAndCheckSign(message, this.config);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;

	}

	@Override
	public Set<QName> getHeaders() {
		Set<QName> headers = new HashSet<QName>();
		headers.add(new QName(WSConstants.WSSE_NS, WSConstants.WSSE_LN));
		headers.add(new QName(WSConstants.WSSE11_NS, WSConstants.WSSE_LN));
		headers.add(new QName(WSConstants.ENC_NS, WSConstants.ENC_DATA_LN));
		return headers;
	}

	@Override
	public boolean handleFault(final SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(final MessageContext context) {
		//
	}

}