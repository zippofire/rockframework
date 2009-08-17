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
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.validation.EntityValidator;
import net.woodstock.rockframework.domain.business.validation.Operation;
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.Validator;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;
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

	public Collection<ValidationResult> validate(Entity<?> entity, Operation operation) throws ValidationException {
		String entityName = this.getEntityName(entity);
		LocalValidationContext rootContext = new JPAValidationContext(entity, entityName, null, operation);
		return this.validate(rootContext, entity, operation);
	}

	public Collection<ValidationResult> validate(LocalValidationContext rootContext, Entity<?> entity, Operation operation) throws ValidationException {
		Collection<ValidationResult> results = new LinkedList<ValidationResult>();
		this.validate(rootContext, entity, operation, results);
		return results;
	}

	private void validate(LocalValidationContext parentContext, Entity<?> entity, Operation operation, Collection<ValidationResult> results) {
		try {
			if (entity == null) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(LocalEntityValidator.MESSAGE_ERROR_NULL));
			}

			if (operation == Operation.QUERY) {
				return;
			}

			if (operation == Operation.ALL) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(LocalEntityValidator.MESSAGE_ERROR_NULL));
			}

			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(entity.getClass());
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				for (Annotation annotation : propertyDescriptor.getAnnotations()) {
					Validator validator = null;
					boolean validate = false;
					if (operation == Operation.RETRIEVE) {
						if (annotation instanceof Id) {
							if (JPAEntityValidator.validatorId == null) {
								JPAEntityValidator.validatorId = new ValidatorId();
								JPAEntityValidator.validatorId.init();
							}
							validate = true;
							validator = JPAEntityValidator.validatorId;
						}
					} else {
						if (annotation instanceof Id) {
							if (JPAEntityValidator.validatorId == null) {
								JPAEntityValidator.validatorId = new ValidatorId();
								JPAEntityValidator.validatorId.init();
							}
							validate = true;
							validator = JPAEntityValidator.validatorId;
						} else if (annotation instanceof Column) {
							if (JPAEntityValidator.validatorColumn == null) {
								JPAEntityValidator.validatorColumn = new ValidatorColumn();
								JPAEntityValidator.validatorColumn.init();
							}
							validate = true;
							validator = JPAEntityValidator.validatorColumn;
						} else if (annotation instanceof OneToOne) {
							if (JPAEntityValidator.validatorOneToOne == null) {
								JPAEntityValidator.validatorOneToOne = new ValidatorOneToOne();
								JPAEntityValidator.validatorOneToOne.init();
							}
							validate = true;
							validator = JPAEntityValidator.validatorOneToOne;
						} else if (annotation instanceof OneToMany) {
							if (JPAEntityValidator.validatorOneToMany == null) {
								JPAEntityValidator.validatorOneToMany = new ValidatorOneToMany();
								JPAEntityValidator.validatorOneToMany.init();
							}
							validate = true;
							validator = JPAEntityValidator.validatorOneToMany;
						} else if (annotation instanceof ManyToOne) {
							if (JPAEntityValidator.validatorManyToOne == null) {
								JPAEntityValidator.validatorManyToOne = new ValidatorManyToOne();
								JPAEntityValidator.validatorManyToOne.init();
							}
							validate = true;
							validator = JPAEntityValidator.validatorManyToOne;
						} else if (annotation instanceof ManyToMany) {
							if (JPAEntityValidator.validatorManyToMany == null) {
								JPAEntityValidator.validatorManyToMany = new ValidatorManyToMany();
								JPAEntityValidator.validatorManyToMany.init();
							}
							validate = true;
							validator = JPAEntityValidator.validatorManyToMany;
						}
					}

					if (validate) {
						Object value = propertyDescriptor.getValue(entity);
						String name = propertyDescriptor.getName();

						LocalValidationContext context = new JPAValidationContext(value, name, annotation, operation, parentContext);
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
	private String getEntityName(Entity<?> entity) {
		if (entity == null) {
			return null;
		}
		String className = entity.getClass().getSimpleName();
		return className;
	}

	private String getMessage(Annotation annotation) throws SecurityException, IllegalAccessException, InvocationTargetException {
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
	public static EntityValidator getInstance() {
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
