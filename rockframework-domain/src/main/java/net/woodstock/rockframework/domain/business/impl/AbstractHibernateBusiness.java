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
package net.woodstock.rockframework.domain.business.impl;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.config.DomainMessage;
import net.woodstock.rockframework.utils.ConditionUtils;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractHibernateBusiness extends AbstractBusiness {

	public AbstractHibernateBusiness() {
		super();
	}

	protected ValidationResult validate(final Entity entity) {
		ClassValidator validator = new ClassValidator(entity.getClass());
		if (validator.hasValidationRules()) {
			InvalidValue[] values = validator.getInvalidValues(entity);
			if (ConditionUtils.isNotEmpty(values)) {
				String message = values[0].toString();
				return new ValidationResult(true, message);
			}
		}
		return new ValidationResult(false, DomainMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_OK));
	}

}
