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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
					ManyToMany manyToMany = propertyDescriptor.getAnnotation(ManyToMany.class);
					CascadeType[] cascades = manyToMany.cascade();
					FetchType fetchType = manyToMany.fetch();

					this.checkCascade(collection, manyToMany, beanDescriptor, propertyDescriptor, cascades);
					this.checkFetch(collection, manyToMany, beanDescriptor, propertyDescriptor, fetchType);

					if (!propertyDescriptor.isAnnotationPresent(JoinTable.class)) {
						collection.add("Missing @JoinTable on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
					}
				}
				if (propertyDescriptor.isAnnotationPresent(ManyToOne.class)) {
					ManyToOne manyToOne = propertyDescriptor.getAnnotation(ManyToOne.class);
					CascadeType[] cascades = manyToOne.cascade();
					FetchType fetchType = manyToOne.fetch();

					this.checkCascade(collection, manyToOne, beanDescriptor, propertyDescriptor, cascades);
					this.checkFetch(collection, manyToOne, beanDescriptor, propertyDescriptor, fetchType);

					if (!propertyDescriptor.isAnnotationPresent(JoinColumn.class)) {
						collection.add("Missing @JoinColumn on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
					}
				}
				if (propertyDescriptor.isAnnotationPresent(OneToMany.class)) {
					OneToMany oneToMany = propertyDescriptor.getAnnotation(OneToMany.class);
					CascadeType[] cascades = oneToMany.cascade();
					FetchType fetchType = oneToMany.fetch();
					String mappedBy = oneToMany.mappedBy();

					this.checkCascade(collection, oneToMany, beanDescriptor, propertyDescriptor, cascades);
					this.checkFetch(collection, oneToMany, beanDescriptor, propertyDescriptor, fetchType);
					this.checkMappedBy(collection, oneToMany, beanDescriptor, propertyDescriptor, mappedBy);
				}
				if (propertyDescriptor.isAnnotationPresent(OneToOne.class)) {
					OneToOne oneToOne = propertyDescriptor.getAnnotation(OneToOne.class);
					CascadeType[] cascades = oneToOne.cascade();
					FetchType fetchType = oneToOne.fetch();
					String mappedBy = oneToOne.mappedBy();

					this.checkCascade(collection, oneToOne, beanDescriptor, propertyDescriptor, cascades);
					this.checkFetch(collection, oneToOne, beanDescriptor, propertyDescriptor, fetchType);

					if (ConditionUtils.isEmpty(mappedBy)) {
						if (!propertyDescriptor.isAnnotationPresent(JoinColumn.class)) {
							collection.add("Missing @OneToOne(mappedBy) or @JoinColumn on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
						}
					}
				}
			}
		}

		return collection;
	}

	private void checkCascade(final Collection<String> results, final Annotation annotation, final BeanDescriptor beanDescriptor, final PropertyDescriptor propertyDescriptor, final CascadeType[] cascades) {
		if (ConditionUtils.isEmpty(cascades)) {
			results.add("Missing value @" + annotation.getClass().getName() + "(cascade) on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
		}
	}

	private void checkFetch(final Collection<String> results, final Annotation annotation, final BeanDescriptor beanDescriptor, final PropertyDescriptor propertyDescriptor, final FetchType fetchType) {
		if (fetchType != FetchType.LAZY) {
			results.add("Illegal FetchType @" + annotation.getClass().getName() + "(fetch) on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
		}
	}

	private void checkMappedBy(final Collection<String> results, final Annotation annotation, final BeanDescriptor beanDescriptor, final PropertyDescriptor propertyDescriptor, final String mappedBy) {
		if (ConditionUtils.isEmpty(mappedBy)) {
			results.add("Missing value @" + annotation.getClass().getName() + "(mappedBy) on " + beanDescriptor.getType().getCanonicalName() + "." + propertyDescriptor.getName());
		}
	}

}
