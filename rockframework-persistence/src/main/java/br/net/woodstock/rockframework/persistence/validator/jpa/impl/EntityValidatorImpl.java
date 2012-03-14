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
package br.net.woodstock.rockframework.persistence.validator.jpa.impl;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import br.net.woodstock.rockframework.domain.config.DomainMessage;
import br.net.woodstock.rockframework.persistence.validator.jpa.Operation;
import br.net.woodstock.rockframework.persistence.validator.jpa.ValidationException;
import br.net.woodstock.rockframework.persistence.validator.jpa.ValidationResult;
import br.net.woodstock.rockframework.reflection.BeanDescriptor;
import br.net.woodstock.rockframework.reflection.PropertyDescriptor;
import br.net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilder;
import br.net.woodstock.rockframework.util.Assert;

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
	public Collection<ValidationResult> validate(final Object entity) {
		if (entity == null) {
			throw new ValidationException(this.getMessage(AbstractEntityValidator.MESSAGE_INVALID_OBJECT));
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(entity.getClass()).getBeanDescriptor();
		Collection<ValidationResult> collection = new ArrayList<ValidationResult>();
		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			ValidationResult result = null;
			boolean checkIdOnly = false;
			boolean checkColumn = false;
			boolean checkJoinColumn = false;

			if ((this.operation == Operation.FIND) || (this.operation == Operation.REMOVE)) {
				checkIdOnly = true;
			}

			if (checkIdOnly) {
				if (propertyDescriptor.isAnnotationPresent(Id.class)) {
					result = this.validateId(entity, propertyDescriptor, this.operation);
				}
			} else {
				if (propertyDescriptor.isAnnotationPresent(Id.class)) {
					result = this.validateId(entity, propertyDescriptor, this.operation);
					checkColumn = true;
				} else if (propertyDescriptor.isAnnotationPresent(Basic.class)) {
					result = this.validateBasic(entity, propertyDescriptor, propertyDescriptor.getAnnotation(Basic.class));
					checkColumn = true;
				} else if (propertyDescriptor.isAnnotationPresent(OneToOne.class)) {
					result = this.validateOneToOne(entity, propertyDescriptor, propertyDescriptor.getAnnotation(OneToOne.class));
					checkJoinColumn = true;
				} else if (propertyDescriptor.isAnnotationPresent(OneToMany.class)) {
					result = this.validateOneToMany(entity, propertyDescriptor, propertyDescriptor.getAnnotation(OneToMany.class));
				} else if (propertyDescriptor.isAnnotationPresent(ManyToOne.class)) {
					result = this.validateManyToOne(entity, propertyDescriptor, propertyDescriptor.getAnnotation(ManyToOne.class));
					checkJoinColumn = true;
				} else if (propertyDescriptor.isAnnotationPresent(ManyToMany.class)) {
					result = this.validateManyToMany(entity, propertyDescriptor, propertyDescriptor.getAnnotation(ManyToMany.class));
				} else if (propertyDescriptor.isAnnotationPresent(Column.class)) {
					result = this.validateColumn(entity, propertyDescriptor, propertyDescriptor.getAnnotation(Column.class));
				}

				if ((result != null) && (!result.isError())) {
					if (checkColumn) {
						if (propertyDescriptor.isAnnotationPresent(Column.class)) {
							result = this.validateColumn(entity, propertyDescriptor, propertyDescriptor.getAnnotation(Column.class));
						}
					}

					if (checkJoinColumn) {
						if (propertyDescriptor.isAnnotationPresent(JoinColumn.class)) {
							result = this.validateJoinColumn(entity, propertyDescriptor, propertyDescriptor.getAnnotation(JoinColumn.class));
						}
					}
				}
			}

			if (result != null) {
				if (result.isError()) {
					collection.add(result);
				} else if (this.includeSuccessResult) {
					collection.add(result);
				}
			}
		}
		return collection;
	}

	private ValidationResult validateId(final Object entity, final PropertyDescriptor propertyDescriptor, final Operation operation) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (operation == Operation.PERSIST) {
			if (value == null) {
				if (propertyDescriptor.isAnnotationPresent(GeneratedValue.class)) {
					return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
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

	private ValidationResult validateColumn(final Object entity, final PropertyDescriptor propertyDescriptor, final Column column) {
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

	private ValidationResult validateJoinColumn(final Object entity, final PropertyDescriptor propertyDescriptor, final JoinColumn column) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (column.nullable()) {
				return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
			}
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}
		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	private ValidationResult validateBasic(final Object entity, final PropertyDescriptor propertyDescriptor, final Basic basic) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (basic.optional()) {
				return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
			}
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	@SuppressWarnings("unused")
	private ValidationResult validateManyToMany(final Object entity, final PropertyDescriptor propertyDescriptor, final ManyToMany manyToMany) {
		String name = propertyDescriptor.getName();
		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	private ValidationResult validateManyToOne(final Object entity, final PropertyDescriptor propertyDescriptor, final ManyToOne manyToOne) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (manyToOne.optional()) {
				return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
			}
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		Object id = this.getId(value);

		if ((id == null) && (!manyToOne.optional())) {
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_INVALID_REFERENCE, name));
		}

		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	private ValidationResult validateOneToOne(final Object entity, final PropertyDescriptor propertyDescriptor, final OneToOne oneToOne) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (oneToOne.optional()) {
				return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
			}
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		Object id = this.getId(value);

		if ((id == null) && (!oneToOne.optional())) {
			return new ValidationResult(true, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_ERROR_INVALID_REFERENCE, name));
		}

		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	@SuppressWarnings("unused")
	private ValidationResult validateOneToMany(final Object entity, final PropertyDescriptor propertyDescriptor, final OneToMany oneToMany) {
		String name = propertyDescriptor.getName();
		return new ValidationResult(false, name, DomainMessage.getInstance().getMessage(AbstractEntityValidator.MESSAGE_VALIDATION_OK));
	}

	private Object getId(final Object o) {
		BeanDescriptor beanDescriptor = new BeanDescriptorBuilder(o.getClass()).getBeanDescriptor();
		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			if (propertyDescriptor.isAnnotationPresent(Id.class)) {
				return propertyDescriptor.getValue(o);
			}
		}
		return null;
	}

}
