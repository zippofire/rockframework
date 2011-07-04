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

class OutputImpl implements Output {

	private static final long	serialVersionUID	= -5028254907646518037L;

	private String				out;

	private String				err;

	public OutputImpl(final String out, final String err) {
		super();
		this.out = out;
		this.err = err;
	}

	@Override
	public String getOut() {
		return this.out;
	}

	@Override
	public String getErr() {
		return this.err;
	}

}
