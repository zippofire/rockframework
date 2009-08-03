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
package net.woodstock.rockframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedList;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

abstract class BeanInfo {

	private Class<?>				clazz;

	private Collection<FieldInfo>	fields;

	protected BeanInfo(Class<?> clazz) {
		super();
		this.clazz = clazz;
		this.fields = new LinkedList<FieldInfo>();
		this.init();
	}

	public void init() {
		Class<?> c = this.clazz;
		while (c != null) {
			for (Field field : c.getDeclaredFields()) {
				if (this.contains(field)) {
					continue;
				}

				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				}

				try {
					FieldInfo fieldInfo = new FieldInfo(this, field);
					this.fields.add(fieldInfo);
				} catch (NoSuchMethodException e) {
					// this.getLogger().info(e.getMessage(), e);
				}
			}
			c = c.getSuperclass();
		}
	}

	private boolean contains(Field field) {
		for (FieldInfo fieldInfo : this.fields) {
			if (fieldInfo.getFieldName().equals(field.getName())) {
				return true;
			}
		}
		return this.contains(field.getName());
	}

	public String getBeanName() {
		return this.clazz.getName();
	}

	public boolean contains(String fieldName) {
		for (FieldInfo fieldInfo : this.fields) {
			if (fieldInfo.getFieldName().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	public FieldInfo getFieldInfo(String fieldName) throws NoSuchFieldException {
		for (FieldInfo field : this.fields) {
			if (field.getFieldName().equals(fieldName)) {
				return field;
			}
		}
		throw new NoSuchFieldException(fieldName);
	}

	public Collection<FieldInfo> getFieldsInfo() {
		return this.fields;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
		return this.clazz.isAnnotationPresent(clazz);
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return this.clazz.getAnnotation(clazz);
	}

	@Override
	public String toString() {
		return this.clazz.toString();
	}

	public static BeanInfo getBeanInfo(Class<?> clazz) {
		return new BeanInfo(clazz) {
			//
		};
	}

	// Logger
	protected Log getLogger() {
		return SysLogger.getLogger();
	}
}
