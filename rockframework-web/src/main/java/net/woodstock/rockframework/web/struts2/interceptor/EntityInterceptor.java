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
package net.woodstock.rockframework.web.struts2.interceptor;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.StringUtils;
import ognl.NoSuchPropertyException;
import ognl.Ognl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class EntityInterceptor extends BaseInterceptor {

	private static final long	serialVersionUID	= 1L;

	private static final String	ENTITY_ID_REGEX		= "^.*(\\..*)?\\.id";

	private static final String	ENTITY_ID			= "id";

	private static final String	ENTITY_SEPARATOR	= ".";

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		ActionContext ac = invocation.getInvocationContext();
		Map<String, Object> parameters = ac.getParameters();
		for (Entry<String, Object> entry : parameters.entrySet()) {
			String key = entry.getKey();
			if (entry.getValue() != null) {
				String value = null;

				Class<?> type = entry.getValue().getClass().getComponentType();

				if (type == String.class) {
					String[] values = (String[]) entry.getValue();

					if ((values != null) && (values.length == 1)) {
						value = values[0];
					}
				}

				if ((!StringUtils.isEmpty(value)) && (this.isIdParameter(key))) {
					String entityName = this.getEntityName(key);
					try {
						Object obj = Ognl.getValue(entityName, action);
						if ((obj != null) && (obj instanceof Entity)) {
							Entity<?> entity = (Entity<?>) obj;
							BeanInfo beanInfo = BeanInfo.getBeanInfo(entity.getClass());
							FieldInfo fieldInfo = beanInfo.getFieldInfo(ENTITY_ID);
							Object fieldValue = fieldInfo.getFieldValue(entity);
							if (fieldValue == null) {
								Class<?> clazz = fieldInfo.getFieldType();
								Constructor<?> contructor = clazz
										.getConstructor(new Class[] { String.class });
								fieldValue = contructor.newInstance(new Object[] { value });
								this.getLogger().info(
										"Setting entity ID " + entityName + "[" + fieldValue + "]");
								fieldInfo.setFieldValue(entity, fieldValue);
							}
						}
					} catch (NoSuchPropertyException e) {
						//
					}
				}
			}
		}
		return invocation.invoke();
	}

	private boolean isIdParameter(String name) {
		return Pattern.matches(ENTITY_ID_REGEX, name);
	}

	private String getEntityName(String name) {
		return name.substring(0, name.lastIndexOf(ENTITY_SEPARATOR + ENTITY_ID));
	}
}
