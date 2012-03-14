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
package br.net.woodstock.rockframework.web.struts.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import br.net.woodstock.rockframework.util.Assert;

public class ForwardResultBuilder implements ResultBuilder {

	private ActionMapping		mapping;

	private String				name;

	private Map<String, Object>	parameters;

	public ForwardResultBuilder(final ActionMapping mapping) {
		super();
		Assert.notNull(mapping, "mapping");
		this.mapping = mapping;
		this.parameters = new HashMap<String, Object>();
	}

	public ForwardResultBuilder name(final String name) {
		Assert.notEmpty(this.name, "name");
		this.name = name;
		return this;
	}

	public ForwardResultBuilder parameter(final String name, final Object value) {
		Assert.notEmpty(this.name, "name");
		this.parameters.put(name, value);
		return this;
	}

	@Override
	public ActionForward build() {
		Assert.notEmpty(this.name, "name");

		ActionRedirect redirect = new ActionRedirect(this.mapping.findForwardConfig(this.name));
		if ((this.parameters != null) && (this.parameters.size() > 0)) {
			for (Entry<String, Object> entry : this.parameters.entrySet()) {
				String k = entry.getKey();
				Object o = entry.getValue();
				String v = "";
				if (o != null) {
					v = o.toString();
				}
				redirect.addParameter(k, v);
			}
		}

		return redirect;
	}

}
