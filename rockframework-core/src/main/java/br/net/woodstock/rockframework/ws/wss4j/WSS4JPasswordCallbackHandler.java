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

import java.io.IOException;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

import br.net.woodstock.rockframework.util.Assert;

public class WSS4JPasswordCallbackHandler implements CallbackHandler {

	private Map<String, String>	map;

	public WSS4JPasswordCallbackHandler(final Map<String, String> map) {
		super();
		Assert.notNull(map, "map");
		this.map = map;
	}

	@Override
	public void handle(final Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		for (Callback cb : callbacks) {
			if (cb instanceof WSPasswordCallback) {
				WSPasswordCallback pc = (WSPasswordCallback) cb;
				String identifier = pc.getIdentifier();
				if (identifier != null) {
					if (this.map.containsKey(identifier)) {
						pc.setPassword(this.map.get(identifier));
					}
				}
			}
		}
	}

}
