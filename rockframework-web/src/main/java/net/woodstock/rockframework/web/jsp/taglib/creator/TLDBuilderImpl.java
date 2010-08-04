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
package net.woodstock.rockframework.web.jsp.taglib.creator;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilderImpl;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.xml.dom.XmlDocument;
import net.woodstock.rockframework.xml.dom.XmlElement;

class TLDBuilderImpl extends TLDBuilder {

	public TLDBuilderImpl() {
		super();
	}

	@Override
	public String create(final Class<?> clazz) {
		Assert.notNull(clazz, "clazz");
		if (!clazz.isAnnotationPresent(Tag.class)) {
			throw new IllegalArgumentException("Class " + clazz + " must be annoted with @" + Tag.class.getCanonicalName());
		}

		Tag tag = clazz.getAnnotation(Tag.class);
		String name = tag.name();
		String description = tag.description();
		String type = tag.content().getName();
		boolean dynamicAttributes = tag.DynamicAttributes();

		XmlDocument document = new XmlDocument("tag");
		XmlElement root = document.getRoot();

		root.addElement("name", name);
		root.addElement("description", description);
		root.addElement("body-content", type);
		root.addElement("tag-class", clazz.getCanonicalName());

		if (dynamicAttributes) {
			root.addElement("dynamic-attributes", dynamicAttributes);
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilderImpl().setType(clazz).setMode(ReflectionType.MIXED).getBeanDescriptor();

		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			if (!propertyDescriptor.isAnnotationPresent(Attribute.class)) {
				continue;
			}

			Attribute tldAttribute = propertyDescriptor.getAnnotation(Attribute.class);
			XmlElement e = root.addElement("attribute");

			e.addElement("name", propertyDescriptor.getName());
			e.addElement("rtexprvalue", tldAttribute.rtexprvalue());
			e.addElement("required", tldAttribute.required());
			if ((!tldAttribute.rtexprvalue()) && (tldAttribute.type() != String.class)) {
				e.addElement("type", tldAttribute.type().getCanonicalName());
			}
		}

		return document.toString();
	}

}
