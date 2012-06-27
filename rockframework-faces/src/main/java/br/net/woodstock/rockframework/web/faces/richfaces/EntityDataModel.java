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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.model.DataVisitResult;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;

import br.net.woodstock.rockframework.domain.Entity;
import br.net.woodstock.rockframework.persistence.orm.Page;
import br.net.woodstock.rockframework.persistence.orm.QueryResult;
import br.net.woodstock.rockframework.persistence.orm.impl.PageImpl;
import br.net.woodstock.rockframework.util.Assert;

public class EntityDataModel<E extends Entity<Integer>> extends BaseExtendedDataModel<E> {

	private static final long	serialVersionUID	= -1013531104655352691L;

	private int					rows;

	private EntityRepository	repository;

	private QueryResult			queryResult;

	private Object				currentId;

	private Map<Object, E>		wrappedData			= new HashMap<Object, E>();

	private List<Object>		wrappedKeys			= null;

	public EntityDataModel(final int rows, final EntityRepository repository) {
		super();
		Assert.greaterThan(rows, 0, "rows");
		Assert.notNull(repository, "repository");
		this.rows = rows;
		this.repository = repository;
	}

	@Override
	public void walk(final FacesContext context, final DataVisitor visitor, final Range range, final Object argument) {
		Page page = this.toPage(range);

		if ((this.queryResult == null) || (!page.equals(this.queryResult.getCurrentPage()))) {
			this.queryResult = this.repository.getResult(page);
		}

		DataVisitResult visitResult = null;

		this.wrappedKeys = new ArrayList<Object>();
		this.wrappedData = new HashMap<Object, E>();

		Collection<E> collection = this.queryResult.getResult();
		for (E e : collection) {
			this.wrappedKeys.add(e.getId());
			this.wrappedData.put(e.getId(), e);
			visitResult = visitor.process(context, e.getId(), argument);
			if (DataVisitResult.STOP.equals(visitResult)) {
				break;
			}
		}
	}

	@Override
	public boolean isRowAvailable() {
		if (this.currentId == null) {
			return false;
		}
		return true;
	}

	@Override
	public int getRowCount() {
		if (this.queryResult == null) {
			Page page = new PageImpl(1, this.rows);
			this.queryResult = this.repository.getResult(page);
		}

		return this.queryResult.getTotal();
	}

	@Override
	@SuppressWarnings("unchecked")
	public E getRowData() {
		if (this.currentId == null) {
			return null;
		}
		E e = this.wrappedData.get(this.currentId);
		if (e == null) {
			e = (E) this.repository.getEntity(this.currentId);
			this.wrappedData.put(this.currentId, e);
		}
		return e;
	}

	@Override
	public Object getRowKey() {
		return this.currentId;
	}

	@Override
	public void setRowKey(final Object key) {
		this.currentId = key;
	}

	protected Page toPage(final Range range) {
		if (range instanceof SequenceRange) {
			SequenceRange sequenceRange = (SequenceRange) range;
			if ((sequenceRange.getFirstRow() >= 0) && (sequenceRange.getRows() >= 0)) {
				int div = sequenceRange.getFirstRow() / sequenceRange.getRows();

				div++;

				Page page = new PageImpl(div, sequenceRange.getRows());
				return page;
			}

		}
		return null;
	}

}
