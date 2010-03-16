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
package net.woodstock.rockframework.web.struts.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.utils.StringUtils;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

public class ForwardResultBuilder implements ResultBuilder {

	private ActionMapping		mapping;

	private String				name;

	private Map<String, Object>	parameters;

	public ForwardResultBuilder(final ActionMapping mapping) {
		super();
		if (mapping == null) {
			throw new IllegalArgumentException("Mapping must be not null");
		}
		this.mapping = mapping;
		this.parameters = new HashMap<String, Object>();
	}

	public ForwardResultBuilder name(final String name) {
		this.name = name;
		return this;
	}

	public ForwardResultBuilder parameter(final String name, final Object value) {
		this.parameters.put(name, value);
		return this;
	}

	public ActionForward build() {
		if (StringUtils.isEmpty(this.name)) {
			throw new IllegalStateException("Name must be not empty");
		}

		ActionRedirect redirect = new ActionRedirect(this.mapping.findForwardConfig(this.name));
		if ((this.parameters != null) && (this.parameters.size() > 0)) {
			for (Entry<String, Object> entry : this.parameters.entrySet()) {
				String k = entry.getKey();
				String v = "";
				if (entry.getValue() != null) {
					v = entry.getValue().toString();
				}
				redirect.addParameter(k, v);
			}
		}

		return redirect;
	}

}
