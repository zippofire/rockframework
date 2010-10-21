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
package net.woodstock.rockframework.domain.junit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;

public abstract class TestCaseSpringHibernate extends TestCaseSpring {

	protected abstract SessionFactory getSessionFactory();

	private Session	session;

	@Override
	protected void setUp() throws Exception {
		SessionFactory factory = this.getSessionFactory();
		this.session = SessionFactoryUtils.getSession(factory, true);
		this.bind(factory, new SessionHolder(this.session));
	}

	@Override
	protected void tearDown() throws Exception {
		SessionFactory factory = this.getSessionFactory();
		SessionHolder sessionHolder = (SessionHolder) this.unbind(factory);
		SessionFactoryUtils.closeSession(sessionHolder.getSession());
	}

	public Session getSession() {
		return this.session;
	}

}
