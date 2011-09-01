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
package br.net.woodstock.rockframework.domain.util;

import java.io.Serializable;

import br.net.woodstock.rockframework.domain.Pojo;
import br.net.woodstock.rockframework.util.Entry;


public class KeyValue<K extends Serializable, V extends Serializable> extends Entry<K, V> implements Pojo {

	private static final long	serialVersionUID	= 611990085341006973L;

	public KeyValue() {
		super();
	}

	public KeyValue(final K key, final V value) {
		super(key, value);
	}

	public KeyValue(final Entry<K, V> entry) {
		super(entry.getKey(), entry.getValue());
	}

}
