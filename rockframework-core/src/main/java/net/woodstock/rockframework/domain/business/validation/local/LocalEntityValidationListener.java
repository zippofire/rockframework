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

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.EntityValidator;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.ValidationListener;

public class LocalEntityValidationListener implements ValidationListener {

	private EntityValidator	validator;

	public LocalEntityValidationListener() {
		super();
		this.validator = LocalEntityValidator.getInstance();
	}

	@PreRemove
	public void preRemove(final Entity<?> entity) {
		Collection<ValidationResult> results = this.validator.validate(entity, Operation.DELETE);
		for (ValidationResult result : results) {
			if (result.isError()) {
				throw new ValidationException(result.getMessage());
			}
		}
	}

	@PrePersist
	public void preInsert(final Entity<?> entity) {
		Collection<ValidationResult> results = this.validator.validate(entity, Operation.SAVE);
		for (ValidationResult result : results) {
			if (result.isError()) {
				throw new ValidationException(result.getMessage());
			}
		}
	}

	@PreUpdate
	public void preUpdate(final Entity<?> entity) {
		Collection<ValidationResult> results = this.validator.validate(entity, Operation.UPDATE);
		for (ValidationResult result : results) {
			if (result.isError()) {
				throw new ValidationException(result.getMessage());
			}
		}
	}

}
