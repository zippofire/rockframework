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

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ValidationResult implements Serializable {

	private static final long	serialVersionUID	= -5491787053707784242L;

	private ValidationContext	context;

	private boolean				error;

	private String				message;

	private ValidationResult(ValidationContext context, boolean error, String message) {
		super();
		this.context = context;
		this.error = error;
		this.message = message;
	}

	public ValidationContext getContext() {
		return this.context;
	}

	public boolean isError() {
		return this.error;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// Others
	public boolean isSuccess() {
		return !this.error;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getContext().getCanonicalName());
		builder.append("\n\tAnnotation: ");
		builder.append(this.context.getAnnotation());
		builder.append("\n\tStatus    : ");
		if (this.error) {
			builder.append("ERROR ");
			builder.append(this.getMessage());
		} else {
			builder.append("SUCCESS");
		}
		return builder.toString();
	}

	// Static
	protected static ValidationResult getSuccessResult(ValidationContext context) {
		return new ValidationResult(context, false, null);
	}

	protected static ValidationResult getErrorResult(ValidationContext context, String message) {
		return new ValidationResult(context, true, message);
	}

	public static Collection<ValidationResult> getErrors(Collection<ValidationResult> collection) {
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
