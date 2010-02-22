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
package net.woodstock.rockframework.web.wicket;

import net.woodstock.rockframework.web.config.WebLog;

import org.apache.commons.logging.Log;
import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

public abstract class AbstractApplication extends WebApplication {

	public AbstractApplication() {
		super();
	}

	@Override
	public abstract Class<? extends Page> getHomePage();

	@Override
	public abstract String getConfigurationType();

	// Log
	protected Log getLog() {
		return WebLog.getInstance().getLog();
	}

	public static enum ConfigurationType {
		DEPLOYMENT(Application.DEPLOYMENT), DEVELOPMENT(Application.DEVELOPMENT);

		private String	type;

		private ConfigurationType(final String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}
	}

}
