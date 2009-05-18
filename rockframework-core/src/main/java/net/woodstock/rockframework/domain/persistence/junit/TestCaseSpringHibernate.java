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
package net.woodstock.rockframework.domain.persistence.junit;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public abstract class TestCaseSpringHibernate extends TestCase {

	private SessionFactory	factory;

	public abstract SessionFactory getSessionFactory();

	@Override
	protected void setUp() throws Exception {
		this.factory = this.getSessionFactory();
		Session session = SessionFactoryUtils.getSession(this.factory, true);
		TransactionSynchronizationManager.bindResource(this.factory, new SessionHolder(session));
	}

	@Override
	protected void tearDown() throws Exception {
		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
				.unbindResource(this.factory);
		SessionFactoryUtils.closeSession(sessionHolder.getSession());
	}

}
