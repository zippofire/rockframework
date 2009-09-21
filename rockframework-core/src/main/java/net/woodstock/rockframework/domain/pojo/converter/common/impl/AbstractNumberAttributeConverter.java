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
package net.woodstock.rockframework.domain.pojo.converter.common.impl;

import net.woodstock.rockframework.domain.pojo.converter.ConverterException;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.utils.NumberUtils;
import net.woodstock.rockframework.utils.StringUtils;

abstract class AbstractNumberAttributeConverter<T extends Number> extends AbstractAttributeConverter<T> {

	public T fromText(String text, PropertyDescriptor propertyDescriptor) {
		try {
			Number n = null;
			if (!StringUtils.isEmpty(text)) {
				String format = this.getNumberFormat(propertyDescriptor);
				if (!StringUtils.isEmpty(format)) {
					n = NumberUtils.parse(text, format);
				} else {
					n = NumberUtils.parse(text);
				}
			}
			if (n == null) {
				return null;
			}
			return this.convertTo(n);
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	public String toText(T n, PropertyDescriptor propertyDescriptor) {
		try {
			String s = StringUtils.BLANK;
			if (n != null) {
				String format = this.getNumberFormat(propertyDescriptor);
				if (!StringUtils.isEmpty(format)) {
					s = NumberUtils.format(n, format);
				} else {
					s = n.toString();
				}
			}
			return s;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

	protected abstract T convertTo(Number n);

}
