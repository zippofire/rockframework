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
package br.net.woodstock.rockframework.web.menu;

import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.xml.dom.XmlElement;

public class SimpleULMenuTransformer extends AbstractULMenuTransformer {

	@Override
	protected void setElementData(final XmlElement parent, final MenuItemBean bean) {
		XmlElement e = parent.addElement("a");
		e.setData(bean.getName());
		if (ConditionUtils.isNotEmpty(bean.getUrl())) {
			e.setAttribute("href", bean.getUrl());
		}
	}

}
