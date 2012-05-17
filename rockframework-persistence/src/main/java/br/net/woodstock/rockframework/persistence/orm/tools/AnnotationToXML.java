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
package br.net.woodstock.rockframework.persistence.orm.tools;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import br.net.woodstock.rockframework.reflection.BeanDescriptor;
import br.net.woodstock.rockframework.reflection.ClassFinder;
import br.net.woodstock.rockframework.reflection.PropertyDescriptor;
import br.net.woodstock.rockframework.reflection.impl.AnnotationClassFilter;
import br.net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;
import br.net.woodstock.rockframework.reflection.impl.ClassFinderImpl;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

public class AnnotationToXML {

	private static final String	XML_NAMESPACE	= "http://java.sun.com/xml/ns/persistence/orm";

	private static final String	XML_LOCATION	= "http://java.sun.com/xml/ns/persistence/orm http://java.sun.com/xml/ns/persistence/orm_1_0.xsd";

	private static final String	XML_ROOT		= "entity-mappings";

	private String				baseName;

	public AnnotationToXML(final String baseName) {
		super();
		this.baseName = baseName;
	}

	public XmlDocument toXML() {
		ClassFinder classFinder = new ClassFinderImpl(this.baseName, new AnnotationClassFilter(Entity.class));
		XmlDocument document = new XmlDocument(AnnotationToXML.XML_NAMESPACE, AnnotationToXML.XML_LOCATION, AnnotationToXML.XML_ROOT);
		XmlElement entityMappings = document.getRoot();

		for (Class<?> clazz : classFinder.getClasses()) {
			XmlElement entity = entityMappings.addElement("entity");
			entity.setAttribute("class", clazz.getCanonicalName());
			entity.setAttribute("access", "PROPERTY");
			entity.setAttribute("metadata-complete", "true");

			BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(clazz).getBeanDescriptor();
			Entity e = beanDescriptor.getAnnotation(Entity.class);
			if (ConditionUtils.isNotEmpty(e.name())) {
				entity.setAttribute("name", e.name());
			}

			Table t = beanDescriptor.getAnnotation(Table.class);
			if (t != null) {
				XmlElement table = entity.addElement("table");
				table.setAttribute("name", t.name());
				if (ConditionUtils.isNotEmpty(t.schema())) {
					table.setAttribute("schema", t.schema());
				}
			}

			XmlElement attributes = entity.addElement("attributes");

			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(ManyToMany.class)) {
					ManyToMany mm = propertyDescriptor.getAnnotation(ManyToMany.class);
					this.addManyToMany(attributes, propertyDescriptor, mm);
				} else if (propertyDescriptor.isAnnotationPresent(ManyToOne.class)) {
					ManyToOne mo = propertyDescriptor.getAnnotation(ManyToOne.class);
					this.addManyToOne(attributes, propertyDescriptor, mo);
				} else if (propertyDescriptor.isAnnotationPresent(OneToMany.class)) {
					OneToMany om = propertyDescriptor.getAnnotation(OneToMany.class);
					this.addOneToMany(attributes, propertyDescriptor, om);
				} else if (propertyDescriptor.isAnnotationPresent(OneToOne.class)) {
					OneToOne oo = propertyDescriptor.getAnnotation(OneToOne.class);
					this.addOneToOne(attributes, propertyDescriptor, oo);
				} else if (propertyDescriptor.isAnnotationPresent(Basic.class)) {
					Basic b = propertyDescriptor.getAnnotation(Basic.class);
					this.addBasic(attributes, propertyDescriptor, b);
				} else if (propertyDescriptor.isAnnotationPresent(Id.class)) {
					this.addId(attributes, propertyDescriptor);
				} else if (propertyDescriptor.isAnnotationPresent(Column.class)) {
					Column c = propertyDescriptor.getAnnotation(Column.class);
					this.addBasic(attributes, propertyDescriptor, c);
				} else if (propertyDescriptor.isAnnotationPresent(Transient.class)) {
					this.addTransient(attributes, propertyDescriptor);
				} else {
					attributes.addComment("Field " + propertyDescriptor.getName() + " not correctly mapped");
				}
			}
		}

		return document;
	}

	private XmlElement addManyToMany(final XmlElement parent, final PropertyDescriptor propertyDescriptor, final ManyToMany manyToMany) {
		XmlElement element = parent.addElement("many-to-many");
		element.setAttribute("name", propertyDescriptor.getName());
		element.setAttribute("fetch", manyToMany.fetch().name());
		element.setAttribute("mappedBy", manyToMany.mappedBy());

		this.addCascades(element, manyToMany.cascade());

		if (propertyDescriptor.isAnnotationPresent(JoinTable.class)) {
			JoinTable jt = propertyDescriptor.getAnnotation(JoinTable.class);
			this.addJoinTable(element, jt);
		}
		return element;
	}

	private XmlElement addManyToOne(final XmlElement parent, final PropertyDescriptor propertyDescriptor, final ManyToOne manyToOne) {
		XmlElement element = parent.addElement("many-to-one");
		element.setAttribute("name", propertyDescriptor.getName());
		element.setAttribute("optional", Boolean.toString(manyToOne.optional()));
		element.setAttribute("fetch", manyToOne.fetch().name());

		this.addCascades(element, manyToOne.cascade());

		if (propertyDescriptor.isAnnotationPresent(JoinColumn.class)) {
			JoinColumn jc = propertyDescriptor.getAnnotation(JoinColumn.class);
			this.addJoinColumn(element, jc);
		}
		return element;
	}

	private XmlElement addOneToMany(final XmlElement parent, final PropertyDescriptor propertyDescriptor, final OneToMany oneToMany) {
		XmlElement element = parent.addElement("one-to-many");
		element.setAttribute("name", propertyDescriptor.getName());
		element.setAttribute("mappedBy", oneToMany.mappedBy());
		element.setAttribute("fetch", oneToMany.fetch().name());

		this.addCascades(element, oneToMany.cascade());
		return element;
	}

	private XmlElement addOneToOne(final XmlElement parent, final PropertyDescriptor propertyDescriptor, final OneToOne oneToOne) {
		XmlElement element = parent.addElement("one-to-one");
		element.setAttribute("name", propertyDescriptor.getName());
		element.setAttribute("mappedBy", oneToOne.mappedBy());
		element.setAttribute("fetch", oneToOne.fetch().name());

		this.addCascades(element, oneToOne.cascade());

		if (propertyDescriptor.isAnnotationPresent(JoinColumn.class)) {
			JoinColumn jc = propertyDescriptor.getAnnotation(JoinColumn.class);
			this.addJoinColumn(element, jc);
		}
		return element;
	}

	private XmlElement addBasic(final XmlElement parent, final PropertyDescriptor propertyDescriptor, final Basic basic) {
		XmlElement element = parent.addElement("basic");
		element.setAttribute("name", propertyDescriptor.getName());
		element.setAttribute("optional", Boolean.toString(basic.optional()));
		element.setAttribute("fetch", basic.fetch().name());

		if (propertyDescriptor.isAnnotationPresent(Column.class)) {
			Column c = propertyDescriptor.getAnnotation(Column.class);
			this.addColumn(element, propertyDescriptor, c);
		}
		return element;
	}

	private XmlElement addBasic(final XmlElement parent, final PropertyDescriptor propertyDescriptor, final Column column) {
		XmlElement element = parent.addElement("basic");
		element.setAttribute("name", propertyDescriptor.getName());
		element.setAttribute("optional", Boolean.toString(column.nullable()));

		this.addColumn(element, propertyDescriptor, column);
		return element;
	}

	private XmlElement addId(final XmlElement parent, final PropertyDescriptor propertyDescriptor) {
		XmlElement element = parent.addElement("id");
		element.setAttribute("name", propertyDescriptor.getName());

		if (propertyDescriptor.isAnnotationPresent(Column.class)) {
			Column c = propertyDescriptor.getAnnotation(Column.class);
			this.addColumn(element, propertyDescriptor, c);
		}
		return element;
	}

	private XmlElement addTransient(final XmlElement parent, final PropertyDescriptor propertyDescriptor) {
		XmlElement element = parent.addElement("transient");
		element.setAttribute("name", propertyDescriptor.getName());
		return element;
	}

	private XmlElement addJoinTable(final XmlElement parent, final JoinTable joinTable) {
		XmlElement element = parent.addElement("join-table");
		element.setAttribute("name", joinTable.name());
		if (ConditionUtils.isNotEmpty(joinTable.schema())) {
			element.setAttribute("schema", joinTable.schema());
		}
		for (JoinColumn jc : joinTable.joinColumns()) {
			this.addJoinColumn(element, jc);
		}
		for (JoinColumn jc : joinTable.inverseJoinColumns()) {
			this.addInverseJoinColumn(element, jc);
		}
		return element;
	}

	private XmlElement addColumn(final XmlElement parent, final PropertyDescriptor propertyDescriptor, final Column column) {
		XmlElement element = parent.addElement("column");
		element.setAttribute("name", column.name());
		element.setAttribute("nullable", Boolean.toString(column.nullable()));

		if (propertyDescriptor.getType() == String.class) {
			element.setAttribute("length", Integer.toString(column.length()));
		} else if (Number.class.isAssignableFrom(propertyDescriptor.getType())) {
			if (column.scale() > 0) {
				element.setAttribute("scale", Integer.toString(column.scale()));
			}
			if (column.precision() > 0) {
				element.setAttribute("precision", Integer.toString(column.precision()));
			}
		}

		if (propertyDescriptor.isAnnotationPresent(Temporal.class)) {
			Temporal t = propertyDescriptor.getAnnotation(Temporal.class);
			parent.addElement("temporal", t.value().name());
		}
		return element;
	}

	private XmlElement addJoinColumn(final XmlElement parent, final JoinColumn joinColumn) {
		XmlElement element = parent.addElement("join-column");
		element.setAttribute("name", joinColumn.name());
		element.setAttribute("referenced-column-name", joinColumn.referencedColumnName());
		element.setAttribute("nullable", Boolean.toString(joinColumn.nullable()));
		return element;
	}

	private XmlElement addInverseJoinColumn(final XmlElement parent, final JoinColumn joinColumn) {
		XmlElement element = parent.addElement("inverse-join-column");
		element.setAttribute("name", joinColumn.name());
		element.setAttribute("referenced-column-name", joinColumn.referencedColumnName());
		element.setAttribute("nullable", Boolean.toString(joinColumn.nullable()));
		return element;
	}

	private XmlElement addCascades(final XmlElement parent, final CascadeType[] cascades) {
		XmlElement element = parent.addElement("cascades");
		for (CascadeType c : cascades) {
			element.addElement("cascade-" + c.name().toLowerCase());
		}
		return element;
	}

}
