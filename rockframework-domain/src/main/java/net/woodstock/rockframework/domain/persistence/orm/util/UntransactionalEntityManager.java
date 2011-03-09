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
package net.woodstock.rockframework.domain.persistence.orm.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class UntransactionalEntityManager extends DelegateEntityManager {

	public UntransactionalEntityManager(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public EntityTransaction getTransaction() {
		// throw new UnsupportedOperationException();
		return new EmptyEntityTransaction();
	}

	public static class EmptyEntityTransaction implements EntityTransaction {

		@Override
		public void begin() {
			//
		}

		@Override
		public void commit() {
			//
		}

		@Override
		public void rollback() {
			//
		}

		@Override
		public boolean isActive() {
			return false;
		}

		@Override
		public void setRollbackOnly() {
			//
		}

		@Override
		public boolean getRollbackOnly() {
			return false;
		}

	}

}
