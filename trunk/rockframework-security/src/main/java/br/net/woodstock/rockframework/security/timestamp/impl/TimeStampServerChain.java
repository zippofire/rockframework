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
package br.net.woodstock.rockframework.security.timestamp.impl;

import br.net.woodstock.rockframework.security.timestamp.TimeStampException;
import br.net.woodstock.rockframework.security.timestamp.TimeStampServer;
import br.net.woodstock.rockframework.util.Assert;

public class TimeStampServerChain implements TimeStampServer {

	private TimeStampServer[]	chain;

	public TimeStampServerChain(final TimeStampServer[] chain) {
		super();
		Assert.notEmpty(chain, "chain");
		this.chain = chain;
	}

	@Override
	public byte[] getTimeStamp(final byte[] request) {
		Exception error = null;
		byte[] ts = null;
		boolean ok = false;
		for (TimeStampServer server : this.chain) {
			try {
				ts = server.getTimeStamp(request);
				ok = true;
				break;
			} catch (Exception e) {
				error = e;
			}
		}
		if (!ok) {
			if (error != null) {
				throw new TimeStampException(error);
			}
		}
		return ts;
	}

}
