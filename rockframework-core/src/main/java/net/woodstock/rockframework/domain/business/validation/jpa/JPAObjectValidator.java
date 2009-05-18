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
package net.woodstock.rockframework.domain.business.validation.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Pojo;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.validation.local.ObjectValidator;
import net.woodstock.rockframework.domain.business.validation.local.Operation;
import net.woodstock.rockframework.domain.business.validation.local.ValidateParent;
import net.woodstock.rockframework.domain.business.validation.local.ValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.Validator;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.StringUtils;

public abstract class JPAObjectValidator {

	private static ValidatorColumn		validatorColumn;

	private static ValidatorId			validatorId;

	private static ValidatorManyToMany	validatorManyToMany;

	private static ValidatorManyToOne	validatorManyToOne;

	private static ValidatorOneToMany	validatorOneToMany;

	private static ValidatorOneToOne	validatorOneToOne;

	private JPAObjectValidator() {
		//
	}

	public static Collection<ValidationResult> validate(Pojo pojo, Operation operation)
			throws ValidationException {
		return JPAObjectValidator.validate(pojo, operation, false);
	}

	@SuppressWarnings("unchecked")
	public static Collection<ValidationResult> validate(Pojo pojo, Operation operation,
			boolean stopOnFirstError) throws ValidationException {
		String pojoName = JPAObjectValidator.getPojoName(pojo);
		ValidationContext rootContext = new JPAValidationContext(pojo, pojoName, null, operation);
		return JPAObjectValidator.validate(rootContext, pojo, (Class<Pojo>) pojo.getClass(), operation,
				stopOnFirstError);
	}

	@SuppressWarnings("unchecked")
	public static Collection<ValidationResult> validate(ValidationContext parentContext, Pojo pojo,
			Operation operation, boolean stopOnFirstError) throws ValidationException {
		return JPAObjectValidator.validate(parentContext, pojo, (Class<Pojo>) pojo.getClass(), operation,
				stopOnFirstError);
	}

	private static Collection<ValidationResult> validate(ValidationContext parentContext, Pojo pojo,
			Class<Pojo> pojoClass, Operation operation, boolean stopOnFirstError) {
		try {
			Collection<ValidationResult> results = new LinkedList<ValidationResult>();
			JPAObjectValidator.validate(parentContext, pojo, pojoClass, operation, stopOnFirstError, results);
			return results;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private static void validate(ValidationContext parentContext, Pojo pojo, Class<Pojo> pojoClass,
			Operation operation, boolean stopOnFirstError, Collection<ValidationResult> results) {
		try {
			if (pojo == null) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(
						ObjectValidator.MESSAGE_ERROR_NULL));
			}

			if (operation == Operation.QUERY) {
				return;
			}

			if (operation == Operation.ALL) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(
						ObjectValidator.MESSAGE_ERROR_NULL));
			}

			BeanInfo pojoInfo = BeanInfo.getBeanInfo(pojo.getClass());
			for (FieldInfo fieldInfo : pojoInfo.getFieldsInfo()) {
				for (Annotation annotation : fieldInfo.getAnnotations()) {
					Validator validator = null;
					boolean validate = false;
					if (operation == Operation.RETRIEVE) {
						if (annotation instanceof Id) {
							if (validatorId == null) {
								validatorId = new ValidatorId();
								validatorId.init();
							}
							validate = true;
							validator = validatorId;
						}
					} else {
						if (annotation instanceof Id) {
							if (validatorId == null) {
								validatorId = new ValidatorId();
								validatorId.init();
							}
							validate = true;
							validator = validatorId;
						} else if (annotation instanceof Column) {
							if (validatorColumn == null) {
								validatorColumn = new ValidatorColumn();
								validatorColumn.init();
							}
							validate = true;
							validator = validatorColumn;
						} else if (annotation instanceof OneToOne) {
							if (validatorOneToOne == null) {
								validatorOneToOne = new ValidatorOneToOne();
								validatorOneToOne.init();
							}
							validate = true;
							validator = validatorOneToOne;
						} else if (annotation instanceof OneToMany) {
							if (validatorOneToMany == null) {
								validatorOneToMany = new ValidatorOneToMany();
								validatorOneToMany.init();
							}
							validate = true;
							validator = validatorOneToMany;
						} else if (annotation instanceof ManyToOne) {
							if (validatorManyToOne == null) {
								validatorManyToOne = new ValidatorManyToOne();
								validatorManyToOne.init();
							}
							validate = true;
							validator = validatorManyToOne;
						} else if (annotation instanceof ManyToMany) {
							if (validatorManyToMany == null) {
								validatorManyToMany = new ValidatorManyToMany();
								validatorManyToMany.init();
							}
							validate = true;
							validator = validatorManyToMany;
						}
					}

					if (validate) {
						Object value = fieldInfo.getFieldValue(pojo);
						String name = fieldInfo.getFieldName();

						ValidationContext context = new JPAValidationContext(value, name, annotation,
								operation, parentContext);
						ValidationResult result = validator.validate(context);

						results.add(result);

						if (result.isError()) {
							String msg = JPAObjectValidator.getMessage(annotation);

							if (!StringUtils.isEmpty(msg)) {
								result.setMessage(msg);
							}

							if (stopOnFirstError) {
								return;
							}
						}
					}
				}
			}
			if (Pojo.class.isAssignableFrom(pojoClass.getSuperclass())) {
				boolean validate = true;
				if (pojoClass.isAnnotationPresent(ValidateParent.class)) {
					validate = pojoClass.getAnnotation(ValidateParent.class).validate();
				}
				if (validate) {
					JPAObjectValidator.validate(null, pojo, (Class<Pojo>) pojoClass.getSuperclass(),
							operation, stopOnFirstError, results);
				}
			}
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	private static String getPojoName(Pojo pojo) {
		if (pojo == null) {
			return null;
		}
		String className = pojo.getClass().getSimpleName();
		return className;
	}

	private static String getMessage(Annotation annotation) throws SecurityException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		try {
			Method m = annotation.getClass().getMethod(ObjectValidator.MESSAGE_PARAM, new Class[] {});
			String message = (String) m.invoke(annotation, new Object[] {});
			return message;
		} catch (NoSuchMethodException ee) {
			//
		}
		return null;
	}
}
