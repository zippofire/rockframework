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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItemBean implements Serializable {

	private static final long	serialVersionUID	= -1145043382914846076L;

	private int					index;

	private String				name;

	private String				url;

	private String				description;

	private String				imageUrl;

	private TargetType			target;

	private List<MenuItemBean>	childs;

	MenuItemBean(final MenuItem menuItem) {
		super();
		this.index = menuItem.index();
		this.name = menuItem.name();
		this.url = menuItem.url();
		this.description = menuItem.description();
		this.imageUrl = menuItem.imageUrl();
		this.target = menuItem.target();
		this.childs = new ArrayList<MenuItemBean>();
	}

	public MenuItemBean(final int index, final String name, final String url, final String description, final String imageUrl, final TargetType target) {
		super();
		this.index = index;
		this.name = name;
		this.url = url;
		this.description = description;
		this.imageUrl = imageUrl;
		this.target = target;
		this.childs = new ArrayList<MenuItemBean>();
	}

	public int getIndex() {
		return this.index;
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public TargetType getTarget() {
		return this.target;
	}

	public void setTarget(final TargetType target) {
		this.target = target;
	}

	public List<MenuItemBean> getChilds() {
		return this.childs;
	}

	public void setChilds(final List<MenuItemBean> childs) {
		this.childs = childs;
	}

}
