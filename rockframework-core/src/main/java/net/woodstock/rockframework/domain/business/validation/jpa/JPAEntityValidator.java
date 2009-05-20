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
import net.woodstock.rockframework.domain.business.validation.EntityValidator;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.Validator;
import net.woodstock.rockframework.util.BeanInfo;
import net.woodstock.rockframework.util.FieldInfo;
import net.woodstock.rockframework.utils.StringUtils;

public class JPAEntityValidator implements EntityValidator {

	private static ValidatorColumn		validatorColumn;

	private static ValidatorId			validatorId;

	private static ValidatorManyToMany	validatorManyToMany;

	private static ValidatorManyToOne	validatorManyToOne;

	private static ValidatorOneToMany	validatorOneToMany;

	private static ValidatorOneToOne	validatorOneToOne;

	private static JPAEntityValidator	entityValidator;

	private JPAEntityValidator() {
		super();
	}

	public Collection<ValidationResult> validate(Pojo pojo, Operation operation) throws ValidationException {
		String pojoName = this.getPojoName(pojo);
		LocalValidationContext rootContext = new JPAValidationContext(pojo, pojoName, null, operation);
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

						LocalValidationContext context = new JPAValidationContext(value, name, annotation,
								operation, parentContext);
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
	public static JPAEntityValidator getInstance() {
		if (JPAEntityValidator.entityValidator == null) {
			synchronized (JPAEntityValidator.class) {
				if (JPAEntityValidator.entityValidator == null) {
					JPAEntityValidator.entityValidator = new JPAEntityValidator();
				}
			}
		}
		return JPAEntityValidator.entityValidator;
	}
}
