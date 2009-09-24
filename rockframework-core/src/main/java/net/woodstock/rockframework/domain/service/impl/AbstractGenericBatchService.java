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
package net.woodstock.rockframework.domain.service.impl;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessException;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.service.GenericBatchService;
import net.woodstock.rockframework.domain.service.ServiceException;

public class AbstractGenericBatchService extends AbstractGenericService implements GenericBatchService {

	public AbstractGenericBatchService() {
		super();
	}

	@Override
	public void save(Entity<?>... entities) throws ServiceException, BusinessException, PersistenceException {
		for (Entity<?> entity : entities) {
			ValidationResult result = this.getBusiness().validateSave(entity);
			if (result.isError()) {
				throw new ValidationException(result.getMessage());
			}
		}
		for (Entity<?> entity : entities) {
			this.getRepository().save(entity);
		}
	}

	@Override
	public void update(Entity<?>... entities) throws ServiceException, BusinessException, PersistenceException {
		for (Entity<?> entity : entities) {
			ValidationResult result = this.getBusiness().validateUpdate(entity);
			if (result.isError()) {
				throw new ValidationException(result.getMessage());
			}
		}
		for (Entity<?> entity : entities) {
			this.getRepository().update(entity);
		}
	}

	@Override
	public void delete(Entity<?>... entities) throws ServiceException, BusinessException, PersistenceException {
		for (Entity<?> entity : entities) {
			ValidationResult result = this.getBusiness().validateDelete(entity);
			if (result.isError()) {
				throw new ValidationException(result.getMessage());
			}
		}
		for (Entity<?> entity : entities) {
			this.getRepository().delete(entity);
		}
	}

}
