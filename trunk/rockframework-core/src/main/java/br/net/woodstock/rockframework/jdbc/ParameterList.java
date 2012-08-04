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
package br.net.woodstock.rockframework.jdbc;

import java.util.LinkedList;

public class ParameterList extends LinkedList<Parameter> {

	private static final long	serialVersionUID	= 17529574718383571L;

	public ParameterList() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		boolean first = true;
		s.append("[");
		for (Parameter p : this) {
			if (first) {
				s.append(p.toString());
				first = false;
			} else {
				s.append(", ");
				s.append(p.toString());
			}
		}
		s.append("]");
		return s.toString();
	}

}
