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
package net.woodstock.rockframework.domain.validator.jpa.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.config.DomainMessage;
import net.woodstock.rockframework.domain.validator.jpa.Operation;
import net.woodstock.rockframework.domain.validator.jpa.ValidationException;
import net.woodstock.rockframework.domain.validator.jpa.ValidationResult;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;
import net.woodstock.rockframework.util.Assert;

@SuppressWarnings("rawtypes")
public class EntityValidatorImpl extends AbstractEntityValidator {

	private Operation	operation;

	private boolean		includeSuccessResult;

	public EntityValidatorImpl(final Operation operation) {
		this(operation, false);
	}

	public EntityValidatorImpl(final Operation operation, final boolean includeSuccessResult) {
		super();
		Assert.notNull(operation, "operation");
		this.operation = operation;
		this.includeSuccessResult = includeSuccessResult;
	}

	@Override
	public Collection<ValidationResult> validate(final Entity entity) {
		if (entity == null) {
			throw new ValidationException(this.getMessage(AbstractEntityValidator.MESSAGE_INVALID_OBJECT));
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(entity.getClass()).getBeanDescriptor();
		Collection<ValidationResult> collection = new ArrayList<ValidationResult>();
		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			for (Annotation annotation : propertyDescriptor.getAnnotations()) {
				ValidationResult result = null;
				if (annotation instanceof Id) {
					result = this.validateId(entity, propertyDescriptor, this.operation);
				} else if (annotation instanceof Column) {
					result = this.validateColumn(entity, propertyDescriptor, (Column) annotation);
				} else if (annotation instanceof OneToOne) {
					result = this.validateOneToOne(entity, propertyDescriptor, (OneToOne) annotation);
				} else if (annotation instanceof OneToMany) {
					result = this.validateOneToMany(entity, propertyDescriptor, (OneToMany) annotation);
				} else if (annotation instanceof ManyToOne) {
					result = this.validateManyToOne(entity, propertyDescriptor, (ManyToOne) annotation);
				} else if (annotation instanceof ManyToMany) {
					result = this.validateManyToMany(entity, propertyDescriptor, (ManyToMany) annotation);
				}

				if (result != null) {
					if (result.isError()) {
						collection.add(result);
					} else if (this.includeSuccessResult) {
						collection.add(result);
					}
				}
			}
		}
		return collection;
	}

	private ValidationResult validateId(final Entity entity, final PropertyDescriptor propertyDescriptor, final Operation operation) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (operation == Operation.PERSIST) {
			if (value == null) {
				if (propertyDescriptor.isAnnotationPresent(GeneratedValue.class)) {
					return null;
				}
				return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
			}

			if (propertyDescriptor.isAnnotationPresent(GeneratedValue.class)) {
				return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NULL, name));
			}
		} else {
			if (value == null) {
				return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
			}
		}
		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	private ValidationResult validateColumn(final Entity entity, final PropertyDescriptor propertyDescriptor, final Column column) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (column.nullable()) {
				return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
			}
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		if (value instanceof String) {
			int length = ((String) value).length();
			if (length > column.length()) {
				return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_LENGTH, name, Integer.valueOf(0), Integer.valueOf(column.length())));
			}
		}
		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	@SuppressWarnings("unused")
	private ValidationResult validateManyToMany(final Entity entity, final PropertyDescriptor propertyDescriptor, final ManyToMany manyToMany) {
		String name = propertyDescriptor.getName();
		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	private ValidationResult validateManyToOne(final Entity entity, final PropertyDescriptor propertyDescriptor, final ManyToOne manyToOne) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (manyToOne.optional()) {
				return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
			}
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		if (!(value instanceof Entity)) {
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_INVALID_TYPE, name));
		}

		Entity<?> e = (Entity<?>) value;

		if ((e.getId() == null) && (!manyToOne.optional())) {
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_EMPTY, name));
		}

		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	private ValidationResult validateOneToOne(final Entity entity, final PropertyDescriptor propertyDescriptor, final OneToOne oneToOne) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (oneToOne.optional()) {
				return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
			}
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		if (!(value instanceof Entity)) {
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_INVALID_TYPE, name));
		}

		Entity<?> e = (Entity<?>) value;

		if ((e.getId() == null) && (!oneToOne.optional())) {
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_EMPTY, name));
		}

		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	@SuppressWarnings("unused")
	private ValidationResult validateOneToMany(final Entity entity, final PropertyDescriptor propertyDescriptor, final OneToMany oneToMany) {
		String name = propertyDescriptor.getName();
		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

}
