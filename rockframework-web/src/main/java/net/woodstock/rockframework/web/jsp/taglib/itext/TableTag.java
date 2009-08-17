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

import net.woodstock.rockframework.itext.Object;
import net.woodstock.rockframework.itext.impl.Cell;
import net.woodstock.rockframework.itext.impl.Table;
import net.woodstock.rockframework.itext.types.Alignment;
import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.creator.BodyContent;
import net.woodstock.rockframework.web.jsp.taglib.creator.TLD;
import net.woodstock.rockframework.web.jsp.taglib.creator.TLDAttribute;

@TLD(name = "table", content = BodyContent.SCRIPTLESS)
public class TableTag extends ContainerTag {

	@TLDAttribute(required = false, rtexprvalue = true)
	private String	alignment;

	@TLDAttribute(required = true, rtexprvalue = true)
	private String	columns;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String	spacingAfter;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String	spacingBefore;

	@TLDAttribute(required = false, rtexprvalue = true)
	private String	width;

	private Table	table;

	@Override
	protected void doTagInternal() throws JspException, IOException {
		if (this.getContainer() == null) {
			throw new JspException("Table must appers inside a page");
		}

		this.table = new Table();

		if (!StringUtils.isEmpty(this.alignment)) {
			this.table.setAlignment(Alignment.valueOf(this.alignment));
		}

		if (!StringUtils.isEmpty(this.columns)) {
			this.table.setColumns(Integer.parseInt(this.columns));
		}

		if (!StringUtils.isEmpty(this.spacingAfter)) {
			this.table.setSpacingAfter(Float.parseFloat(this.spacingAfter));
		}

		if (!StringUtils.isEmpty(this.spacingBefore)) {
			this.table.setSpacingBefore(Float.parseFloat(this.spacingBefore));
		}

		if (!StringUtils.isEmpty(this.width)) {
			this.table.setTotalWidth(Float.parseFloat(this.width));

		}

		this.getJspBody().invoke(null);
		this.getContainer().add(this.table);
	}

	@Override
	public void add(Object item) {
		if (!(item instanceof Cell)) {
			throw new RuntimeException("Cold not insert a " + item.getClass() + " into table");
		}
		SysLogger.getLogger().debug("Inserting a cell with content '" + ((Cell) item).getText() + "'");
		this.table.addCell((Cell) item);
	}

	// Setters and getters
	public String getAlignment() {
		return this.alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getColumns() {
		return this.columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getSpacingAfter() {
		return this.spacingAfter;
	}

	public void setSpacingAfter(String spacingAfter) {
		this.spacingAfter = spacingAfter;
	}

	public String getSpacingBefore() {
		return this.spacingBefore;
	}

	public void setSpacingBefore(String spacingBefore) {
		this.spacingBefore = spacingBefore;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
