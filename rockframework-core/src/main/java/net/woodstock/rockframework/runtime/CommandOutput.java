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
package net.woodstock.rockframework.runtime;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

public class CommandOutput implements Serializable {

	private static final long	serialVersionUID	= -6634828891946568609L;

	private Collection<String>	out;

	private Collection<String>	err;

	public CommandOutput() {
		this.out = new LinkedList<String>();
		this.err = new LinkedList<String>();
	}

	public Collection<String> getOut() {
		return this.out;
	}

	public Collection<String> getErr() {
		return this.err;
	}

	void addOut(final String s) {
		this.out.add(s);
	}

	void addErr(final String s) {
		this.err.add(s);
	}

}
