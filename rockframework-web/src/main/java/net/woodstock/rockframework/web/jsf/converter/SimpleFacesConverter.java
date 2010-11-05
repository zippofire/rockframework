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

import net.woodstock.rockframework.utils.ConditionUtils;
import net.woodstock.rockframework.web.config.WebLog;

public abstract class SimpleFacesConverter<T> extends FacesConverter {

	@Override
	public final T getAsObject(final FacesContext context, final UIComponent component, final String value) {
		if (ConditionUtils.isEmpty(value)) {
			return null;
		}

		T t = this.getAsObject(value);
		WebLog.getInstance().getLog().debug("From String [" + value + " => " + t + "]");
		return t;
	}

	@Override
	@SuppressWarnings("unchecked")
	public final String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (value == null) {
			return null;
		}
		T t = (T) value;
		String s = this.getAsString(t);
		WebLog.getInstance().getLog().debug("To String [" + t + " => " + s + "]");
		return s;
	}

	// Abstract
	protected abstract T getAsObject(String value);

	protected abstract String getAsString(T value);

}
