package net.woodstock.rockframework.domain.persistence.impl;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.utils.StringUtils;

abstract class RepositoryHelper {

	private RepositoryHelper() {
		//
	}

	public static String getListAllSql(final Class<?> clazz, final String order) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT o FROM ");
		builder.append(clazz.getCanonicalName());
		builder.append(" AS o");
		if (!StringUtils.isEmpty(order)) {
			builder.append(" ORDER BY ");
			builder.append(order);
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
