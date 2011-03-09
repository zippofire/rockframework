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

import net.woodstock.rockframework.utils.ConditionUtils;

public class JSCookieMenuTranformer implements MenuTransformer {

	private static final String	TAB	= "    ";

	private static final String	NL	= "\n";

	private String				id;

	private JSCookieMenuType	type;

	public JSCookieMenuTranformer(final String id, final JSCookieMenuType type) {
		super();
		this.id = id;
		this.type = type;
	}

	@Override
	public String toText(final List<MenuItemBean> list) {
		String propId = "prop_" + this.id;

		StringBuilder builder = new StringBuilder();

		builder.append("<div id=\"" + this.id + "\"></div>" + JSCookieMenuTranformer.NL);
		builder.append("<script type=\"text/javascript\">" + JSCookieMenuTranformer.NL);
		builder.append(JSCookieMenuTranformer.TAB + "var " + this.id + " = [" + JSCookieMenuTranformer.NL);

		builder.append(this.addItems(list, JSCookieMenuTranformer.TAB + JSCookieMenuTranformer.TAB));

		builder.append(JSCookieMenuTranformer.TAB + "];" + JSCookieMenuTranformer.NL);

		builder.append(JSCookieMenuTranformer.TAB + "var " + propId + " = cmClone (cmThemeOffice);" + JSCookieMenuTranformer.NL);
		builder.append(JSCookieMenuTranformer.TAB + propId + ".effect = new CMSlidingEffect (28);" + JSCookieMenuTranformer.NL);
		builder.append(JSCookieMenuTranformer.TAB + "cmDraw ('" + this.id + "', " + this.id + ", '" + this.type.getType() + "', " + propId + ", 'ThemeOffice');" + JSCookieMenuTranformer.NL);
		builder.append("</script>" + JSCookieMenuTranformer.NL);

		return builder.toString();
	}

	private String addItems(final List<MenuItemBean> list, final String tab) {
		if ((list == null) || (list.size() == 0)) {
			return "";
		}
		StringBuilder builder = new StringBuilder();

		int index = 0;
		int size = list.size();

		for (MenuItemBean m : MenuHelper.sort(list)) {
			if (!MenuHelper.isValid(m)) {
				continue;
			}
			builder.append(tab);
			builder.append("[");

			// Image
			if (ConditionUtils.isNotEmpty(m.getImageUrl())) {
				builder.append("'" + m.getImageUrl() + "'");
			} else {
				builder.append("null");
			}
			builder.append(", ");

			// Name
			builder.append("'" + m.getName() + "', ");

			// Page
			builder.append("'" + m.getUrl() + "'");
			builder.append(", ");

			// Target
			builder.append("'" + m.getTarget().getTarget() + "', ");

			// Description
			if (ConditionUtils.isNotEmpty(m.getDescription())) {
				builder.append("'" + m.getDescription() + "'");
			} else {
				builder.append("null");
			}

			if ((m.getChilds() != null) && (m.getChilds().size() > 0)) {
				builder.append(", " + JSCookieMenuTranformer.NL);
				builder.append(this.addItems(m.getChilds(), tab + JSCookieMenuTranformer.TAB));
				builder.append(tab + "]");
			} else {
				builder.append("]");
			}

			index++;
			if (index < size) {
				builder.append(",");
			}
			builder.append(JSCookieMenuTranformer.NL);
		}

		return builder.toString();
	}

	public static enum JSCookieMenuType {
		HBR("hbr"), HBL("hbl"), HUR("hur"), HUL("hul"), VBR("vbr"), VBL("vbl"), VUR("vur"), VUL("vul");

		private String	type;

		private JSCookieMenuType(final String type) {
			this.type = type;
		}

		public String getType() {
			return this.type;
		}
	}

}
