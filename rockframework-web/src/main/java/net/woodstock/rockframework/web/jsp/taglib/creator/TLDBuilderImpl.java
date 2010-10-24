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

import javax.servlet.jsp.tagext.JspTag;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilderImpl;
import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.config.WebLog;
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

		if (!JspTag.class.isAssignableFrom(clazz)) {
			WebLog.getInstance().getLog().info("Class " + clazz.getCanonicalName() + " is not not a child of " + JspTag.class.getCanonicalName());
		}

		Tag tag = clazz.getAnnotation(Tag.class);

		XmlDocument document = new XmlDocument("tag");
		XmlElement root = document.getRoot();

		if (!StringUtils.isEmpty(tag.description())) {
			root.addElement("description").setData(tag.description());
		}

		root.addElement("name").setData(tag.name());
		root.addElement("tag-class").setData(clazz.getCanonicalName());
		root.addElement("body-content").setData(tag.content());

		if (tag.dynamicAttributes()) {
			root.addElement("dynamic-attributes").setData(Boolean.valueOf(tag.dynamicAttributes()));
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilderImpl().setType(clazz).setMode(ReflectionType.MIXED).getBeanDescriptor();

		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			if (!propertyDescriptor.isAnnotationPresent(Attribute.class)) {
				continue;
			}

			Attribute tldAttribute = propertyDescriptor.getAnnotation(Attribute.class);
			XmlElement e = root.addElement("attribute");

			if (!StringUtils.isEmpty(tldAttribute.description())) {
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
