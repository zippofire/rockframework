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
package net.woodstock.rockframework.domain.business.validation;

import net.woodstock.rockframework.utils.StringUtils;

public class ValidationContext {

	private static final char	NAME_SEPARATOR	= '.';

	private Object				value;

	private String				name;

	private Operation			operation;

	private ValidationContext	parentContext;

	protected ValidationContext(final Object value, final String name, final Operation operation) {
		this(value, name, operation, null);
	}

	protected ValidationContext(final Object value, final String name, final Operation operation, final ValidationContext parentContext) {
		super();
		this.value = value;
		this.name = name;
		this.operation = operation;
		this.parentContext = parentContext;
	}

	// Getters and Setters
	public Object getValue() {
		return this.value;
	}

	public String getName() {
		if (StringUtils.isEmpty(this.name)) {
			return StringUtils.BLANK;
		}
		return this.name;
	}

	public Operation getOperation() {
		return this.operation;
	}

	public ValidationContext getParentContext() {
		return this.parentContext;
	}

	// Others
	public String getCanonicalName() {
		StringBuilder builder = new StringBuilder();
		if (this.parentContext != null) {
			String parentName = this.parentContext.getCanonicalName();
			if (!StringUtils.isEmpty(parentName)) {
				builder.append(parentName);
				builder.append(ValidationContext.NAME_SEPARATOR);
			}
		}
		if (!StringUtils.isEmpty(this.name)) {
			builder.append(this.name);
		}
		return builder.toString();
	}

}
