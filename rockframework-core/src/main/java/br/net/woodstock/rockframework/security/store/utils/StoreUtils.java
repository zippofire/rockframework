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
package br.net.woodstock.rockframework.security.store.utils;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntry;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.util.Assert;

public abstract class StoreUtils {

	private StoreUtils() {
		//
	}

	public static void copy(final Store from, final Store to) {
		Assert.notNull(from, "from");
		Assert.notNull(to, "to");

		for (Alias alias : from.aliases()) {
			StoreEntry entry = null;

			entry = from.get(alias, StoreEntryType.CERTIFICATE);
			if (entry != null) {
				to.add(entry);
			}
			entry = from.get(alias, StoreEntryType.PRIVATE_KEY);
			if (entry != null) {
				to.add(entry);
			}
			entry = from.get(alias, StoreEntryType.PUBLIC_KEY);
			if (entry != null) {
				to.add(entry);
			}
		}
	}

}
