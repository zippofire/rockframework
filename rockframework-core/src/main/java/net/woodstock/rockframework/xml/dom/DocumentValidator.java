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
package net.woodstock.rockframework.xml.dom;

import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DocumentValidator {

	private SchemaFactory	factory;

	private Schema			schema;

	private Validator		validator;

	public DocumentValidator(final Source schema, final String ns) throws SAXException {
		super();
		this.factory = SchemaFactory.newInstance(ns);
		this.schema = this.factory.newSchema(schema);
		this.validator = this.schema.newValidator();
	}

	public void validate(final Document document) throws SAXException, IOException {
		this.validator.validate(new DOMSource(document));
	}

}
