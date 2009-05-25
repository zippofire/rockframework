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

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.BusinessException;
import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.business.impl.AbstractBusiness;
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.persistence.PersistenceException;
import net.woodstock.rockframework.domain.service.GenericService;
import net.woodstock.rockframework.domain.service.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class SpringGenericServiceImpl extends AbstractService implements GenericService {

	@Autowired(required = false)
	private GenericBusiness		business;

	@Autowired(required = true)
	private GenericRepository	repository;

	public SpringGenericServiceImpl() {
		super();
	}

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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(Entity<?> entity) throws ServiceException, BusinessException, PersistenceException {
		try {
			if (this.business != null) {
				this.business.validateCreateWithError(entity);
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

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public <ID extends Serializable, E extends Entity<ID>> E get(Class<E> clazz, ID id)
			throws ServiceException, BusinessException, PersistenceException {
		try {
			if (this.business != null) {
				this.business.validateRetrieveWithError(clazz, id);
			}
			return this.repository.get(clazz, id);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public <E extends Entity<?>> Collection<E> listAll(Class<E> clazz, String order) throws ServiceException,
			BusinessException, PersistenceException {
		try {
			if (clazz == null) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(
						AbstractBusiness.MESSAGE_INVALID_CLASS, clazz));
			}
			return this.repository.listAll(clazz, order);
		} catch (PersistenceException e) {
			throw e;
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public <E extends Entity<?>> Collection<E> listByExample(E entity, Map<String, Object> options)
			throws ServiceException, BusinessException, PersistenceException {
		try {
			if (this.business != null) {
				this.business.validateQueryWithError(entity);
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void update(Entity<?> entity) throws ServiceException, BusinessException, PersistenceException {
		try {
			if (this.business != null) {
				this.business.validateUpdateWithError(entity);
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void delete(Entity<?> entity) throws ServiceException, BusinessException, PersistenceException {
		try {
			if (this.business != null) {
				this.business.validateDeleteWithError(entity);
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

}
