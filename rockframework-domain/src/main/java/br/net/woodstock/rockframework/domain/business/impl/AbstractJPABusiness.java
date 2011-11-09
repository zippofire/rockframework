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
package br.net.woodstock.rockframework.domain.business.impl;

import java.util.Collection;

import org.springframework.util.Assert;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.domain.business.BusinessResult;
import br.net.woodstock.rockframework.domain.validator.jpa.EntityValidator;
import br.net.woodstock.rockframework.domain.validator.jpa.Operation;
import br.net.woodstock.rockframework.domain.validator.jpa.ValidationResult;
import br.net.woodstock.rockframework.domain.validator.jpa.impl.EntityValidatorImpl;

@SuppressWarnings("rawtypes")
public abstract class AbstractJPABusiness extends AbstractBusiness {

	private static final long	serialVersionUID	= 5567904241262848365L;

	public AbstractJPABusiness() {
		super();
	}

	protected BusinessResult validate(final Entity entity, final Operation operation) {
		Assert.notNull(entity, "entity");
		Assert.notNull(operation, "operation");

		EntityValidator validator = new EntityValidatorImpl(operation);
		Collection<ValidationResult> results = validator.validate(entity);
		if (results.size() > 0) {
			ValidationResult result = results.iterator().next();
			return new BusinessResult(true, result.getMessage());
		}

		return new BusinessResult(false, AbstractBusiness.OK_MESSAGE);
	}

}
