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
package net.woodstock.rockframework.web.jsp.taglib.util;

import java.io.IOException;
import java.io.Writer;

import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.jsp.taglib.BaseTag;

public class FormatTag extends BaseTag {

	private String	format;

	private Object	value;

	private String	character;

	public FormatTag() {
		super();
		this.character = Character.toString(StringUtils.DEFAULT_FORMAT_CHAR);
	}

	@Override
	public void doTag() throws IOException {
		if (this.value == null) {
			return;
		}
		String value = this.value.toString();
		char character = this.character.charAt(0);
		Writer writer = this.getJspContext().getOut();
		String formated = StringUtils.BLANK;

		try {
			formated = StringUtils.format(this.format, value, character);
		} catch (ArrayIndexOutOfBoundsException e) {
			this.getLogger().warn(e.getMessage(), e);
			formated = "??ERROR??";
		}

		writer.write(formated);
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getCharacter() {
		return this.character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

}
