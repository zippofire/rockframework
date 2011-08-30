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
package net.woodstock.rockframework.domain.persistence.orm.check;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.ClassFinder;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.AssignableClassFilter;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;
import net.woodstock.rockframework.reflection.impl.ClassFinderImpl;
import net.woodstock.rockframework.utils.ConditionUtils;

public class MappingChecker {

	private String	baseName;

	public MappingChecker(final String baseName) {
		super();
		this.baseName = baseName;
	}

	public Collection<String> getErrors() {
		Collection<String> collection = new ArrayList<String>();
		ClassFinder classFinder = new ClassFinderImpl(this.baseName, new AssignableClassFilter(Entity.class));
		for (Class<?> clazz : classFinder.getClasses()) {
			BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(clazz).getBeanDescriptor();
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (propertyDescriptor.isAnnotationPresent(ManyToMany.class)) {

				}
				if (propertyDescriptor.isAnnotationPresent(ManyToOne.class)) {
					CascadeType[] cascades = propertyDescriptor.getAnnotation(OneToMany.class).cascade();
					if (ConditionUtils.isNotEmpty(cascades)) {
						collection.add("Missing value @OneToMany(cascade) on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
					}

					if (!propertyDescriptor.isAnnotationPresent(JoinColumn.class)) {
						collection.add("Missing @JoinColumn on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
					}
				}
				if (propertyDescriptor.isAnnotationPresent(OneToMany.class)) {
					String mappedBy = propertyDescriptor.getAnnotation(OneToMany.class).mappedBy();
					if (ConditionUtils.isEmpty(mappedBy)) {
						collection.add("Missing value @OneToMany(mappedBy) on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
					}
				}
				if (propertyDescriptor.isAnnotationPresent(OneToOne.class)) {

				}
			}
		}

		return collection;
	}

}
