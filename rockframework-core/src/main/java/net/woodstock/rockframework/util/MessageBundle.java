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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import net.woodstock.rockframework.utils.LocaleUtils;

public class MessageBundle {

	private ResourceBundle	resource;

	protected MessageBundle(final String baseName) {
		super();
		this.resource = ResourceBundle.getBundle(baseName, LocaleUtils.getLocale());
	}

	protected MessageBundle(final String baseName, final Locale locale) {
		super();
		this.resource = ResourceBundle.getBundle(baseName, locale);
	}

	public String getString(final String key) {
		String msg = this.resource.getString(key);
		return msg;
	}

	public String getString(final String key, final Object... arguments) {
		String pattern = this.resource.getString(key);
		String msg = MessageFormat.format(pattern, arguments);
		return msg;
	}

	public Object getObject(final String key) {
		Object obj = this.resource.getObject(key);
		return obj;
	}

	public static MessageBundle getBundle(final String baseName) {
		return new MessageBundle(baseName, LocaleUtils.getLocale());
	}

	public static MessageBundle getBundle(final String baseName, final Locale locale) {
		return new MessageBundle(baseName, locale);
	}

}
