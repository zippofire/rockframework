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
package br.net.woodstock.rockframework.domain.persistence.orm.util;

import org.hibernate.HibernateException;
import org.hibernate.event.EventSource;
import org.hibernate.event.def.DefaultAutoFlushEventListener;

public class HibernateAutoFlushProblemPatchListener extends DefaultAutoFlushEventListener {

	private static final long	serialVersionUID	= -7413770767669684078L;

	// See: https://hibernate.onjira.com/browse/HHH-2763
	@Override
	protected void performExecutions(final EventSource session) {
		session.getPersistenceContext().setFlushing(true);
		try {
			session.getJDBCContext().getConnectionManager().flushBeginning();
			session.getActionQueue().prepareActions();
			session.getActionQueue().executeActions();
		} catch (HibernateException he) {
			throw he;
		} finally {
			session.getPersistenceContext().setFlushing(false);
			session.getJDBCContext().getConnectionManager().flushEnding();
		}
	}
}
