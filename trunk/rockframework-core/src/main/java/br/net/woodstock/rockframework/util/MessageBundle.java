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
package br.net.woodstock.rockframework.util;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageBundle extends ResourceBundle {

	private ResourceBundle	resource;

	private MessageBundle(final String baseName) {
		super();
		Assert.notNull(baseName, "baseName");
		this.resource = ResourceBundle.getBundle(baseName, Locale.getDefault());
	}

	private MessageBundle(final String baseName, final Locale locale) {
		super();
		Assert.notNull(baseName, "baseName");
		Assert.notNull(locale, "locale");
		this.resource = ResourceBundle.getBundle(baseName, locale);
	}

	private MessageBundle(final ResourceBundle resource) {
		super();
		Assert.notNull(resource, "resource");
		this.resource = resource;
	}

	public String getString(final String key, final Object... arguments) {
		String pattern = this.resource.getString(key);
		String msg = MessageFormat.format(pattern, arguments);
		return msg;
	}

	@Override
	public Enumeration<String> getKeys() {
		return this.resource.getKeys();
	}

	@Override
	protected Object handleGetObject(final String key) {
		return this.handleGetObject(key);
	}

	public static MessageBundle getMessageBundle(final String baseName) {
		return new MessageBundle(baseName);
	}

	public static MessageBundle getMessageBundle(final String baseName, final Locale locale) {
		return new MessageBundle(baseName, locale);
	}

	public static MessageBundle getMessageBundle(final ResourceBundle resource) {
		return new MessageBundle(resource);
	}

}
