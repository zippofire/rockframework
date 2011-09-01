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

import java.util.List;

import br.net.woodstock.rockframework.xml.dom.XmlDocument;
import br.net.woodstock.rockframework.xml.dom.XmlElement;


public abstract class AbstractULMenuTransformer implements MenuTransformer {

	@Override
	public String toText(final List<MenuItemBean> list) {
		XmlDocument document = new XmlDocument("ul");
		XmlElement root = document.getRoot();
		for (MenuItemBean bean : list) {
			XmlElement e = root.addElement("li");
			this.setElementData(e, bean);
			if ((bean.getChilds() != null) && (bean.getChilds().size() > 0)) {
				this.setChilds(e, bean.getChilds());
			}
		}
		String str = document.toString();
		return str.substring(str.indexOf('\n') + 1);
	}

	private void setChilds(final XmlElement parent, final List<MenuItemBean> list) {
		XmlElement ul = parent.addElement("ul");
		for (MenuItemBean bean : list) {
			XmlElement e = ul.addElement("li");
			this.setElementData(e, bean);
			if ((bean.getChilds() != null) && (bean.getChilds().size() > 0)) {
				this.setChilds(e, bean.getChilds());
			}
		}
	}

	protected abstract void setElementData(XmlElement parent, MenuItemBean bean);

}
