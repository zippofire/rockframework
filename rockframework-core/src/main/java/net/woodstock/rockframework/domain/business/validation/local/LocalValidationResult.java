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
package net.woodstock.rockframework.domain.business.validation.local;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import net.woodstock.rockframework.domain.business.ValidationResult;

public final class LocalValidationResult extends ValidationResult {

	private static final long		serialVersionUID	= 6766952296126588148L;

	private LocalValidationContext	context;

	private LocalValidationResult(final LocalValidationContext context, final boolean error, final String message) {
		super(error, message);
		this.context = context;
	}

	public LocalValidationContext getContext() {
		return this.context;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getContext().getCanonicalName());
		builder.append("\n\tAnnotation: ");
		builder.append(this.context.getAnnotation());
		builder.append("\n\tStatus    : ");
		if (this.isError()) {
			builder.append("ERROR ");
			builder.append(this.getMessage());
		} else {
			builder.append("SUCCESS");
		}
		return builder.toString();
	}

	// Static
	protected static LocalValidationResult getSuccessResult(final LocalValidationContext context) {
		return new LocalValidationResult(context, false, null);
	}

	protected static LocalValidationResult getErrorResult(final LocalValidationContext context, final String message) {
		return new LocalValidationResult(context, true, message);
	}

	public static Collection<ValidationResult> getErrors(final Collection<ValidationResult> collection) {
		if (collection == null) {
			return null;
		}
		List<ValidationResult> list = new LinkedList<ValidationResult>();
		for (ValidationResult result : collection) {
			if (result.isError()) {
				list.add(result);
			}
		}
		return list;
	}

}
