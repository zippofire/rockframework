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

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import net.woodstock.rockframework.config.CoreLog;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.persistence.query.BuilderException;
import net.woodstock.rockframework.domain.persistence.query.LikeMode;
import net.woodstock.rockframework.domain.persistence.query.QueryBuilder;
import net.woodstock.rockframework.domain.persistence.query.impl.QueryContextParameter.Operator;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactoryImpl;
import net.woodstock.rockframework.utils.StringUtils;

@SuppressWarnings("unchecked")
abstract class QueryContextHelper {

	private static final String	HIBERNATE_PROXY_CLASS	= "org.hibernate.proxy.HibernateProxy";

	private static final String	OPENJPA_PROXY_CLASS		= "org.apache.openjpa.enhance.PersistenceCapable";

	private static final String	JPA_ENTITY_CLASS		= "javax.persistence.Entity";

	private static final String	JPA_TRANSIENT_CLASS		= "javax.persistence.Transient";

	private static final String	ROOT_ALIAS				= "_obj";

	private static final String	ALIAS_SEPARADOR			= "_";

	private static final String	OBJECT_SEPARATOR		= ".";

	private QueryContextHelper() {
		//
	}

	public static QueryContext createQueryContext(final Entity e, final Map<String, Object> options) {
		if (e == null) {
			throw new IllegalArgumentException("Entity must be not null");
		}
		if (QueryContextHelper.isProxy(e)) {
			throw new IllegalArgumentException("Proxy classes cannot be parsed[" + e.getClass().getCanonicalName() + "]");
		}
		try {
			Class<?> clazz = e.getClass();
			BeanDescriptor beanDescriptor = BeanDescriptorFactoryImpl.getInstance(ReflectionType.FIELD).getBeanDescriptor(clazz);
			String entityName = QueryContextHelper.getEntityName(beanDescriptor);
			QueryContext context = new QueryContext(entityName, entityName, QueryContextHelper.ROOT_ALIAS, null);
			Queue<Entity> parsed = new LinkedList<Entity>();

			parsed.add(e);

			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (!propertyDescriptor.isReadable()) {
					continue;
				}
				String name = propertyDescriptor.getName();
				String alias = name;
				Object value = propertyDescriptor.getValue(e);
				boolean isTransient = QueryContextHelper.isTransient(propertyDescriptor);

				if ((value != null) && (!isTransient)) {
					QueryContextHelper.handleValue(context, options, name, name, alias, value, parsed);
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

	private static void generateQueryString(final QueryContext context, final Map<String, Object> options) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT " + context.getAlias() + "\n");
		builder.append("  FROM " + context.getName() + " AS " + context.getAlias() + "\n");
		boolean where = true;
		for (QueryContext child : context.getChildsRecursive()) {
			if ((child.hasParametersRecursive()) && (child.isJoinNeeded())) {
				builder.append("      INNER JOIN " + child.getName() + " AS " + child.getAlias() + "\n");
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
			builder.append(" ORDER BY " + context.getAlias() + QueryContextHelper.OBJECT_SEPARATOR + order + "\n");
		}
		context.setQueryString(builder.toString().trim());
	}

	private static void handleValue(final QueryContext context, final Map<String, Object> options, final String realName, final String name, final String alias, final Object value, final Queue<Entity> parsed) {
		try {
			String sqlName = QueryContextHelper.getFieldName(context, name);
			String sqlAlias = QueryContextHelper.getFieldAlias(context, alias);
			if (value instanceof Entity) {
				boolean disable = false;
				if (options.containsKey(QueryBuilder.OPTION_DISABLE_CHILD)) {
					Boolean b = (Boolean) options.get(QueryBuilder.OPTION_DISABLE_CHILD);
					disable = b.booleanValue();
				}
				if (!disable) {
					QueryContextHelper.handleEntityValue(context, options, name, sqlName, sqlAlias, (Entity) value, parsed);
				}
			} else if (value instanceof Collection) {
				boolean disable = false;
				if (options.containsKey(QueryBuilder.OPTION_DISABLE_COLLECTION)) {
					Boolean b = (Boolean) options.get(QueryBuilder.OPTION_DISABLE_COLLECTION);
					disable = b.booleanValue();
				}
				if (!disable) {
					QueryContextHelper.handleCollectionValue(context, options, realName, sqlName, sqlAlias, (Collection) value, parsed);
				}
			} else if (value instanceof String) {
				QueryContextHelper.handleStringValue(context, options, sqlName, sqlAlias, (String) value);
			} else {
				QueryContextHelper.handleCommonValue(context, sqlName, sqlAlias, value);
			}
		} catch (BuilderException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new BuilderException(exception);
		}
	}

	private static void handleEntityValue(final QueryContext context, final Map<String, Object> options, final String realName, final String name, final String alias, final Entity value, final Queue<Entity> parsed) {
		if (QueryContextHelper.contains(parsed, value)) {
			CoreLog.getInstance().getLog().debug("Entity " + value + " already parsed!!!");
			return;
		}
		if (QueryContextHelper.isProxy(value)) {
			CoreLog.getInstance().getLog().warn("Child proxy classes cannot will be parsed[" + context.getName() + "." + realName + "]");
			return;
		}

		parsed.add(value);

		if (QueryContextHelper.hasNotNullAttribute(value)) {
			Class<?> clazz = value.getClass();
			QueryContext child = new QueryContext(realName, name, alias, context);
			BeanDescriptor beanDescriptor = BeanDescriptorFactoryImpl.getInstance(ReflectionType.FIELD).getBeanDescriptor(clazz);
			child.setJoinNeeded(true);
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				if (!propertyDescriptor.isReadable()) {
					continue;
				}
				String childName = propertyDescriptor.getName();
				String childAlias = propertyDescriptor.getName();
				Object childValue = propertyDescriptor.getValue(value);
				boolean isTransient = QueryContextHelper.isTransient(propertyDescriptor);

				if ((childValue != null) && (!isTransient)) {
					QueryContextHelper.handleValue(child, options, childName, childName, childAlias, childValue, parsed);
				}
			}
			context.getChilds().add(child);
		}
	}

	private static void handleCollectionValue(final QueryContext context, final Map<String, Object> options, final String realName, final String name, final String alias, final Collection value, final Queue<Entity> parsed) {
		if (value.size() > 0) {
			QueryContext child = new QueryContext(realName, name, alias, context);
			child.setJoinNeeded(true);
			int index = 0;
			for (Object o : value) {
				if (o instanceof Entity) {
					if (QueryContextHelper.contains(parsed, (Entity) o)) {
						CoreLog.getInstance().getLog().debug("Entity " + value + " already parsed!!!");
						continue;
					}
					parsed.add((Entity) o);

					Class<?> clazz = o.getClass();
					BeanDescriptor beanDescriptor = BeanDescriptorFactoryImpl.getInstance(ReflectionType.FIELD).getBeanDescriptor(clazz);
					for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
						if (!propertyDescriptor.isReadable()) {
							continue;
						}
						String childName = propertyDescriptor.getName();
						String childAlias = propertyDescriptor.getName() + QueryContextHelper.ALIAS_SEPARADOR + index;
						Object childValue = propertyDescriptor.getValue(o);
						if (childValue != null) {
							QueryContextHelper.handleValue(child, options, childName, childName, childAlias, childValue, parsed);
						}
					}
				}
				index++;
			}
			context.getChilds().add(child);
		}
	}

	private static void handleStringValue(final QueryContext context, final Map<String, Object> options, final String name, final String alias, final String value) {
		if (StringUtils.isEmpty(value)) {
			return;
		}
		String sqlName = name;
		String sqlValue = value;
		String sqlAlias = alias;
		if (options.containsKey(QueryBuilder.OPTION_IGNORE_CASE)) {
			Boolean b = (Boolean) options.get(QueryBuilder.OPTION_IGNORE_CASE);
			if ((b != null) && (b.booleanValue())) {
				sqlValue = sqlValue.toLowerCase();
				sqlName = "LOWER(" + name + ")";
			}
		}

		if ((options.containsKey(QueryBuilder.OPTION_LIKE_MODE)) && (options.get(QueryBuilder.OPTION_LIKE_MODE) instanceof LikeMode)) {
			LikeMode likeMode = (LikeMode) options.get(QueryBuilder.OPTION_LIKE_MODE);
			if (likeMode != null) {
				switch (likeMode) {
					case ALL:
						sqlValue = "%" + sqlValue + "%";
						break;
					case BEGIN:
						sqlValue = "%" + sqlValue;
						break;
					case END:
						sqlValue = sqlValue + "%";
						break;
					case DISABLED:
						break;
					default:
						break;
				}
			}
			if (likeMode == LikeMode.DISABLED) {
				context.getParameters().add(new QueryContextParameter(name, sqlName, alias, sqlAlias, sqlValue, Operator.EQUALS));
			} else {
				context.getParameters().add(new QueryContextParameter(name, sqlName, alias, sqlAlias, sqlValue, Operator.LIKE));
			}
		} else {
			context.getParameters().add(new QueryContextParameter(name, sqlName, alias, sqlAlias, sqlValue, Operator.EQUALS));
		}
	}

	private static void handleCommonValue(final QueryContext context, final String name, final String alias, final Object value) {
		context.getParameters().add(new QueryContextParameter(name, name, alias, alias, value, Operator.EQUALS));
	}

	private static String getEntityName(final BeanDescriptor beanDescriptor) {
		try {
			Class.forName(QueryContextHelper.JPA_ENTITY_CLASS);
			return JPAHelper.getEntityName(beanDescriptor);
		} catch (ClassNotFoundException e) {
			return beanDescriptor.getType().getCanonicalName();
		}
	}

	private static String getFieldName(final QueryContext context, final String name) {
		StringBuilder builder = new StringBuilder();
		if ((context.getParent() == null) || (context.isJoinNeeded())) {
			builder.append(context.getAlias());
		} else {
			builder.append(context.getName());
		}
		builder.append(QueryContextHelper.OBJECT_SEPARATOR);
		builder.append(name);
		return builder.toString();
	}

	private static String getFieldAlias(final QueryContext context, final String alias) {
		StringBuilder builder = new StringBuilder();
		builder.append(context.getAlias());
		builder.append(QueryContextHelper.ALIAS_SEPARADOR);
		builder.append(alias);
		return builder.toString();
	}

	private static boolean contains(final Queue<Entity> list, final Entity entity) {
		for (Entity e : list) {
			if (e == entity) {
				return true;
			}
		}
		return false;
	}

	private static boolean isTransient(final PropertyDescriptor propertyDescriptor) {
		if (Modifier.isTransient(propertyDescriptor.getModifiers())) {
			return true;
		}
		try {
			Class.forName(QueryContextHelper.JPA_TRANSIENT_CLASS);
			return JPAHelper.isTransient(propertyDescriptor);
		} catch (ClassNotFoundException e) {
			CoreLog.getInstance().getLog().debug(e.getMessage(), e);
			return false;
		}
	}

	private static boolean isProxy(final Object o) {
		if (o == null) {
			return false;
		}
		boolean b = false;
		// Hibernate
		try {
			Class.forName(QueryContextHelper.HIBERNATE_PROXY_CLASS);
			b = HibernateHelper.isProxy(o);
		} catch (ClassNotFoundException e) {
			CoreLog.getInstance().getLog().debug(e.getMessage(), e);
		}
		// OpenJPA
		if (!b) {
			try {
				Class.forName(QueryContextHelper.OPENJPA_PROXY_CLASS);
				b = OpenJPAHelper.isProxy(o);
			} catch (ClassNotFoundException e) {
				CoreLog.getInstance().getLog().debug(e.getMessage(), e);
			}
		}
		// EclipseLink
		// ???
		// TopLink
		// ???
		return b;
	}

	private static boolean hasNotNullAttribute(final Entity e) {
		if (e == null) {
			return false;
		}

		BeanDescriptor beanDescriptor = BeanDescriptorFactoryImpl.getInstance().getBeanDescriptor(e.getClass());
		for (PropertyDescriptor property : beanDescriptor.getProperties()) {
			if (!property.isReadable()) {
				continue;
			}
			Object tmp = property.getValue(e);
			if ((tmp != null) && (QueryContextHelper.isValidType(property.getType()))) {
				return true;
			}
		}

		return false;
	}

	private static boolean isValidType(final Class<?> clazz) {
		// Wrappers
		if (clazz == Boolean.class) {
			return true;
		}
		if (clazz == Byte.class) {
			return true;
		}
		if (clazz == Character.class) {
			return true;
		}
		if (clazz == Double.class) {
			return true;
		}
		if (clazz == Float.class) {
			return true;
		}
		if (clazz == Integer.class) {
			return true;
		}
		if (clazz == Long.class) {
			return true;
		}
		if (clazz == Short.class) {
			return true;
		}
		// String
		if (clazz == String.class) {
			return true;
		}
		// Number
		if (Number.class.isAssignableFrom(clazz)) {
			return true;
		}
		// Date
		if (Date.class.isAssignableFrom(clazz)) {
			return true;
		}
		// Primitives
		if (clazz.isEnum()) {
			return true;
		}
		if (clazz.isPrimitive()) {
			return true;
		}
		// Entity
		if (Entity.class.isAssignableFrom(clazz)) {
			return true;
		}
		// Collection
		if (Collection.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}
}
