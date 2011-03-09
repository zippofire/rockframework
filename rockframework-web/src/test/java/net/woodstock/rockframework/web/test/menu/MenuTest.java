package net.woodstock.rockframework.web.test.menu;

import java.util.List;

import junit.framework.TestCase;
import net.woodstock.rockframework.web.menu.JSCookieMenuTranformer;
import net.woodstock.rockframework.web.menu.JSCookieMenuTranformer.JSCookieMenuType;
import net.woodstock.rockframework.web.menu.MenuHelper;
import net.woodstock.rockframework.web.menu.MenuItemBean;
import net.woodstock.rockframework.web.menu.MenuTransformer;
import net.woodstock.rockframework.web.menu.SimpleULMenuTransformer;

public class MenuTest extends TestCase {

	public void test1() throws Exception {
		List<MenuItemBean> menus = MenuHelper.getMenu("net.woodstock.rockframework.web.test.menu");
		for (MenuItemBean menu : menus) {
			System.out.println(menu.getName() + " - " + menu.getUrl());
			for (MenuItemBean submenu : menu.getChilds()) {
				System.out.println("\t" + submenu.getName() + " - " + submenu.getUrl());
				for (MenuItemBean submenu2 : submenu.getChilds()) {
					System.out.println("\t\t" + submenu2.getName() + " - " + submenu2.getUrl());
				}
			}
		}

		MenuTransformer transformer = new JSCookieMenuTranformer("menu", JSCookieMenuType.HBR);
		System.out.println(transformer.toText(menus));

		transformer = new SimpleULMenuTransformer();
		System.out.println(transformer.toText(menus));
	}

}
