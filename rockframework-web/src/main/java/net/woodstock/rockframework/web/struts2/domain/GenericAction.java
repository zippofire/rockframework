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
package net.woodstock.rockframework.web.struts2.domain;

import java.util.Collection;

import net.woodstock.rockframework.domain.DomainException;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.service.GenericService;
import net.woodstock.rockframework.web.config.WebLog;
import net.woodstock.rockframework.web.struts2.Action;
import net.woodstock.rockframework.web.struts2.Constants;

@SuppressWarnings("rawtypes")
public abstract class GenericAction<E extends Entity> extends Action {

	private static final long	serialVersionUID	= -8494600394343655188L;

	private E					entity;

	private Collection<E>		entities;

	public GenericAction() {
		super();
		this.entity = this.getEmptyEntity();
	}

	public String delete() {
		try {
			this.getGenericService().delete(this.entity);
			this.addActionMessage(this.getSuccessMessage());
			this.entity = this.getEmptyEntity();
			return Constants.SUCCESS;
		} catch (DomainException e) {
			WebLog.getInstance().getLog().error(e.getMessage(), e);
			this.addActionError(e.getMessage());
			return Constants.ERROR;
		}
	}

	@Override
	public String execute() {
		try {
			if (this.entity.getId() != null) {
				this.entity = this.getGenericService().get(this.entity);
			}
			return Constants.INPUT;
		} catch (DomainException e) {
			WebLog.getInstance().getLog().error(e.getMessage(), e);
			this.addActionError(e.getMessage());
			return Constants.ERROR;
		}
	}

	public String list() {
		try {
			this.entities = this.getGenericService().listByExample(this.entity, null);
			return Constants.SUCCESS;
		} catch (DomainException e) {
			WebLog.getInstance().getLog().error(e.getMessage(), e);
			this.addActionError(e.getMessage());
			return Constants.ERROR;
		}
	}

	public String listAll() {
		try {
			this.entities = this.getGenericService().listAll(this.entity, null);
			return Constants.SUCCESS;
		} catch (DomainException e) {
			WebLog.getInstance().getLog().error(e.getMessage(), e);
			this.addActionError(e.getMessage());
			return Constants.ERROR;
		}
	}

	public String save() {
		try {
			this.getGenericService().save(this.entity);
			this.entity = this.getEmptyEntity();
			this.addActionMessage(this.getSuccessMessage());
			return Constants.SUCCESS;
		} catch (DomainException e) {
			WebLog.getInstance().getLog().error(e.getMessage(), e);
			this.addActionError(e.getMessage());
			return Constants.ERROR;
		}
	}

	public String update() {
		try {
			this.getGenericService().update(this.entity);
			this.addActionMessage(this.getSuccessMessage());
			return Constants.SUCCESS;
		} catch (DomainException e) {
			WebLog.getInstance().getLog().error(e.getMessage(), e);
			this.addActionError(e.getMessage());
			return Constants.ERROR;
		}
	}

	public E getEntity() {
		return this.entity;
	}

	public void setEntity(final E entity) {
		this.entity = entity;
	}

	public Collection<E> getEntities() {
		return this.entities;
	}

	public void setEntities(final Collection<E> entities) {
		this.entities = entities;
	}

	// Aux
	protected abstract E getEmptyEntity();

	protected abstract GenericService getGenericService();

	protected abstract String getSuccessMessage();

}
