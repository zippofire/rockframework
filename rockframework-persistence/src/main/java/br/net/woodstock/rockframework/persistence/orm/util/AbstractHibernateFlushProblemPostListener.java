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

import java.io.Serializable;
import java.util.logging.Level;

import org.hibernate.event.EventSource;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.config.DomainLog;
import br.net.woodstock.rockframework.domain.utils.EntityUtils;
import br.net.woodstock.rockframework.reflection.BeanDescriptor;
import br.net.woodstock.rockframework.reflection.PropertyDescriptor;
import br.net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;

abstract class AbstractHibernateFlushProblemPostListener {

	@SuppressWarnings("rawtypes")
	public void refreshEntity(final Object src, final EventSource eventSource) {
		if (src instanceof Entity) {
			try {
				BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(src.getClass()).getBeanDescriptor();
				for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
					Class propertyType = propertyDescriptor.getType();
					Object propertyValue = propertyDescriptor.getValue(src);
					if (Entity.class.isAssignableFrom(propertyType)) {
						Entity entity = (Entity) propertyValue;
						if (EntityUtils.isNotEmptyId(entity)) {
							if (!eventSource.contains(entity)) {
								entity = (Entity) eventSource.get(entity.getClass(), (Serializable) entity.getId());
								eventSource.refresh(entity);
							} else {
								eventSource.refresh(entity);
							}
							propertyDescriptor.setValue(src, entity);
						}
					}
				}
			} catch (Exception e) {
				DomainLog.getInstance().getLog().log(Level.INFO, e.getMessage(), e);
			}
		}
	}
}
