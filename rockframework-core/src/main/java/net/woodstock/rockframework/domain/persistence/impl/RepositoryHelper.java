package net.woodstock.rockframework.domain.persistence.impl;

import java.util.Map;

import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.utils.StringUtils;

abstract class RepositoryHelper {

	private RepositoryHelper() {
		//
	}

	public static String getListAllSql(final Class<?> clazz, final Map<String, Object> options, final boolean canonicalName) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT o FROM ");
		if (canonicalName) {
			builder.append(clazz.getCanonicalName());
		} else {
			builder.append(clazz.getSimpleName());
		}
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

	public static String getDeleteSql(final Class<?> clazz, final boolean canonicalName) {
		StringBuilder builder = new StringBuilder();
		builder.append("DELETE FROM ");
		if (canonicalName) {
			builder.append(clazz.getCanonicalName());
		} else {
			builder.append(clazz.getSimpleName());
		}
		builder.append(" WHERE id = :id");
		return builder.toString();
	}

}
