package net.woodstock.rockframework.domain.test.test1;

import net.woodstock.rockframework.domain.persistence.query.impl.EJBQLQueryBuilder;

public class TestQueryBuilder extends EJBQLQueryBuilder<Object> {

	@Override
	protected Object getQuery(final String sql) {
		return null;
	}

	@Override
	protected void setQueryOption(final Object query, final String name, final Object value) {
		//
	}

	@Override
	protected void setQueryParameter(final Object query, final String name, final Object value) {
		//
	}

}
