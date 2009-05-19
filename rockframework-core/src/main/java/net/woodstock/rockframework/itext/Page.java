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
package net.woodstock.rockframework.itext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Page implements Serializable {

	private static final long	serialVersionUID	= -4202725975730236713L;

	private Collection<Object>	items;

	public Page() {
		super();
		this.items = new ArrayList<Object>();
	}

	public void addItem(Object item) {
		this.items.add(item);
	}

	public void removeItem(int index) {
		((ArrayList<Object>) this.items).remove(index);
	}

	public void removeItem(Object item) {
		this.items.remove(item);
	}

	// Getters and Setters
	public Collection<Object> getItems() {
		return this.items;
	}

	public void setItems(Collection<Object> items) {
		if (this.items.size() > 0) {
			while (this.items.size() > 0) {
				((ArrayList<Object>) this.items).remove(0);
			}
		}
		for (Object item : items) {
			this.items.add(item);
		}
	}

}
