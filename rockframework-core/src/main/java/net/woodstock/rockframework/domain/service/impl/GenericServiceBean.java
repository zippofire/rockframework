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

import net.woodstock.rockframework.domain.business.GenericBusiness;
import net.woodstock.rockframework.domain.persistence.GenericRepository;
import net.woodstock.rockframework.domain.service.GenericService;

abstract class GenericServiceBean extends AbstractService implements GenericService {

	private GenericBusiness		business;

	private GenericRepository	repository;

	public GenericServiceBean() {
		super();
	}

	// Objects
	protected GenericBusiness getBusiness() {
		if (this.business == null) {
			throw new IllegalStateException("Business is null");
		}
		return this.business;
	}

	public void setBusiness(GenericBusiness business) {
		this.business = business;
	}

	protected GenericRepository getRepository() {
		if (this.repository == null) {
			throw new IllegalStateException("Repository is null");
		}
		return this.repository;
	}

	public void setRepository(GenericRepository repository) {
		this.repository = repository;
	}

}
