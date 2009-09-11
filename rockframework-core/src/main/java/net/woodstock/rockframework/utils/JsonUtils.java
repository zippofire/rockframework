/*
 * This file is part of rockapi.
 * 
 * rockapi is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * rockapi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>;.
 */
package net.woodstock.rockframework.utils;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;
import net.woodstock.rockframework.sys.SysLogger;

public abstract class JsonUtils {

	public static final int				ALL_LEVELS			= -1;

	private static final String			NULL				= "null";

	private static final String			BEGIN_ARRAY			= "[";

	private static final String			END_ARRAY			= "]";

	private static final String			BEGIN_CLASS			= "{";

	private static final String			END_CLASS			= "}";

	private static final String			FIELD_SEPARADOR		= ", ";

	private static final String			STRING_DELIMITER	= "\"";

	private static final String			ALL_FIELDS			= "*";

	private static final String			MSG_ERROR_REFERENCE	= "Breaking wrong reference, use ignore fields fo prevent this problem.";

	private static Collection<String[]>	replacement;

	private JsonUtils() {
		//
	}

	public static String toJson(Object obj, String[] ignoreFields) {
		return JsonUtils.toJson(obj, ignoreFields, JsonUtils.ALL_LEVELS);
	}

	public static String toJson(Collection<?> collection, String[] ignoreFields) {
		return JsonUtils.toJson(collection, ignoreFields, JsonUtils.ALL_LEVELS);
	}

	public static String toJson(Object obj, String[] ignoreFields, int maxLevel) {
		if (obj == null) {
			StringBuilder builder = new StringBuilder();
			builder.append(JsonUtils.NULL);
			return builder.toString();
		}
		return JsonUtils.toJson(obj, obj.getClass(), null, ignoreFields, new HashMap<Object, String>(), 0, maxLevel);
	}

	public static String toJson(Collection<?> collection, String[] ignoreFields, int maxLevel) {
		if (collection == null) {
			StringBuilder builder = new StringBuilder();
			builder.append(JsonUtils.NULL);
			return builder.toString();
		}

		Map<Object, String> queue = new HashMap<Object, String>();
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		builder.append(JsonUtils.BEGIN_ARRAY);
		for (Object obj : collection) {
			if (!first) {
				builder.append(JsonUtils.FIELD_SEPARADOR);
			} else {
				first = false;
			}
			builder.append(JsonUtils.toJson(obj, obj.getClass(), null, ignoreFields, queue, 0, maxLevel));
		}
		builder.append(JsonUtils.END_ARRAY);

		return builder.toString();
	}

	private static String toJson(Object obj, Class<?> type, Object parent, String[] ignoreFields, Map<Object, String> queue, int currentLevel, int maxLevel) {
		StringBuilder builder = new StringBuilder();

		BeanDescriptor BeanDescriptor = BeanDescriptorFactory.getByMethodInstance().getBeanDescriptor(type);
		Collection<PropertyDescriptor> properties = BeanDescriptor.getProperties();
		Map<String, Set<String>> ignore = new HashMap<String, Set<String>>();

		if (ignoreFields != null) {
			for (String ignoreField : ignoreFields) {
				ignoreField = ignoreField.trim();
				if (ignoreField.indexOf('.') != -1) {
					String s1 = ignoreField.substring(0, ignoreField.indexOf('.'));
					String s2 = ignoreField.substring(ignoreField.indexOf('.') + 1);
					if (ignore.containsKey(s1)) {
						ignore.get(s1).add(s2);
					} else {
						Set<String> set = new HashSet<String>();
						set.add(s2);
						ignore.put(s1, set);
					}
				} else {
					ignore.put(ignoreField, null);
				}
			}
		}

		boolean first = true;

		builder.append(JsonUtils.BEGIN_CLASS);

		for (PropertyDescriptor property : properties) {
			if (!property.isReadable()) {
				continue;
			}

			String propertyName = property.getName();

			Class<?> propertyType = property.getType();
			Object propertyValue = property.getValue(obj);

			if (!first) {
				builder.append(JsonUtils.FIELD_SEPARADOR);
			} else {
				first = false;
			}

			builder.append(propertyName + ": ");

			if ((ignore.containsKey(propertyName)) && (ignore.get(propertyName) == null)) {
				builder.append(JsonUtils.NULL);
				continue;
			}

			if (propertyValue == null) {
				builder.append(JsonUtils.NULL);
				continue;
			}

			if (Boolean.class.isAssignableFrom(propertyType)) {
				builder.append(propertyValue.toString());
			} else if (Character.class.isAssignableFrom(propertyType)) {
				builder.append(JsonUtils.STRING_DELIMITER + JsonUtils.addEscape(propertyValue.toString()) + JsonUtils.STRING_DELIMITER);
			} else if (Date.class.isAssignableFrom(propertyType)) {
				long time = ((Date) propertyValue).getTime();
				builder.append("new Date(" + time + ")");
			} else if (Number.class.isAssignableFrom(propertyType)) {
				builder.append(propertyValue.toString());
			} else if (String.class.isAssignableFrom(propertyType)) {
				builder.append(JsonUtils.STRING_DELIMITER + JsonUtils.addEscape(propertyValue.toString()) + JsonUtils.STRING_DELIMITER);
			} else {
				if ((ignore.containsKey(JsonUtils.ALL_FIELDS)) && (!ignore.containsKey(propertyName))) {
					builder.append(JsonUtils.NULL);
					continue;
				}
				if ((maxLevel != JsonUtils.ALL_LEVELS) && (currentLevel + 1 > maxLevel)) {
					builder.append(JsonUtils.NULL);
					continue;
				}
				if (Collection.class.isAssignableFrom(propertyType)) {
					Collection<?> collection = (Collection<?>) propertyValue;
					int index = 0;
					builder.append(JsonUtils.BEGIN_ARRAY);
					for (Object o : collection) {
						if (!queue.containsKey(o)) {
							String[] s = null;
							if (ignore.containsKey(propertyName)) {
								s = ignore.get(propertyName).toArray(new String[] {});
							}
							String str = JsonUtils.toJson(o, o.getClass(), obj, s, queue, currentLevel + 1, maxLevel);
							queue.put(o, str);
							builder.append(str);
							index++;
							if (index < collection.size()) {
								builder.append(JsonUtils.FIELD_SEPARADOR);
							}
						} else {
							SysLogger.getLogger().warn(JsonUtils.MSG_ERROR_REFERENCE);
							builder.append(queue.get(o));
						}
					}
					builder.append(JsonUtils.END_ARRAY);
				} else {
					if ((!propertyValue.equals(parent)) && (!queue.containsKey(propertyValue))) {
						String[] s = null;
						if (ignore.containsKey(propertyName)) {
							s = ignore.get(propertyName).toArray(new String[] {});
						}
						String str = JsonUtils.toJson(propertyValue, propertyType, obj, s, queue, currentLevel + 1, maxLevel);
						queue.put(propertyValue, str);
						builder.append(str);
					} else {
						SysLogger.getLogger().warn(JsonUtils.MSG_ERROR_REFERENCE);
						builder.append(queue.get(propertyValue));
					}
				}
			}
		}

		builder.append(JsonUtils.END_CLASS);

		return builder.toString();
	}

	public static String addEscape(String str) {
		if (str == null) {
			return null;
		}
		if (JsonUtils.replacement == null) {
			JsonUtils.replacement = new LinkedList<String[]>();
			JsonUtils.replacement.add(new String[] { "\n", "\\n" });
			JsonUtils.replacement.add(new String[] { "\r", "\\r" });
			JsonUtils.replacement.add(new String[] { "\t", "\\t" });
			JsonUtils.replacement.add(new String[] { "'", "\\'" });
			JsonUtils.replacement.add(new String[] { "\"", "\\\"" });
		}

		for (String[] s : JsonUtils.replacement) {
			str = str.replace(s[0], s[1]);
		}

		return str;
	}
}
