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
package net.woodstock.rockframework.domain.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

public abstract class PojoUtils {

	private PojoUtils() {
		//
	}

	public static boolean hasNotNullAttribute(Pojo p) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		return PojoUtils.hasNotNullAttribute(p, false);
	}

	@SuppressWarnings("unchecked")
	public static boolean hasNotNullAttribute(Pojo p, boolean includeCollections) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (p == null) {
			return false;
		}

		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(p.getClass());
		for (PropertyDescriptor pd : beanDescriptor.getProperties()) {
			Object tmp = pd.getValue(p);
			if (tmp != null) {
				if (PojoUtils.isNotNull(tmp, includeCollections)) {
					return true;
				}
				if ((includeCollections) && (tmp instanceof Collection)) {
					if (((Collection<?>) tmp).size() > 0) {
						for (Object o : (Collection<?>) tmp) {
							if (PojoUtils.isNotNull(o, includeCollections)) {
								return true;
							}
						}
					}
				}
				if ((includeCollections) && (tmp.getClass().isArray())) {
					if (Array.getLength(tmp) > 0) {
						for (int i = 0; i < Array.getLength(tmp); i++) {
							if (PojoUtils.isNotNull(Array.get(tmp, i), includeCollections)) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	private static boolean isNotNull(Object o, boolean includeCollections) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (o instanceof Boolean) {
			return true;
		}
		if (o instanceof Character) {
			return true;
		}
		if (o instanceof Date) {
			return true;
		}
		if (o instanceof Number) {
			return true;
		}
		if (o instanceof String) {
			return true;
		}
		if (o instanceof Pojo) {
			if (PojoUtils.hasNotNullAttribute((Pojo) o, includeCollections)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Pojo> int compareTo(T t1, T t2, String fieldName) {
		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(t1.getClass());
		PropertyDescriptor propertyDescriptor = beanDescriptor.getProperty(fieldName);
		Object o1 = propertyDescriptor.getValue(t1);
		Object o2 = propertyDescriptor.getValue(t2);
		return ((Comparable) o1).compareTo(o2);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Pojo> Comparator<T> createComparator(T pojo, String fieldName) {
		return new PojoComparator(pojo.getClass(), fieldName);
	}

	static class PojoComparator<T extends Pojo> implements Comparator<T> {

		private PropertyDescriptor	propertyDescriptor;

		public PojoComparator(Class<? extends Pojo> clazz, String fieldName) {
			super();
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(clazz);
			this.propertyDescriptor = beanDescriptor.getProperty(fieldName);
		}

		@SuppressWarnings("unchecked")
		public int compare(T t1, T t2) {
			try {
				Object o1 = this.propertyDescriptor.getValue(t1);
				Object o2 = this.propertyDescriptor.getValue(t2);
				return ((Comparable) o1).compareTo(o2);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
