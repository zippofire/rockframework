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

import net.woodstock.rockframework.config.CoreLog;
import net.woodstock.rockframework.domain.service.Service;

import org.apache.commons.logging.Log;

public abstract class AbstractService implements Service {

	public AbstractService() {
		super();
	}

	protected Log getLog() {
		return CoreLog.getInstance().getLog();
	}

}
