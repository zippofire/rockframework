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
package net.woodstock.rockframework.domain.spring;

import java.util.ArrayList;
import java.util.List;

import net.woodstock.rockframework.domain.Entity;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

public class EntityDetector {

	private ClassPathScanningCandidateComponentProvider	provider;

	private String										basePackage;

	private List<Class<?>>								classes;

	public EntityDetector() {
		this.provider = new ClassPathScanningCandidateComponentProvider(false);
		// this.provider.addIncludeFilter(new AnnotationTypeFilter(javax.persistence.Entity.class));
		this.provider.addIncludeFilter(new AssignableTypeFilter(Entity.class));
	}

	public void setBasePackage(final String basePackage) {
		this.basePackage = basePackage;
	}

	public List<Class<?>> getClasses() throws ClassNotFoundException {
		if (this.classes == null) {
			this.classes = new ArrayList<Class<? extends Object>>();
			for (BeanDefinition beanDef : this.provider.findCandidateComponents(this.basePackage)) {
				this.classes.add(Class.forName(beanDef.getBeanClassName()));
			}
		}
		return this.classes;
	}

}
