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
package br.net.woodstock.rockframework.persistence.orm.impl;

import br.net.woodstock.rockframework.persistence.orm.Page;
import br.net.woodstock.rockframework.util.Assert;
import br.net.woodstock.rockframework.utils.ObjectUtils;

public class PageImpl implements Page {

	private static final long	serialVersionUID	= 7354276379033649145L;

	private int					pageNumber;

	private int					resultsPerPage;

	public PageImpl() {
		super();
	}

	public PageImpl(final int pageNumber, final int resultsPerPage) {
		super();
		Assert.greaterThan(pageNumber, 0, "pageNumber");
		Assert.greaterThan(resultsPerPage, 0, "resultsPerPage");
		this.pageNumber = pageNumber;
		this.resultsPerPage = resultsPerPage;
	}

	@Override
	public int getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(final int pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public int getResultsPerPage() {
		return this.resultsPerPage;
	}

	public void setResultsPerPage(final int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (ObjectUtils.HASH_PRIME * result) + this.pageNumber;
		result = (ObjectUtils.HASH_PRIME * result) + this.resultsPerPage;
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		PageImpl other = (PageImpl) obj;
		if (this.pageNumber != other.pageNumber) {
			return false;
		}
		if (this.resultsPerPage != other.resultsPerPage) {
			return false;
		}
		return true;
	}

}
