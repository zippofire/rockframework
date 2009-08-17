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
package net.woodstock.rockframework.domain.business;

import java.io.Serializable;

import net.woodstock.rockframework.domain.Entity;

public interface GenericBusiness extends Business {

	// Generic
	boolean validateCreate(Entity<?> entity);

	boolean validateUpdate(Entity<?> pojo);

	boolean validateDelete(Entity<?> pojo);

	boolean validateQuery(Entity<?> pojo);

	// Validation
	<ID extends Serializable, E extends Entity<ID>> boolean validateRetrieve(Class<E> clazz, ID id);

	// Validation With Error
	void validateCreateWithError(Entity<?> pojo) throws BusinessException;

	void validateUpdateWithError(Entity<?> pojo) throws BusinessException;

	void validateDeleteWithError(Entity<?> pojo) throws BusinessException;

	void validateQueryWithError(Entity<?> pojo) throws BusinessException;

	<ID extends Serializable, E extends Entity<ID>> void validateRetrieveWithError(Class<E> clazz, ID id) throws BusinessException;

}
