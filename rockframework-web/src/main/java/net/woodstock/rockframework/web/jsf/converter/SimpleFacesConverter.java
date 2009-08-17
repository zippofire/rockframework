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
package net.woodstock.rockframework.web.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

import net.woodstock.rockframework.utils.StringUtils;

public abstract class SimpleFacesConverter<T> extends FacesConverter {

	@Override
	public final Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		T t = this.getAsObject(value);
		this.getLogger().info("From String [" + value + " => " + t + "]");
		return t;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if (value == null) {
			return null;
		}
		T t = (T) value;
		String s = this.getAsString(t);
		this.getLogger().info("To String [" + t + " => " + s + "]");
		return s;
	}

	// Abstract
	protected abstract T getAsObject(String value) throws ConverterException;

	protected abstract String getAsString(T value) throws ConverterException;

}
