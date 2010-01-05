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
package net.woodstock.rockframework.domain.business.validation.jpa;

import java.lang.annotation.Annotation;

import net.woodstock.rockframework.domain.business.validation.ValidationResult;

public class JPAValidationResult extends ValidationResult {

	private static final long	serialVersionUID	= -8774606852333629295L;

	private String				name;

	private Annotation			annotation;

	JPAValidationResult(final String name, final Annotation annotation, final boolean error, final String message) {
		super(error, message);
		this.name = name;
		this.annotation = annotation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.name);
		builder.append("\n\tAnnotation: ");
		builder.append(this.annotation);
		builder.append("\n\tStatus    : ");
		if (this.isError()) {
			builder.append("ERROR ");
			builder.append(this.getMessage());
		} else {
			builder.append("SUCCESS");
		}
		return builder.toString();
	}

}
