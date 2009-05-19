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
package net.woodstock.rockframework.itext.impl;

import com.lowagie.text.Element;

public class Phrase extends ObjectBase {

	private static final long	serialVersionUID	= -1019639632153210263L;

	private String				text;

	public Phrase() {
		super();
	}

	public Element getObject() {
		com.lowagie.text.Phrase phrase = new com.lowagie.text.Phrase(this.text);
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
