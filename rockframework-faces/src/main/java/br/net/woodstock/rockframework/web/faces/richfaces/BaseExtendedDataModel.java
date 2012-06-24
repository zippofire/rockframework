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
