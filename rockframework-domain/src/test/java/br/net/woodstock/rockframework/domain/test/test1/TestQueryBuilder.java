package br.net.woodstock.rockframework.domain.test.test1;

import br.net.woodstock.rockframework.domain.persistence.orm.query.impl.JPQLQueryBuilder;

public class TestQueryBuilder extends JPQLQueryBuilder<Object> {

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
