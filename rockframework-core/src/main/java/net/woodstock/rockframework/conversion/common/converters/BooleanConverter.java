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
package net.woodstock.rockframework.conversion.common.converters;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.common.AbstractConverter;
import net.woodstock.rockframework.utils.StringUtils;

public class BooleanConverter extends AbstractConverter<Boolean> {

	@Override
	public Boolean from(ConverterContext context, String s) throws ConverterException {
		if (StringUtils.isEmpty(s)) {
			return null;
		}
		if ((context != null) && (context.isAnnotationPresent(BooleanFormat.class))) {
			BooleanFormat format = context.getAnnotation(BooleanFormat.class);
			if (s.equals(format.trueValue())) {
				return Boolean.TRUE;
			} else if (s.equals(format.falseValue())) {
				return Boolean.FALSE;
			}
			return null;
		}
		return new Boolean(s);
	}

	@Override
	public String to(ConverterContext context, Boolean t) throws ConverterException {
		if (t == null) {
			return StringUtils.BLANK;
		}
		if ((context != null) && (context.isAnnotationPresent(BooleanFormat.class))) {
			BooleanFormat format = context.getAnnotation(BooleanFormat.class);
			if (t.booleanValue()) {
				return format.trueValue();
			}
			return format.falseValue();
		}
		return t.toString();
	}

	public static @interface BooleanFormat {

		String trueValue();

		String falseValue();

	}

}
