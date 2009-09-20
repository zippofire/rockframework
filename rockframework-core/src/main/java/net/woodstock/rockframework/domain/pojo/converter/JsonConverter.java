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
package net.woodstock.rockframework.domain.pojo.converter;

import javax.xml.bind.JAXBException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.utils.StringUtils;

public class JsonConverter implements Converter {

	private static final HierarchicalStreamDriver	TO_DRIVER	= new JsonHierarchicalStreamDriver();

	private static final HierarchicalStreamDriver	FROM_DRIVER	= new JettisonMappedXmlDriver();

	public JsonConverter() {
		super();
	}

	public String to(Pojo pojo) throws JAXBException {
		Class<?> clazz = pojo.getClass();
		String name = StringUtils.camelCase(clazz.getSimpleName());
		XStream xstream = new XStream(JsonConverter.TO_DRIVER);
		xstream.alias(name, clazz);		
		String s = xstream.toXML(pojo);
		return s;
	}

	@SuppressWarnings("unchecked")
	public <T extends Pojo> T from(Class<T> clazz, String s) throws JAXBException {
		String name = StringUtils.camelCase(clazz.getSimpleName());
		XStream xstream = new XStream(JsonConverter.FROM_DRIVER);
		xstream.alias(name, clazz);
		Object obj = xstream.fromXML(s);
		return (T) obj;
	}

}