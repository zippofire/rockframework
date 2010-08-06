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
package net.woodstock.rockframework.domain.business.impl;

import java.lang.annotation.Annotation;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import net.woodstock.rockframework.config.CoreMessage;
import net.woodstock.rockframework.domain.Entity;
import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorBuilderImpl;

@SuppressWarnings("rawtypes")
public abstract class AbstractJPABusiness extends AbstractBusiness {

	public AbstractJPABusiness() {
		super();
	}

	// CRUD
	@Override
	public ValidationResult validateSave(final Entity entity) {
		return this.validateSaveOrUpdate(entity, true);
	}

	@Override
	public ValidationResult validateUpdate(final Entity entity) {
		return this.validateSaveOrUpdate(entity, false);
	}

	private ValidationResult validateSaveOrUpdate(final Entity entity, final boolean save) {
		if (entity == null) {
			throw new ValidationException(CoreMessage.getInstance().getMessage(""));
		}

		BeanDescriptor beanDescriptor = new BeanDescriptorBuilderImpl().setType(entity.getClass()).getBeanDescriptor();
		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			for (Annotation annotation : propertyDescriptor.getAnnotations()) {
				if (annotation instanceof Id) {
					ValidationResult result = this.validateId(entity, propertyDescriptor, save);
					if (result != null) {
						return result;
					}
				} else if (annotation instanceof Column) {
					ValidationResult result = this.validateColumn(entity, propertyDescriptor, (Column) annotation);
					if (result != null) {
						return result;
					}
				} else if (annotation instanceof OneToOne) {
					ValidationResult result = this.validateOneToOne(entity, propertyDescriptor, (OneToOne) annotation);
					if (result != null) {
						return result;
					}
				} else if (annotation instanceof OneToMany) {
					ValidationResult result = this.validateOneToMany();
					if (result != null) {
						return result;
					}
				} else if (annotation instanceof ManyToOne) {
					ValidationResult result = this.validateManyToOne(entity, propertyDescriptor, (ManyToOne) annotation);
					if (result != null) {
						return result;
					}
				} else if (annotation instanceof ManyToMany) {
					ValidationResult result = this.validateManyToMany();
					if (result != null) {
						return result;
					}
				}
			}
		}
		return new ValidationResult(false, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_OK));
	}

	private ValidationResult validateId(final Entity entity, final PropertyDescriptor propertyDescriptor, final boolean save) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (save) {
			if (value == null) {
				if (propertyDescriptor.isAnnotationPresent(GeneratedValue.class)) {
					return null;
				}
				return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
			}

			if (propertyDescriptor.isAnnotationPresent(GeneratedValue.class)) {
				return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NULL, name));
			}
		} else {
			if (value == null) {
				return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
			}
		}
		return null;
	}

	private ValidationResult validateColumn(final Entity entity, final PropertyDescriptor propertyDescriptor, final Column column) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (column.nullable()) {
				return null;
			}
			return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		if (value instanceof String) {
			int length = ((String) value).length();
			if (length > column.length()) {
				return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_LENGTH, name, new Integer(0), new Integer(column.length())));
			}
		}
		return null;
	}

	private ValidationResult validateManyToMany() {
		return null;
	}

	private ValidationResult validateManyToOne(final Entity entity, final PropertyDescriptor propertyDescriptor, final ManyToOne manyToOne) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (manyToOne.optional()) {
				return null;
			}
			return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		if (!(value instanceof Entity)) {
			return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_INVALID_TYPE, name));
		}

		Entity<?> e = (Entity<?>) value;

		if ((e.getId() == null) && (!manyToOne.optional())) {
			return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NOT_EMPTY, name));
		}

		return null;
	}

	private ValidationResult validateOneToOne(final Entity entity, final PropertyDescriptor propertyDescriptor, final OneToOne oneToOne) {
		String name = propertyDescriptor.getName();
		Object value = propertyDescriptor.getValue(entity);

		if (value == null) {
			if (oneToOne.optional()) {
				return null;
			}
			return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NOT_NULL, name));
		}

		if (!(value instanceof Entity)) {
			return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_INVALID_TYPE, name));
		}

		Entity<?> e = (Entity<?>) value;

		if ((e.getId() == null) && (!oneToOne.optional())) {
			return new ValidationResult(true, CoreMessage.getInstance().getMessage(AbstractBusiness.MESSAGE_VALIDATION_ERROR_NOT_EMPTY, name));
		}

		return null;
	}

	private ValidationResult validateOneToMany() {
		return null;
	}

}
