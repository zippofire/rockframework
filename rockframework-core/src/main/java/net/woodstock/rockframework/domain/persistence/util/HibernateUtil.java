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

import net.woodstock.rockframework.config.CoreConfig;
import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.utils.StringUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public abstract class HibernateUtil {

	private static final String			HIBERNATE_ANNOTATION_PROPERTY	= "hibernate.annotation";

	private static ThreadLocal<Session>	session							= new ThreadLocal<Session>();

	private static boolean				annotation;

	private static SessionFactory		factory;

	private HibernateUtil() {
		//
	}

	public static Session getSession() {
		if (HibernateUtil.factory == null) {
			String s = CoreConfig.getInstance().getValue(HibernateUtil.HIBERNATE_ANNOTATION_PROPERTY);
			if (!StringUtils.isEmpty(s)) {
				HibernateUtil.annotation = Boolean.parseBoolean(s);
			} else {
				HibernateUtil.annotation = true;
			}
			if (HibernateUtil.annotation) {
				HibernateUtil.factory = new AnnotationConfiguration().configure().buildSessionFactory();
			} else {
				HibernateUtil.factory = new Configuration().configure().buildSessionFactory();
			}
		}
		Session s = session.get();
		if (s == null) {
			s = HibernateUtil.factory.openSession();
			session.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = session.get();
		if (s != null) {
			Transaction t = s.getTransaction();
			if (t.isActive()) {
				SysLogger.getLogger().warn("Session contains an active transaction, commiting transaction");
				t.commit();
			}
			s.flush();
			s.close();
			session.set(null);
		}
	}

}
