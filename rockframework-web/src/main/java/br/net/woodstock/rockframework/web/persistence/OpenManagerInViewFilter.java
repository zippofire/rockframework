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
package br.net.woodstock.rockframework.web.persistence;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.net.woodstock.rockframework.persistence.orm.util.PersistenceHelper;
import br.net.woodstock.rockframework.web.filter.AbstractHttpFilter;

@SuppressWarnings("rawtypes")
public abstract class OpenManagerInViewFilter extends AbstractHttpFilter {

	private PersistenceHelper	persistenceHelper;

	public OpenManagerInViewFilter(final PersistenceHelper persistenceHelper) {
		super();
		this.persistenceHelper = persistenceHelper;
	}

	@Override
	public void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
		try {
			this.persistenceHelper.get();
			chain.doFilter(request, response);
		} catch (IOException e) {
			throw e;
		} catch (ServletException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			this.persistenceHelper.close();
		}
	}

}
