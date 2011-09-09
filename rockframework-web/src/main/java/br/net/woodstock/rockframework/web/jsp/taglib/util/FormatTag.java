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
package br.net.woodstock.rockframework.web.jsp.taglib.util;

import java.io.IOException;
import java.io.Writer;

import br.net.woodstock.rockframework.util.StringFormater;
import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.web.jsp.taglib.AbstractTag;

public class FormatTag extends AbstractTag {

	private static final String	ERROR_VALUE	= "??ERROR??";

	private String				format;

	private Object				value;

	private char				character;

	public FormatTag() {
		super();
		this.character = StringFormater.DEFAULT_CHARACTER;
	}

	@Override
	public void doTag() throws IOException {
		if (this.value == null) {
			return;
		}
		String value = this.value.toString();
		StringFormater format = new StringFormater(this.format, this.character);
		Writer writer = this.getJspContext().getOut();
		String formated = "";

		try {
			formated = format.format(value);
		} catch (ArrayIndexOutOfBoundsException e) {
			WebLog.getInstance().getLog().warn("Error formating '" + value + "'  with mask '" + this.format + "'");
			formated = FormatTag.ERROR_VALUE;
		}

		writer.write(formated);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(final String format) {
		this.format = format;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public char getCharacter() {
		return this.character;
	}

	public void setCharacter(final char character) {
		this.character = character;
	}

}
