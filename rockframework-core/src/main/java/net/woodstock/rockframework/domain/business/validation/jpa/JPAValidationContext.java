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

import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationContext;

public class JPAValidationContext extends LocalValidationContext {

	public JPAValidationContext(Object value, String name, Annotation annotation, Operation operation,
			LocalValidationContext parentContext) {
		super(value, name, annotation, operation, parentContext);
	}

	public JPAValidationContext(Object value, String name, Annotation annotation, Operation operation) {
		super(value, name, annotation, operation);
	}

}
