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
package net.woodstock.rockframework.domain.business.validation.local;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.validation.EntityValidator;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.StringUtils;

public class LocalEntityValidator implements EntityValidator {

	public static final String							OPERATION_PARAM							= "operation";

	public static final String							MESSAGE_PARAM							= "message";

	public static final String							MESSAGE_ERROR_OPERATION					= "domain.validation.error.operation";

	public static final String							MESSAGE_ERROR_NULL						= "domain.validation.error.null";

	public static final String							MESSAGE_FIELD_ERROR_CONSTRAINT			= "domain.validation.field.error.constraint";

	public static final String							MESSAGE_FIELD_ERROR_DATE				= "domain.validation.field.error.date";

	public static final String							MESSAGE_FIELD_ERROR_DATE_FUTURE			= "domain.validation.field.error.dateFuture";

	public static final String							MESSAGE_FIELD_ERROR_DATE_INTERVAL		= "domain.validation.field.error.dateInterval";

	public static final String							MESSAGE_FIELD_ERROR_DATE_PAST			= "domain.validation.field.error.datePast";

	public static final String							MESSAGE_FIELD_ERROR_EQUALS_FIELD		= "domain.validation.field.error.equals_field";

	public static final String							MESSAGE_FIELD_ERROR_EXPRESSION			= "domain.validation.field.error.expression";

	public static final String							MESSAGE_FIELD_ERROR_EXPRESSION_RESULT	= "domain.validation.field.error.expressionResult";

	public static final String							MESSAGE_FIELD_ERROR_INVALID				= "domain.validation.field.error.invalid";

	public static final String							MESSAGE_FIELD_ERROR_INVALID_TYPE		= "domain.validation.field.error.invalidType";

	public static final String							MESSAGE_FIELD_ERROR_LENGTH				= "domain.validation.field.error.length";

	public static final String							MESSAGE_FIELD_ERROR_NOT_NULL			= "domain.validation.field.error.notNull";

	public static final String							MESSAGE_FIELD_ERROR_NOT_EMPTY			= "domain.validation.field.error.notEmpty";

	public static final String							MESSAGE_FIELD_ERROR_NULL				= "domain.validation.field.error.null";

	public static final String							MESSAGE_FIELD_ERROR_RANGE				= "domain.validation.field.error.range";

	public static final String							MESSAGE_FIELD_ERROR_REGEX				= "domain.validation.field.error.regex";

	private static LocalEntityValidator					entityValidator;

	private Map<Class<? extends Validator>, Validator>	validators;

	private LocalEntityValidator() {
		super();
		this.validators = new HashMap<Class<? extends Validator>, Validator>();
	}

	public Collection<ValidationResult> validate(Pojo pojo, Operation operation) throws ValidationException {
		String pojoName = this.getPojoName(pojo);
		LocalValidationContext rootContext = new LocalValidationContext(pojo, pojoName, null, operation);
		return this.validate(rootContext, pojo, operation);
	}

	public Collection<ValidationResult> validate(LocalValidationContext rootContext, Pojo pojo,
			Operation operation) throws ValidationException {
		Collection<ValidationResult> results = new LinkedList<ValidationResult>();
		this.validate(rootContext, pojo, operation, results);
		return results;
	}

	private void validate(LocalValidationContext parentContext, Pojo pojo, Operation operation,
			Collection<ValidationResult> results) {
		try {
			if (pojo == null) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(
						LocalEntityValidator.MESSAGE_ERROR_NULL));
			}

			if (operation == Operation.QUERY) {
				return;
			}

			if (operation == Operation.ALL) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(
						LocalEntityValidator.MESSAGE_ERROR_NULL));
			}

			BeanInfo pojoInfo = BeanInfo.getBeanInfo(pojo.getClass());
			for (FieldInfo fieldInfo : pojoInfo.getFieldsInfo()) {

				for (Annotation annotation : fieldInfo.getAnnotations()) {
					Class<?> annotationClass = this.getAnnotationClass(annotation);

					if (annotationClass.isAnnotationPresent(Validate.class)) {

						Operation[] operations = this.getOperations(annotation);
						boolean validate = false;
						for (Operation o : operations) {
							if ((o == operation) || (o == Operation.ALL)) {
								validate = true;
								break;
							}
						}
						if (validate) {
							Class<? extends Validator> validatorClass = this
									.getValidatorClass(annotationClass);

							Validator validator = this.getValidator(validatorClass);

							Object value = fieldInfo.getFieldValue(pojo);
							String name = fieldInfo.getFieldName();

							LocalValidationContext context = new LocalValidationContext(value, name,
									annotation, operation, parentContext);
							ValidationResult result = validator.validate(context);

							results.add(result);

							if (result.isError()) {
								String msg = this.getMessage(annotation);

								if (!StringUtils.isEmpty(msg)) {
									result.setMessage(msg);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	// Utils
	private String getPojoName(Pojo pojo) {
		if (pojo == null) {
			return null;
		}
		String className = pojo.getClass().getSimpleName();
		return className;
	}

	private Validator getValidator(Class<? extends Validator> clazz) throws InstantiationException,
			IllegalAccessException {
		if (!this.validators.containsKey(clazz)) {
			Validator validator = clazz.newInstance();
			if (validator instanceof InitializableValidator) {
				((InitializableValidator) validator).init();
			}
			this.validators.put(clazz, validator);
		}
		return this.validators.get(clazz);
	}

	private Class<?> getAnnotationClass(Annotation annotation) throws ClassNotFoundException {
		Class<?> annotationClass = annotation.getClass();

		if (Proxy.class.isAssignableFrom(annotationClass)) {
			Proxy proxy = (Proxy) annotation;
			String className = proxy.toString();
			className = className.substring(1, className.indexOf('('));
			return Class.forName(className);
		}

		return annotationClass;
	}

	private Class<? extends Validator> getValidatorClass(Class<?> annotationClass) {
		Validate bv = annotationClass.getAnnotation(Validate.class);

		Class<? extends Validator> clazz = bv.validator();

		return clazz;
	}

	private Operation[] getOperations(Annotation annotation) throws SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		try {
			Method method = annotation.getClass().getMethod(LocalEntityValidator.OPERATION_PARAM,
					new Class[] {});
			Operation[] operations = (Operation[]) method.invoke(annotation, new Object[] {});
			return operations;
		} catch (NoSuchMethodException e) {
			return new Operation[] { Operation.ALL };
		}
	}

	private String getMessage(Annotation annotation) throws SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		try {
			Method m = annotation.getClass().getMethod(LocalEntityValidator.MESSAGE_PARAM, new Class[] {});
			String message = (String) m.invoke(annotation, new Object[] {});
			return message;
		} catch (NoSuchMethodException ee) {
			//
		}
		return null;
	}

	// Instance
	public static LocalEntityValidator getInstance() {
		if (LocalEntityValidator.entityValidator == null) {
			synchronized (LocalEntityValidator.class) {
				if (LocalEntityValidator.entityValidator == null) {
					LocalEntityValidator.entityValidator = new LocalEntityValidator();
				}
			}
		}
		return LocalEntityValidator.entityValidator;
	}

}
