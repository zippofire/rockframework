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
package br.net.woodstock.rockframework.persistence.orm.util;

import java.util.logging.Level;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.event.AbstractPreDatabaseOperationEvent;
import org.hibernate.event.EventSource;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.config.DomainLog;
import br.net.woodstock.rockframework.domain.utils.EntityUtils;
import br.net.woodstock.rockframework.reflection.BeanDescriptor;
import br.net.woodstock.rockframework.reflection.PropertyDescriptor;
import br.net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;

abstract class AbstractHibernateFlushProblemPreListener {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cleanEntity(final AbstractPreDatabaseOperationEvent event) {
		Object src = event.getEntity();
		EventSource eventSource = event.getSession();
		if (src instanceof Entity) {
			try {
				BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(src.getClass()).getBeanDescriptor();
				for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
					Class propertyType = propertyDescriptor.getType();
					Object propertyValue = propertyDescriptor.getValue(src);
					if (Entity.class.isAssignableFrom(propertyType)) {
						Entity entity = (Entity) propertyValue;
						if (EntityUtils.isNotEmptyId(entity)) {
							if (!this.isCascade(propertyDescriptor)) {
								Entity newEntity = (Entity) propertyType.newInstance();
								newEntity.setId(entity.getId());
								propertyDescriptor.setValue(src, newEntity);
								eventSource.evict(entity);
							}
						} else {
							propertyDescriptor.setValue(src, null);
						}
					}
				}
			} catch (Exception e) {
				DomainLog.getInstance().getLog().log(Level.INFO, e.getMessage(), e);
			}
		}
	}

	protected boolean isCascade(final PropertyDescriptor propertyDescriptor) {
		if ((propertyDescriptor.isAnnotationPresent(OneToOne.class)) || (propertyDescriptor.isAnnotationPresent(ManyToOne.class))) {
			CascadeType[] cascades = null;
			boolean isCascade = false;
			if (propertyDescriptor.isAnnotationPresent(OneToOne.class)) {
				OneToOne oneToOne = propertyDescriptor.getAnnotation(OneToOne.class);
				cascades = oneToOne.cascade();
			} else {
				ManyToOne manyToOne = propertyDescriptor.getAnnotation(ManyToOne.class);
				cascades = manyToOne.cascade();
			}
			if ((cascades != null) && (cascades.length > 0)) {
				for (CascadeType cascade : cascades) {
					if ((cascade == CascadeType.ALL) || (cascade == CascadeType.MERGE)) {
						isCascade = true;
					}
				}
			}
			return isCascade;
		}
		return false;
	}

}
