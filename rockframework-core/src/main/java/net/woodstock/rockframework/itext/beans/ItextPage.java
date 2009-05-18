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
package net.woodstock.rockframework.itext.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ItextPage implements Serializable {

	private static final long		serialVersionUID	= -4202725975730236713L;

	private Collection<ItextBasic>	basicItems;

	private Collection<ItextObject>	items;

	public ItextPage() {
		super();
		this.basicItems = new ArrayList<ItextBasic>();
		this.items = new ArrayList<ItextObject>();
	}

	public void addBasicItem(ItextBasic item) {
		this.basicItems.add(item);
	}

	public void addItem(ItextObject item) {
		this.items.add(item);
	}

	public void removeBasicItem(int index) {
		((ArrayList<ItextBasic>) this.basicItems).remove(index);
	}

	public void removeBasicItem(ItextBasic item) {
		this.basicItems.remove(item);
	}

	public void removeItem(int index) {
		((ArrayList<ItextObject>) this.items).remove(index);
	}

	public void removeItem(ItextObject item) {
		this.items.remove(item);
	}

	// Getters and Setters
	public Collection<ItextBasic> getBasicItems() {
		return this.basicItems;
	}

	public void setBasicItems(Collection<ItextBasic> items) {
		if (this.basicItems.size() > 0) {
			while (this.basicItems.size() > 0) {
				((ArrayList<ItextBasic>) this.basicItems).remove(0);
			}
		}
		for (ItextBasic item : items) {
			this.basicItems.add(item);
		}
	}

	public Collection<ItextObject> getItems() {
		return this.items;
	}

	public void setItems(Collection<ItextObject> items) {
		if (this.items.size() > 0) {
			while (this.items.size() > 0) {
				((ArrayList<ItextObject>) this.items).remove(0);
			}
		}
		for (ItextObject item : items) {
			this.items.add(item);
		}
	}

}
