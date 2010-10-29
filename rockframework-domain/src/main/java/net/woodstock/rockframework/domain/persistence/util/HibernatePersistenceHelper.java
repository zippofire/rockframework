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
package net.woodstock.rockframework.domain.persistence.util;

import net.woodstock.rockframework.domain.config.DomainConfig;
import net.woodstock.rockframework.domain.config.DomainLog;
import net.woodstock.rockframework.utils.StringUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public final class HibernatePersistenceHelper implements PersistenceHelper<Session> {

	private static HibernatePersistenceHelper	instance						= new HibernatePersistenceHelper();

	private static ThreadLocal<Session>			session							= new ThreadLocal<Session>();

	private static final String					HIBERNATE_ANNOTATION_PROPERTY	= "hibernate.annotation";

	private boolean								annotation;

	private SessionFactory						factory;

	private HibernatePersistenceHelper() {
		super();
		String s = DomainConfig.getInstance().getValue(HibernatePersistenceHelper.HIBERNATE_ANNOTATION_PROPERTY);
		if (StringUtils.isNotEmpty(s)) {
			this.annotation = Boolean.parseBoolean(s);
		} else {
			this.annotation = true;
		}
		if (this.annotation) {
			this.factory = new AnnotationConfiguration().configure().buildSessionFactory();
		} else {
			this.factory = new Configuration().configure().buildSessionFactory();
		}
	}

	@Override
	public void close() {
		Session s = HibernatePersistenceHelper.session.get();
		if (s != null) {
			Transaction t = s.getTransaction();
			if (t.isActive()) {
				DomainLog.getInstance().getLog().warn("Session contains an active transaction, commiting transaction");
				t.commit();
			}
			s.flush();
			s.close();
			HibernatePersistenceHelper.session.set(null);
		}
	}

	@Override
	public Session get() {
		Session s = HibernatePersistenceHelper.session.get();
		if (s == null) {
			s = this.factory.openSession();
			HibernatePersistenceHelper.session.set(s);
		}
		return s;
	}

	public static HibernatePersistenceHelper getInstance() {
		return instance;
	}
}
