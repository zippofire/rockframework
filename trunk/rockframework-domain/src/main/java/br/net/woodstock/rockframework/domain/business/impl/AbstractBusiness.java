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
package br.net.woodstock.rockframework.domain.business.impl;

import br.net.woodstock.rockframework.domain.business.Business;
import br.net.woodstock.rockframework.domain.config.DomainMessage;

public abstract class AbstractBusiness implements Business {

	private static final long	serialVersionUID	= -6487843296854967777L;

	public static final String	OK_MESSAGE			= "OK";

	public AbstractBusiness() {
		super();
	}

	protected String getMessage(final String key, final Object... args) {
		return DomainMessage.getInstance().getMessage(key, args);
	}

}
