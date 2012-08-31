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
package br.net.woodstock.rockframework.web.jsp.taglib.creator;

import javax.servlet.jsp.tagext.JspTag;

import br.net.woodstock.rockframework.reflection.BeanDescriptor;
import br.net.woodstock.rockframework.reflection.PropertyDescriptor;
import br.net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

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

		if (!JspTag.class.isAssignableFrom(clazz)) {
			WebLog.getInstance().getLogger().info("Class " + clazz.getCanonicalName() + " is not not a child of " + JspTag.class.getCanonicalName());
		}

		Tag tag = clazz.getAnnotation(Tag.class);

		XmlDocument document = new XmlDocument("tag");
		XmlElement root = document.getRoot();

		if (ConditionUtils.isNotEmpty(tag.description())) {
			root.addElement("description").setData(tag.description());
		}

		root.addElement("name").setData(tag.name());
		root.addElement("tag-class").setData(clazz.getCanonicalName());
		root.addElement("body-content").setData(tag.content());

		if (tag.dynamicAttributes()) {
			root.addElement("dynamic-attributes").setData(Boolean.valueOf(tag.dynamicAttributes()));
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(clazz).getBeanDescriptor();

		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			if (!propertyDescriptor.isAnnotationPresent(Attribute.class)) {
				continue;
			}

			Attribute tldAttribute = propertyDescriptor.getAnnotation(Attribute.class);
			XmlElement e = root.addElement("attribute");

			if (ConditionUtils.isNotEmpty(tldAttribute.description())) {
				e.addElement("description").setData(tldAttribute.description());
			}

			e.addElement("name").setData(propertyDescriptor.getName());
			e.addElement("required").setData(Boolean.valueOf(tldAttribute.required()));
			e.addElement("rtexprvalue").setData(Boolean.valueOf(tldAttribute.rtexprvalue()));
			if ((!tldAttribute.rtexprvalue()) && (tldAttribute.type() != String.class)) {
				e.addElement("type").setData(tldAttribute.type().getCanonicalName());
			}
		}

		return document.toString();
	}
}
