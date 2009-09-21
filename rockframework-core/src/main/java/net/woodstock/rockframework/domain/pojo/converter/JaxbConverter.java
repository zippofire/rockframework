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

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.woodstock.rockframework.domain.Pojo;

public class JaxbConverter implements Converter {

	public JaxbConverter() {
		super();
	}

	public String to(Pojo pojo) {
		try {
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(pojo.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(pojo, writer);
			return writer.toString();
		} catch (JAXBException e) {
			throw new ConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Pojo> T from(Class<T> clazz, String s) {
		try {
			StringReader reader = new StringReader(s);
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object obj = unmarshaller.unmarshal(reader);
			return (T) obj;
		} catch (JAXBException e) {
			throw new ConverterException(e);
		}
	}

}
