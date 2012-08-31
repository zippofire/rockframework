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
package br.net.woodstock.rockframework.domain.spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import br.net.woodstock.rockframework.domain.config.DomainLog;

public class AbstractScope implements Scope {

	@Override
	public Object get(final String name, final ObjectFactory<?> objectFactory) {
		DomainLog.getInstance().getLogger().warn("This method must be overrided");
		return null;
	}

	@Override
	public String getConversationId() {
		DomainLog.getInstance().getLogger().warn("This method must be overrided");
		return null;
	}

	@Override
	public void registerDestructionCallback(final String name, final Runnable callback) {
		DomainLog.getInstance().getLogger().warn("This method must be overrided");
	}

	@Override
	public Object remove(final String name) {
		DomainLog.getInstance().getLogger().warn("This method must be overrided");
		return null;
	}

	@Override
	public Object resolveContextualObject(final String key) {
		DomainLog.getInstance().getLogger().warn("This method must be overrided");
		return null;
	}

}
