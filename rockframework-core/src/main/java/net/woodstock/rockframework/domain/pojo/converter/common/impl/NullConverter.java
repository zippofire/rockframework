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

import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.utils.StringUtils;

public class NullConverter extends AbstractAttributeConverter<Object> {

	public String fromText(String text, PropertyDescriptor propertyDescriptor) {
		return null;
	}

	public String toText(Object o, PropertyDescriptor propertyDescriptor) {
		return StringUtils.BLANK;
	}

}
