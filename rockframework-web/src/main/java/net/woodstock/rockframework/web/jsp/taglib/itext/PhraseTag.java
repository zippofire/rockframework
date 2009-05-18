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
import java.io.StringWriter;

import javax.servlet.jsp.JspException;

import net.woodstock.rockframework.itext.beans.impl.ItextPhrase;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD;
import net.woodstock.rockframework.web.jsp.taglib.common.TLD.BodyContent;

@TLD(name = "phrase", type = BodyContent.SCRIPTLESS)
public class PhraseTag extends ITextTag {

	private ItextPhrase	phrase;

	@Override
	protected void doTagInternal() throws JspException, IOException {
		if (this.getContainer() == null) {
			throw new JspException("Phrase must appers inside a page");
		}

		this.phrase = new ItextPhrase();

		StringWriter writer = new StringWriter();

		this.getJspBody().invoke(writer);

		this.phrase.setText(writer.getBuffer().toString().trim());

		this.getContainer().add(this.phrase);
	}

}
