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

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class WrappedResult implements StrutsResult {

	private ActionForward	forward;

	public WrappedResult() {
		super();
	}

	public WrappedResult(ActionForward forward) {
		super();
		this.forward = forward;
	}

	public ActionForward getForward() {
		return this.forward;
	}

	public void setForward(ActionForward forward) {
		this.forward = forward;
	}

	public ActionForward getForward(ActionMapping mapping) {
		return this.forward;
	}

}
