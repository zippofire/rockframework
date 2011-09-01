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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.net.woodstock.rockframework.reflection.ClassFinder;
import br.net.woodstock.rockframework.reflection.impl.AnnotationClassFilter;
import br.net.woodstock.rockframework.reflection.impl.ClassFinderImpl;
import br.net.woodstock.rockframework.utils.ConditionUtils;


public abstract class MenuHelper {

	public static final char	MENU_SEPARATOR	= '/';

	private MenuHelper() {
		//
	}

	public static List<MenuItemBean> getMenu(final String baseName) {
		List<MenuItemBean> list = new ArrayList<MenuItemBean>();
		Map<String, MenuItemBean> cache = new HashMap<String, MenuItemBean>();
		ClassFinder finder = new ClassFinderImpl(baseName, new AnnotationClassFilter(MenuItem.class));
		Class<?>[] classes = finder.getClasses();

		for (Class<?> clazz : classes) {
			MenuItem menuItem = clazz.getAnnotation(MenuItem.class);
			MenuItemBean bean = new MenuItemBean(menuItem);
			MenuHelper.createItem(list, cache, bean);
		}

		return list;
	}

	private static MenuItemBean createItem(final List<MenuItemBean> collection, final Map<String, MenuItemBean> cache, final MenuItemBean tempBean) {
		int index = tempBean.getIndex();
		String name = tempBean.getName();
		String url = tempBean.getUrl();
		String description = tempBean.getDescription();
		String imageUrl = tempBean.getImageUrl();
		TargetType target = tempBean.getTarget();

		if (name.indexOf(MenuHelper.MENU_SEPARATOR) != -1) {
			MenuItemBean bean = MenuHelper.createParent(collection, cache, tempBean);
			return bean;
		}

		MenuItemBean bean = new MenuItemBean(index, name, url, description, imageUrl, target);
		cache.put(name, bean);
		collection.add(bean);
		return bean;
	}

	private static MenuItemBean createParent(final List<MenuItemBean> collection, final Map<String, MenuItemBean> cache, final MenuItemBean tempBean) {
		int index = tempBean.getIndex();
		String name = tempBean.getName();
		String url = tempBean.getUrl();
		String description = tempBean.getDescription();
		String imageUrl = tempBean.getImageUrl();
		TargetType target = tempBean.getTarget();

		if (name.indexOf(MenuHelper.MENU_SEPARATOR) != -1) {
			String currentName = name.substring(name.lastIndexOf(MenuHelper.MENU_SEPARATOR) + 1);
			if (cache.containsKey(currentName)) {
				MenuItemBean bean = cache.get(currentName);
				return bean;
			}

			MenuItemBean bean = MenuHelper.createParentTree(collection, cache, new MenuItemBean(index, name, url, description, imageUrl, target));
			return bean;
		}

		if (!cache.containsKey(name)) {
			MenuHelper.createItem(collection, cache, new MenuItemBean(index, name, url, description, imageUrl, target));
		}
		MenuItemBean bean = cache.get(name);
		return bean;
	}

	private static MenuItemBean createParentTree(final List<MenuItemBean> collection, final Map<String, MenuItemBean> cache, final MenuItemBean tempBean) {
		int index = tempBean.getIndex();
		String name = tempBean.getName();
		String url = tempBean.getUrl();
		String description = tempBean.getDescription();
		String imageUrl = tempBean.getImageUrl();
		TargetType target = tempBean.getTarget();

		String currentName = name.substring(name.lastIndexOf(MenuHelper.MENU_SEPARATOR) + 1);
		String parentName = name.substring(0, name.lastIndexOf(MenuHelper.MENU_SEPARATOR));

		MenuItemBean parent = MenuHelper.createParent(collection, cache, new MenuItemBean(index, parentName, null, description, imageUrl, target));
		MenuItemBean bean = new MenuItemBean(index, currentName, url, description, imageUrl, target);

		parent.getChilds().add(bean);
		cache.put(currentName, bean);
		return bean;
	}

	public static boolean isValid(final MenuItemBean m) {
		if (m == null) {
			return false;
		}
		if (ConditionUtils.isNotEmpty(m.getUrl())) {
			return true;
		}
		if ((m.getChilds() != null) && (m.getChilds().size() > 0)) {
			for (MenuItemBean mm : m.getChilds()) {
				boolean b = MenuHelper.isValid(mm);
				if (b) {
					return true;
				}
			}
		}
		return false;
	}

	public static List<MenuItemBean> sort(final List<MenuItemBean> items) {
		List<MenuItemBean> ordered = new ArrayList<MenuItemBean>();
		ordered.addAll(items);

		Collections.sort(ordered, new MenuItemComparator());

		return ordered;
	}
}
