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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import br.net.woodstock.rockframework.ExecutionException;

abstract class XmlHelper {

	private static DocumentBuilderFactory	factory;

	private static DocumentBuilder			builder;

	public static DocumentBuilderFactory getDocumentBuilderFactory() {
		return XmlHelper.factory;
	}

	public static DocumentBuilder getDocumentBuilder() {
		return XmlHelper.builder;
	}

	static {
		try {
			XmlHelper.factory = DocumentBuilderFactory.newInstance();
			XmlHelper.factory.setNamespaceAware(true);
			XmlHelper.builder = XmlHelper.factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ExecutionException(e);
		}
	}
}
