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
package br.net.woodstock.rockframework.web.struts2.spring;

import java.util.Map;

import br.net.woodstock.rockframework.domain.DomainException;
import br.net.woodstock.rockframework.domain.spring.ContextHelper;
import br.net.woodstock.rockframework.web.config.WebLog;

import com.opensymphony.xwork2.ObjectFactory;

@Deprecated
public class SpringObjectFactory extends ObjectFactory {

	private static final long	serialVersionUID	= -3223027211302424826L;

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object buildBean(final Class clazz, final Map extraContext) throws Exception {
		try {
			return ContextHelper.getInstance().getObject(clazz);
		} catch (DomainException e) {
			WebLog.getInstance().getLogger().debug("Class " + clazz + " not found in Spring Context");
			Object obj = super.buildBean(clazz, extraContext);
			super.injectInternalBeans(obj);
			return obj;
		}
	}

}
