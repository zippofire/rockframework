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
package br.net.woodstock.rockframework.web.faces.richfaces;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;

public abstract class BaseExtendedDataModel<T> extends ExtendedDataModel<T> implements Serializable {

	private static final long	serialVersionUID	= -3271960339474636240L;

	@Override
	public void setRowKey(final Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getRowKey() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void walk(final FacesContext context, final DataVisitor visitor, final Range range, final Object argument) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRowAvailable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRowCount() {
		throw new UnsupportedOperationException();
	}

	@Override
	public T getRowData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRowIndex() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRowIndex(final int rowIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getWrappedData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWrappedData(final Object data) {
		throw new UnsupportedOperationException();
	}

}
