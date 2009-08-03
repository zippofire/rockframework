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
package net.woodstock.rockframework.web.struts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import net.woodstock.rockframework.utils.StringUtils;

public class ForwardResult implements StrutsResult {

	private String				name;

	private Map<String, Object>	parameters;

	public ForwardResult(String name) {
		super();
		this.name = name;
		this.parameters = new HashMap<String, Object>();
	}

	public String getName() {
		return this.name;
	}

	public Map<String, Object> getParameters() {
		return this.parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public void addParameters(String key, Object value) {
		this.parameters.put(key, value);
	}

	public ActionForward getForward(ActionMapping mapping) {
		ActionRedirect redirect = new ActionRedirect(mapping.findForwardConfig(this.name));
		for (Entry<String, Object> entry : this.parameters.entrySet()) {
			String k = entry.getKey();
			String v = StringUtils.BLANK;
			if (entry.getValue() != null) {
				v = entry.getValue().toString();
			}
			redirect.addParameter(k, v);
		}
		return redirect;
	}

}
