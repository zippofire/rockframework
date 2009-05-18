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
import javax.servlet.jsp.tagext.SimpleTag;

import net.woodstock.rockframework.itext.beans.ItextObject;
import net.woodstock.rockframework.itext.beans.ItextPage;
import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD.BodyContent;

@TLD(name = "page", type = BodyContent.SCRIPTLESS)
public class PageTag extends ContainerTag {

	private ItextPage	page;

	@Override
	protected void doTagInternal() throws JspException, IOException {
		SimpleTag tag = this.getParent();
		if (!(tag instanceof DocumentTag)) {
			throw new JspException("Page must appers inside a document");
		}

		this.page = new ItextPage();

		this.getJspBody().invoke(null);

		((DocumentTag) tag).addPage(this.page);
	}

	@Override
	public void add(ItextObject item) {
		SysLogger.getLogger().debug("Adding a '" + item.getClass() + "' into page");
		this.page.addItem(item);
	}

}
