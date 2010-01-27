package net.woodstock.rockframework.domain.persistence.impl;

import java.util.Map;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.utils.StringUtils;

abstract class RepositoryHelper {

	private RepositoryHelper() {
		//
	}

	public static String getListAllSql(final Class<?> clazz, final Map<String, Object> options) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT o FROM ");
		builder.append(clazz.getCanonicalName());
		builder.append(" AS o");
		if ((options != null) && (options.containsKey(QueryBuilder.OPTION_ORDER_BY))) {
			String order = (String) options.get(QueryBuilder.OPTION_ORDER_BY);
			if (!StringUtils.isEmpty(order)) {
				builder.append(" ORDER BY ");
				builder.append(order);
			}
		}

		return builder.toString();
	}

	public static String getDeleteSql(final Entity<?> e) {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM ");
		builder.append(e.getClass().getCanonicalName());
		builder.append(" WHERE id = :id");
		return builder.toString();
	}

}
