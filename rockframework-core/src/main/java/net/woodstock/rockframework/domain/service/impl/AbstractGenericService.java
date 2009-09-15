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

import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessException;
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.service.GenericService;
import net.woodstock.rockframework.domain.service.ServiceException;

public abstract class AbstractGenericService extends AbstractService implements GenericService {

	private GenericBusiness		business;

	private GenericRepository	repository;

	public AbstractGenericService() {
		super();
	}

	// Objects
	protected GenericBusiness getBusiness() {
		return this.business;
	}

	public void setBusiness(GenericBusiness business) {
		this.business = business;
	}

	protected GenericRepository getRepository() {
		return this.repository;
	}

	public void setRepository(GenericRepository repository) {
		this.repository = repository;
	}

	// Methods
	public void save(Entity<?> entity) throws ServiceException, BusinessException, PersistenceException {
		try {
			this.verifyObjects();
			if (this.business != null) {
				ValidationResult result = this.business.validateSave(entity);
				if (result.isError()) {
					throw new ValidationException(result.getMessage());
				}
			}
			this.repository.save(entity);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public <E extends Entity<?>> E get(E entity) throws ServiceException, BusinessException, PersistenceException {
		try {
			this.verifyObjects();
			if (this.business != null) {
				ValidationResult result = this.business.validateGet(entity);
				if (result.isError()) {
					throw new ValidationException(result.getMessage());
				}
			}
			return this.repository.get(entity);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public <E extends Entity<?>> Collection<E> listAll(E entity, String order) throws ServiceException, BusinessException, PersistenceException {
		try {
			this.verifyObjects();
			if (this.business != null) {
				ValidationResult result = this.business.validateList(entity);
				if (result.isError()) {
					throw new ValidationException(result.getMessage());
				}
			}
			return this.repository.listAll(entity, order);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public <E extends Entity<?>> Collection<E> listByExample(E entity, Map<String, Object> options) throws ServiceException, BusinessException, PersistenceException {
		try {
			this.verifyObjects();
			if (this.business != null) {
				ValidationResult result = this.business.validateList(entity);
				if (result.isError()) {
					throw new ValidationException(result.getMessage());
				}
			}
			return this.repository.listByExample(entity, options);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void update(Entity<?> entity) throws ServiceException, BusinessException, PersistenceException {
		try {
			this.verifyObjects();
			if (this.business != null) {
				ValidationResult result = this.business.validateUpdate(entity);
				if (result.isError()) {
					throw new ValidationException(result.getMessage());
				}
			}
			this.repository.update(entity);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void delete(Entity<?> entity) throws ServiceException, BusinessException, PersistenceException {
		try {
			this.verifyObjects();
			if (this.business != null) {
				ValidationResult result = this.business.validateDelete(entity);
				if (result.isError()) {
					throw new ValidationException(result.getMessage());
				}
			}
			this.repository.delete(entity);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	private void verifyObjects() {
		if (this.business == null) {
			// throw new IllegalStateException("Business is null");
		}
		if (this.repository == null) {
			throw new IllegalStateException("Repository is null");
		}
	}

}
