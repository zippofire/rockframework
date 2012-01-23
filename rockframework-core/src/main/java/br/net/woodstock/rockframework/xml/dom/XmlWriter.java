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
package br.net.woodstock.rockframework.xml.dom;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;

import org.w3c.dom.Node;

public abstract class XmlWriter {

	private static XmlWriter	instance	= new LSXmlWriter();

	public abstract void write(Node node, Writer writer, Charset encoding) throws IOException;

	public static XmlWriter getInstance() {
		return XmlWriter.instance;
	}

}
