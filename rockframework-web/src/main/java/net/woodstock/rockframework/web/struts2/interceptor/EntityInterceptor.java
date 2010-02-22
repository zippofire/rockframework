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
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.ReflectionType;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactoryImpl;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.web.config.WebLog;
import ognl.NoSuchPropertyException;
import ognl.Ognl;
import ognl.OgnlException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class EntityInterceptor extends BaseInterceptor {

	private static final long	serialVersionUID	= 1L;

	private static final String	ENTITY_ID_REGEX		= "^.*(\\..*)?\\.id";

	private static final String	ENTITY_ID			= "id";

	private static final String	ENTITY_SEPARATOR	= ".";

	@SuppressWarnings("unchecked")
	public String intercept(final ActionInvocation invocation) throws Exception {
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
					this.setIdParameter(action, entityName, value);
				}
			}
		}
		return invocation.invoke();
	}

	@SuppressWarnings("unchecked")
	private void setIdParameter(final Object action, final String entityName, final String value) throws OgnlException {
		try {
			Object obj = Ognl.getValue(entityName, action);
			if ((obj != null) && (obj instanceof Entity)) {
				Entity<?> entity = (Entity<?>) obj;

				BeanDescriptor beanDescriptor = BeanDescriptorFactoryImpl.getInstance(ReflectionType.FIELD).getBeanDescriptor(entity.getClass());

				PropertyDescriptor propertyDescriptor = beanDescriptor.getProperty(EntityInterceptor.ENTITY_ID);
				Class<?> clazz = propertyDescriptor.getType();

				try {
					Constructor<?> contructor = clazz.getConstructor(new Class[] { String.class });
					Object fieldValue = contructor.newInstance(new Object[] { value });

					WebLog.getInstance().getLog().info("Setting entity ID " + entityName + "[" + fieldValue + "]");
					propertyDescriptor.setValue(entity, fieldValue);
				} catch (NoSuchMethodException e) {
					WebLog.getInstance().getLog().warn("Could not find constructor " + entity.getClass().getCanonicalName() + "(String). Parameter not setted");
				} catch (Exception e) {
					WebLog.getInstance().getLog().warn("Error in constructor " + entity.getClass().getCanonicalName() + "(String)");
					WebLog.getInstance().getLog().warn(e.getMessage(), e);
				}
			}
		} catch (NoSuchPropertyException e) {
			WebLog.getInstance().getLog().debug(e.getMessage(), e);
		}
	}

	private boolean isIdParameter(final String name) {
		return Pattern.matches(EntityInterceptor.ENTITY_ID_REGEX, name);
	}

	private String getEntityName(final String name) {
		return name.substring(0, name.lastIndexOf(EntityInterceptor.ENTITY_SEPARATOR + EntityInterceptor.ENTITY_ID));
	}
}
