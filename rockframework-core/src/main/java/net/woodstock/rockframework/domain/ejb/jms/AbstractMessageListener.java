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
package net.woodstock.rockframework.domain.ejb.jms;

import javax.annotation.Resource;
import javax.ejb.MessageDrivenContext;
import javax.jms.MessageListener;

import net.woodstock.rockframework.sys.SysLogger;

import org.apache.commons.logging.Log;

public abstract class AbstractMessageListener implements MessageListener {

	@Resource
	private MessageDrivenContext	context;

	protected MessageDrivenContext getContext() {
		return this.context;
	}

	protected Log getLogger() {
		return SysLogger.getLogger();
	}

}