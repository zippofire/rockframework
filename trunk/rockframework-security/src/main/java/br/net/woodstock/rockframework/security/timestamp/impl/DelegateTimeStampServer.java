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

import br.net.woodstock.rockframework.security.timestamp.TimeStampProcessor;
import br.net.woodstock.rockframework.security.timestamp.TimeStampServer;
import br.net.woodstock.rockframework.util.Assert;

public class DelegateTimeStampServer implements TimeStampServer {

	private TimeStampProcessor	processor;

	public DelegateTimeStampServer(final TimeStampProcessor processor) {
		super();
		Assert.notNull(processor, "processor");
		this.processor = processor;
	}

	@Override
	public byte[] getTimeStamp(final byte[] request) {
		return this.processor.getBinaryResponse(request);
	}

}
