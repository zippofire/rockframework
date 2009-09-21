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

import net.woodstock.rockframework.domain.pojo.converter.common.AttributeConverter;
import net.woodstock.rockframework.domain.pojo.converter.common.DateFormat;
import net.woodstock.rockframework.domain.pojo.converter.common.NumberFormat;
import net.woodstock.rockframework.domain.pojo.converter.common.Size;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.StringUtils;

import org.apache.commons.logging.Log;

public abstract class AbstractAttributeConverter<T> implements AttributeConverter<T> {

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

	protected String getDateFormat(PropertyDescriptor p) {
		if (p.isAnnotationPresent(DateFormat.class)) {
			return p.getAnnotation(DateFormat.class).value();
		}
		return null;
	}

	protected String getNumberFormat(PropertyDescriptor p) {
		if (p.isAnnotationPresent(NumberFormat.class)) {
			return p.getAnnotation(NumberFormat.class).value();
		}
		return null;
	}

	protected int getSize(PropertyDescriptor p) {
		if (p.isAnnotationPresent(Size.class)) {
			return p.getAnnotation(Size.class).value();
		}
		return -1;
	}

	// Format
	protected String lpad(String s, int size) {
		if (size < 1) {
			return s;
		}
		return StringUtils.lpad(s, size, ' ').substring(0, size);
	}

	protected String rpad(String s, int size) {
		if (size < 1) {
			return s;
		}
		return StringUtils.rpad(s, size, ' ').substring(0, size);
	}

	protected String trim(String s) {
		String str = s.trim();
		if (str.length() == 0) {
			return null;
		}
		return str;
	}
}
