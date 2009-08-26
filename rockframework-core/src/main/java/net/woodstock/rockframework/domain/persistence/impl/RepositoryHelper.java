package net.woodstock.rockframework.domain.persistence.impl;

import net.woodstock.rockframework.utils.StringUtils;

abstract class RepositoryHelper {

	private RepositoryHelper() {
		//
	}

	public static String getListAllSql(Class<?> clazz, String order) {
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

}
