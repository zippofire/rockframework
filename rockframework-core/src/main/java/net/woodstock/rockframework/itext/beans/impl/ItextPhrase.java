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
package net.woodstock.rockframework.itext.beans.impl;

import com.lowagie.text.Element;
import com.lowagie.text.Phrase;

public class ItextPhrase extends ItextObjectImpl {

	private static final long	serialVersionUID	= -1019639632153210263L;

	private String				text;

	public ItextPhrase() {
		super();
	}

	public Element getObject() {
		Phrase phrase = new Phrase(this.text);
		return phrase;
	}

	// Getters and Setters
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
