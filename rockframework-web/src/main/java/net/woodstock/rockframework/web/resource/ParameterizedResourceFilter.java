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
package net.woodstock.rockframework.web.resource;

public class ParameterizedResourceFilter extends AbstractResourceFilter {

	private static final long	serialVersionUID			= -8118171525668671964L;

	private static final String	RESOURCE_MANAGER_PARAMETER	= "resourceManager";

	public ParameterizedResourceFilter() {
		super();

	}

	@SuppressWarnings("rawtypes")
	@Override
	public void init() {
		super.init();
		try {
			String clazzName = this.getInitParameter(ParameterizedResourceFilter.RESOURCE_MANAGER_PARAMETER);
			Class clazz = Class.forName(clazzName);
			ResourceManager resourceManager = (ResourceManager) clazz.newInstance();
			this.setResourceManager(resourceManager);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
