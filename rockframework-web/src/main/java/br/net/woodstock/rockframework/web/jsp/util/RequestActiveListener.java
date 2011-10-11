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

public class RequestActiveListener implements ServletRequestListener {

	private static Map<Integer, String>	requests	= new HashMap<Integer, String>();

	@Override
	public void requestInitialized(final ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		String address = RequestUtils.getRequestAddress(request);
		String path = RequestUtils.getFullRequestUrl(request);
		String s = address + "\t" + path;
		RequestActiveListener.requests.put(Integer.valueOf(request.hashCode()), s);
	}

	@Override
	public void requestDestroyed(final ServletRequestEvent event) {
		HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
		RequestActiveListener.requests.remove(Integer.valueOf(request.hashCode()));
	}

	public static Collection<String> getRequests() {
		return RequestActiveListener.requests.values();
	}

}
