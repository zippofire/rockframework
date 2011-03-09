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
package net.woodstock.rockframework.web.menu;

import java.util.List;

import javax.servlet.ServletContextEvent;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.web.JSPException;
import net.woodstock.rockframework.web.listener.AbstractServletContextListener;

public class MenuListener extends AbstractServletContextListener {

	private static final String	BASENAME_PARAM			= "net.woodstock.rockframework.web.menu.BASENAME";

	private static final String	TRANSFORM_PARAM			= "net.woodstock.rockframework.web.menu.TRANSFORM";

	private static final String	MENU_ATTRIBUTE_PARAM	= "net.woodstock.rockframework.web.menu.MENU_ATTRIBUTE";

	@SuppressWarnings("unchecked")
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		try {
			super.contextInitialized(event);
			String baseName = event.getServletContext().getInitParameter(MenuListener.BASENAME_PARAM);
			String transform = event.getServletContext().getInitParameter(MenuListener.TRANSFORM_PARAM);
			String menuAttribute = event.getServletContext().getInitParameter(MenuListener.MENU_ATTRIBUTE_PARAM);
			Assert.notEmpty(baseName, MenuListener.BASENAME_PARAM);
			Assert.notEmpty(transform, MenuListener.TRANSFORM_PARAM);
			Assert.notEmpty(menuAttribute, MenuListener.MENU_ATTRIBUTE_PARAM);

			Class<MenuTransformer> clazz = (Class<MenuTransformer>) Class.forName(transform);
			MenuTransformer transformer = clazz.newInstance();
			List<MenuItemBean> menu = MenuHelper.getMenu(baseName);
			String menuStr = transformer.toText(menu);
			event.getServletContext().setAttribute(menuAttribute, menuStr);
		} catch (Exception e) {
			throw new JSPException(e);
		}
	}

}
