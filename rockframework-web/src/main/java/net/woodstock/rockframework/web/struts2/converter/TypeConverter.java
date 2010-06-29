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
package net.woodstock.rockframework.web.struts2.converter;

import java.util.Map;

import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.config.WebLog;

import org.apache.struts2.util.StrutsTypeConverter;

public abstract class TypeConverter<T> extends StrutsTypeConverter {

	@Override
	@SuppressWarnings("unchecked")
	public final Object convertFromString(final Map context, final String[] o, final Class toClass) {
		if ((o == null) || (o.length != 1)) {
			super.performFallbackConversion(context, o, toClass);
		}

		String s = o[0];
		if (StringUtils.isEmpty(s)) {
			return super.performFallbackConversion(context, o, toClass);
		}

		T t = this.convertFromString(o[0], toClass);
		WebLog.getInstance().getLog().debug("From String [" + s + " => " + t + "]");
		return t;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final String convertToString(final Map map, final Object o) {
		if (o == null) {
			return null;
		}
		T t = (T) o;
		String s = this.convertToString(t);
		WebLog.getInstance().getLog().debug("To String [" + t + " => " + s + "]");
		return s;
	}

	@SuppressWarnings("unchecked")
	protected abstract T convertFromString(String s, Class toClass);

	protected abstract String convertToString(T o);

}
