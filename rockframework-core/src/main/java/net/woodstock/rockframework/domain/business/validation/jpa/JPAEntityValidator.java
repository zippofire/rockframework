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
import net.woodstock.rockframework.domain.business.validation.ValidationException;
import net.woodstock.rockframework.domain.business.validation.ValidationResult;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;

public final class JPAEntityValidator implements EntityValidator {

	public static final String			MESSAGE_ERROR_NULL					= "domain.validation.error.null";

	public static final String			MESSAGE_FIELD_ERROR_INVALID_TYPE	= "domain.validation.field.error.invalidType";

	public static final String			MESSAGE_FIELD_ERROR_LENGTH			= "domain.validation.field.error.length";

	public static final String			MESSAGE_FIELD_ERROR_NOT_EMPTY		= "domain.validation.field.error.notEmpty";

	public static final String			MESSAGE_FIELD_ERROR_NULL			= "domain.validation.field.error.null";

	private static ValidatorColumn		validatorColumn						= new ValidatorColumn();

	private static ValidatorId			validatorId							= new ValidatorId();

	private static ValidatorManyToMany	validatorManyToMany					= new ValidatorManyToMany();

	private static ValidatorManyToOne	validatorManyToOne					= new ValidatorManyToOne();

	private static ValidatorOneToMany	validatorOneToMany					= new ValidatorOneToMany();

	private static ValidatorOneToOne	validatorOneToOne					= new ValidatorOneToOne();

	private static JPAEntityValidator	instance							= new JPAEntityValidator();

	private JPAEntityValidator() {
		super();
	}

	public Collection<ValidationResult> validate(final Entity<?> entity) {
		String entityName = this.getEntityName(entity);
		JPAValidationContext rootContext = new JPAValidationContext(entity, entityName, null);
		return this.validate(rootContext, entity);
	}

	public Collection<ValidationResult> validate(final JPAValidationContext rootContext, final Entity<?> entity) {
		Collection<ValidationResult> results = new LinkedList<ValidationResult>();
		this.validate(rootContext, entity, results);
		return results;
	}

	private void validate(final JPAValidationContext parentContext, final Entity<?> entity, final Collection<ValidationResult> results) {
		try {
			if (entity == null) {
				throw new ValidationException(CoreMessage.getInstance().getMessage(JPAEntityValidator.MESSAGE_ERROR_NULL));
			}

			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getInstance().getBeanDescriptor(entity.getClass());
			for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
				for (Annotation annotation : propertyDescriptor.getAnnotations()) {
					Validator validator = null;
					boolean validate = false;
					if (annotation instanceof Id) {
						// FIXME
						validate = false;
						validator = JPAEntityValidator.validatorId;
					} else if (annotation instanceof Column) {
						validate = true;
						validator = JPAEntityValidator.validatorColumn;
					} else if (annotation instanceof OneToOne) {
						validate = true;
						validator = JPAEntityValidator.validatorOneToOne;
					} else if (annotation instanceof OneToMany) {
						validate = true;
						validator = JPAEntityValidator.validatorOneToMany;
					} else if (annotation instanceof ManyToOne) {
						validate = true;
						validator = JPAEntityValidator.validatorManyToOne;
					} else if (annotation instanceof ManyToMany) {
						validate = true;
						validator = JPAEntityValidator.validatorManyToMany;
					}

					if (validate) {
						Object value = propertyDescriptor.getValue(entity);
						String name = propertyDescriptor.getName();

						JPAValidationContext context = new JPAValidationContext(value, name, annotation, parentContext);
						ValidationResult result = validator.validate(context);

						results.add(result);
					}
				}
			}
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

	// Utils
	private String getEntityName(final Entity<?> entity) {
		if (entity == null) {
			return null;
		}
		String className = entity.getClass().getSimpleName();
		return className;
	}

	// Instance
	public static JPAEntityValidator getInstance() {
		return JPAEntityValidator.instance;
	}
}
