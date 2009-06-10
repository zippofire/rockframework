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

import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.xml.dom.XmlDocument;
import net.woodstock.rockframework.xml.dom.XmlElement;

public abstract class TLDFactory {

	private static TLDFactory	factory;

	TLDFactory() {
		super();
	}

	public String create(Class<?> clazz) {
		String name = clazz.getName();
		String type = BodyContent.JSP.getName();
		if (clazz.isAnnotationPresent(TLD.class)) {
			name = clazz.getAnnotation(TLD.class).name();
			type = clazz.getAnnotation(TLD.class).content().getName();
		}

		XmlDocument document = new XmlDocument("tag");
		XmlElement root = document.getRoot();

		root.addElement("name", name);
		root.addElement("body-content", type);
		root.addElement("tag-class", clazz.getCanonicalName());

		BeanInfo beanInfo = BeanInfo.getBeanInfo(clazz);

		for (FieldInfo fieldInfo : beanInfo.getFieldsInfo()) {
			if (!fieldInfo.isAnnotationPresent(TLDAttribute.class)) {
				continue;
			}

			TLDAttribute tldAttribute = fieldInfo.getAnnotation(TLDAttribute.class);
			XmlElement e = root.addElement("attribute");

			e.addElement("name", fieldInfo.getFieldName());
			e.addElement("rtexprvalue", tldAttribute.rtexprvalue());
			e.addElement("required", tldAttribute.required());
		}

		return document.toString();
	}

	public static TLDFactory getInstance() {
		if (TLDFactory.factory == null) {
			synchronized (TLDFactory.class) {
				if (TLDFactory.factory == null) {
					TLDFactory.factory = new TLDFactory() {
						//
					};
				}
			}
		}
		return TLDFactory.factory;
	}

}
