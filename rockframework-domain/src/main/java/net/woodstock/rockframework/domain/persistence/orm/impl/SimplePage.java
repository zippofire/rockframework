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
package net.woodstock.rockframework.domain.persistence.orm.impl;

import net.woodstock.rockframework.domain.persistence.orm.Page;

public class SimplePage implements Page {

	private static final long	serialVersionUID	= -1686862188704202461L;

	private int					maxResults;

	private int					currentPage;

	public SimplePage(final int maxResults) {
		this(maxResults, 0);
	}

	public SimplePage(final int maxResults, final int currentPage) {
		super();
		this.maxResults = maxResults;
		this.currentPage = currentPage;
	}

	@Override
	public int getMaxResults() {
		return this.maxResults;
	}

	@Override
	public int getCurrentPage() {
		return this.currentPage;
	}

	// Aux
	@Override
	public int getFirstResult() {
		int firstResult = this.maxResults * this.currentPage;
		return firstResult;
	}

	@Override
	public Page getNext() {
		Page p = new SimplePage(this.maxResults, this.currentPage + 1);
		return p;
	}

	@Override
	public Page getPrevious() {
		if (this.currentPage > 0) {
			Page p = new SimplePage(this.maxResults, this.currentPage - 1);
			return p;
		}
		Page p = new SimplePage(this.maxResults, 0);
		return p;
	}

}
