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
package net.woodstock.rockframework.domain.persistence.query.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.query.BuilderException;
import net.woodstock.rockframework.domain.persistence.query.LikeMode;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.QueryContextParameter.Operator;
import net.woodstock.rockframework.domain.utils.PojoUtils;
import net.woodstock.rockframework.sys.SysLogger;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;

abstract class QueryContextHelper {

	private static final String	ROOT_ALIAS			= "_obj";

	private static final String	ALIAS_SEPARADOR		= "_";

	private static final String	OBJECT_SEPARATOR	= ".";

	private QueryContextHelper() {
		//
	}

	public static QueryContext createQueryContext(Entity<?> e, Map<String, Object> options)
			throws BuilderException {
		try {
			Class<?> clazz = e.getClass();
			String className = clazz.getCanonicalName();

			QueryContext context = new QueryContext(className, ROOT_ALIAS, null);

			BeanInfo beanInfo = BeanInfo.getBeanInfo(clazz);

			List<Entity<?>> parsed = new ArrayList<Entity<?>>();
			parsed.add(e);

			for (FieldInfo fieldInfo : beanInfo.getFieldsInfo()) {
				String name = fieldInfo.getFieldName();
				String alias = name;
				Object value = fieldInfo.getFieldValue(e);
				if (value != null) {
					QueryContextHelper.handleValue(context, options, name, alias, value, parsed);
				}
			}
			QueryContextHelper.generateQueryString(context, options);
			return context;
		} catch (BuilderException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new BuilderException(exception);
		}
	}

	private static void generateQueryString(QueryContext context, Map<String, Object> options) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT " + context.getAlias() + "\n");
		builder.append("  FROM " + context.getName() + " AS " + context.getAlias() + "\n");
		boolean where = true;
		for (QueryContext child : context.getChildsRecursive()) {
			if ((child.hasParametersRecursive()) && (child.isJoinNeeded())) {
				builder.append("       JOIN " + child.getName() + " AS " + child.getAlias() + "\n");
			}
		}
		for (QueryContextParameter parameter : context.getParametersRecursive()) {
			if (where) {
				builder.append(" WHERE ");
				where = false;
			} else {
				builder.append("   AND ");
			}
			builder.append(parameter.getSqlName());
			builder.append(" ");
			builder.append(parameter.getOperator().getOperator());
			builder.append(" :");
			builder.append(parameter.getSqlAlias());
			builder.append("\n");
		}
		if (options.containsKey(QueryBuilder.OPTION_ORDER_BY)) {
			String order = options.get(QueryBuilder.OPTION_ORDER_BY).toString();
			builder.append(" ORDER BY " + order + "\n");
		}
		context.setQueryString(builder.toString().trim());
	}

	private static void handleValue(QueryContext context, Map<String, Object> options, String name,
			String alias, Object value, List<Entity<?>> parsed) throws BuilderException {
		try {
			name = getFieldName(context, name);
			alias = getFieldAlias(context, alias);
			if (value instanceof Entity) {
				boolean disable = false;
				if (options.containsKey(QueryBuilder.OPTION_DISABLE_CHILD)) {
					Boolean b = (Boolean) options.get(QueryBuilder.OPTION_DISABLE_CHILD);
					disable = b.booleanValue();
				}
				if (!disable) {
					handleEntityValue(context, options, name, alias, (Entity<?>) value, parsed);
				}
			} else if (value instanceof Collection) {
				boolean disable = false;
				if (options.containsKey(QueryBuilder.OPTION_DISABLE_COLLECTION)) {
					Boolean b = (Boolean) options.get(QueryBuilder.OPTION_DISABLE_COLLECTION);
					disable = b.booleanValue();
				}
				if (!disable) {
					handleCollectionValue(context, options, name, alias, (Collection<?>) value, parsed);
				}
			} else if (value instanceof String) {
				handleStringValue(context, options, name, alias, (String) value);
			} else {
				handleCommonValue(context, name, alias, value);
			}
		} catch (BuilderException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new BuilderException(exception);
		}
	}

	private static void handleEntityValue(QueryContext context, Map<String, Object> options, String name,
			String alias, Entity<?> value, List<Entity<?>> parsed) throws IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (QueryContextHelper.contains(parsed, value)) {
			SysLogger.getLogger().warn("Entity " + value + " already parsed!!!");
			return;
		}

		parsed.add(value);

		if (PojoUtils.hasNotNullAttribute(value, true)) {
			Class<?> clazz = value.getClass();
			QueryContext child = new QueryContext(name, alias, context);
			BeanInfo beanInfo = BeanInfo.getBeanInfo(clazz);
			for (FieldInfo fieldInfo : beanInfo.getFieldsInfo()) {
				String childName = fieldInfo.getFieldName();
				String childAlias = fieldInfo.getFieldName();
				Object childValue = fieldInfo.getFieldValue(value);
				if (childValue != null) {
					QueryContextHelper
							.handleValue(child, options, childName, childAlias, childValue, parsed);
				}
			}
			context.getChilds().add(child);
		}
	}

	private static void handleCollectionValue(QueryContext context, Map<String, Object> options, String name,
			String alias, Collection<?> value, List<Entity<?>> parsed) throws IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (value.size() > 0) {
			QueryContext child = new QueryContext(name, alias, context);
			child.setJoinNeeded(true);
			int index = 0;
			for (Object o : value) {
				if (o instanceof Entity) {
					if (QueryContextHelper.contains(parsed, (Entity<?>) o)) {
						SysLogger.getLogger().warn("Entity " + value + " already parsed!!!");
						continue;
					}
					parsed.add((Entity<?>) o);

					Class<?> clazz = o.getClass();
					BeanInfo beanInfo = BeanInfo.getBeanInfo(clazz);
					for (FieldInfo fieldInfo : beanInfo.getFieldsInfo()) {
						String childName = fieldInfo.getFieldName();
						String childAlias = fieldInfo.getFieldName() + ALIAS_SEPARADOR + index;
						Object childValue = fieldInfo.getFieldValue(o);
						if (childValue != null) {
							QueryContextHelper.handleValue(child, options, childName, childAlias,
									childValue, parsed);
						}
					}
				}
				index++;
			}
			context.getChilds().add(child);
		}
	}

	private static void handleStringValue(QueryContext context, Map<String, Object> options, String name,
			String alias, String value) {
		String sqlName = name;
		String sqlAlias = alias;
		if (options.containsKey(QueryBuilder.OPTION_IGNORE_CASE)) {
			Boolean b = (Boolean) options.get(QueryBuilder.OPTION_IGNORE_CASE);
			if ((b != null) && (b.booleanValue())) {
				value = value.toLowerCase();
				sqlName = "LOWER(" + name + ")";
			}
		}

		if ((options.containsKey(QueryBuilder.OPTION_LIKE_MODE))
				&& (options.get(QueryBuilder.OPTION_LIKE_MODE) instanceof LikeMode)) {
			LikeMode likeMode = (LikeMode) options.get(QueryBuilder.OPTION_LIKE_MODE);
			if (likeMode != null) {
				switch (likeMode) {
					case ALL:
						value = "%" + value + "%";
						break;
					case BEGIN:
						value = "%" + value;
						break;
					case END:
						value = value + "%";
						break;
					case DISABLED:
						break;
				}
			}
			if (likeMode == LikeMode.DISABLED) {
				context.getParameters().add(
						new QueryContextParameter(name, sqlName, alias, sqlAlias, value, Operator.EQUALS));
			} else {
				context.getParameters().add(
						new QueryContextParameter(name, sqlName, alias, sqlAlias, value, Operator.LIKE));
			}
		} else {
			context.getParameters().add(
					new QueryContextParameter(name, sqlName, alias, sqlAlias, value, Operator.EQUALS));
		}
	}

	private static void handleCommonValue(QueryContext context, String name, String alias, Object value)
			throws BuilderException {
		context.getParameters().add(
				new QueryContextParameter(name, name, alias, alias, value, Operator.EQUALS));
	}

	private static String getFieldName(QueryContext context, String name) {
		StringBuilder builder = new StringBuilder();
		if ((context.getParent() == null) || (context.isJoinNeeded())) {
			builder.append(context.getAlias());
		} else {
			builder.append(context.getName());
		}
		builder.append(OBJECT_SEPARATOR);
		builder.append(name);
		return builder.toString();
	}

	private static String getFieldAlias(QueryContext context, String alias) {
		StringBuilder builder = new StringBuilder();
		builder.append(context.getAlias());
		builder.append(ALIAS_SEPARADOR);
		builder.append(alias);
		return builder.toString();
	}

	private static boolean contains(List<Entity<?>> list, Entity<?> entity) {
		for (Entity<?> e : list) {
			if (e == entity) {
				return true;
			}
		}
		return false;
	}

}
