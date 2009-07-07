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
package net.woodstock.rockframework.web.jsp.taglib.itext;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTag;

import net.woodstock.rockframework.web.jsp.taglib.BaseTag;

abstract class ITextTag extends BaseTag {

	private ContainerTag	container;

	private boolean			checkParent;

	@Override
	public SimpleTag getParent() {
		return (SimpleTag) super.getParent();
	}

	@Override
	public final void doTag() throws JspException, IOException {
		super.doTag();
		this.init();
		this.doTagInternal();
	}

	protected void init() {
		this.container = null;
		this.checkParent = true;
	}

	protected ContainerTag getContainer() {
		if (this.checkParent) {
			this.checkParent = false;
			if ((this.getParent() != null) && (this.getParent() instanceof ContainerTag)) {
				this.container = (ContainerTag) this.getParent();
			} else {
				SimpleTag tmp = this.getParent();
				while (tmp != null) {
					if (tmp.getParent() instanceof ContainerTag) {
						this.container = (ContainerTag) tmp.getParent();
						break;
					}
					tmp = (SimpleTag) tmp.getParent();
				}
			}
		}
		return this.container;
	}

	protected PageContext getPageContext() {
		return (PageContext) this.getJspContext();
	}

	protected abstract void doTagInternal() throws JspException, IOException;

}
