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
package br.net.woodstock.rockframework.web.jsp.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import br.net.woodstock.rockframework.web.utils.RequestUtils;


public class RequestTimeListener implements ServletRequestListener {

	private static Map<Integer, RequestDebug>	requests	= new HashMap<Integer, RequestDebug>();

	public RequestTimeListener() {
		super();
	}

	@Override
	public void requestInitialized(final ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		String address = RequestUtils.getRequestAddress(request);
		String url = RequestUtils.getFullRequestUrl(request);
		RequestTimeListener.requests.put(Integer.valueOf(request.hashCode()), new RequestDebug(address, url));
	}

	@Override
	public void requestDestroyed(final ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		RequestDebug requestDebug = RequestTimeListener.requests.get(Integer.valueOf(request.hashCode()));
		requestDebug.setEnd(System.currentTimeMillis());
	}

	public static Collection<RequestDebug> getRequests() {
		return RequestTimeListener.requests.values();
	}

	public static class RequestDebug {

		private String	address;

		private String	url;

		private long	start;

		private long	end;

		public RequestDebug(final String address, final String url) {
			super();
			this.address = address;
			this.url = url;
			this.start = System.currentTimeMillis();
		}

		public String getAddress() {
			return this.address;
		}

		public void setAddress(final String address) {
			this.address = address;
		}

		public String getUrl() {
			return this.url;
		}

		public void setUrl(final String url) {
			this.url = url;
		}

		public long getStart() {
			return this.start;
		}

		public void setStart(final long start) {
			this.start = start;
		}

		public long getEnd() {
			return this.end;
		}

		public void setEnd(final long end) {
			this.end = end;
		}

		// Calc
		public long getTime() {
			return this.end - this.start;
		}

	}

}
