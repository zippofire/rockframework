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
package br.net.woodstock.rockframework.domain.validator.jee;

import javax.validation.ConstraintValidatorContext;

import br.net.woodstock.rockframework.domain.Entity;


public class ReferenceValidator extends AbstractValidator<Reference, Entity<?>> {

	@Override
	public boolean isValid(final Entity<?> entity, final ConstraintValidatorContext context) {
		if (entity == null) {
			return true;
		}
		Object id = entity.getId();
		if (id != null) {
			return true;
		}
		return false;
	}

}
