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
package net.woodstock.rockframework.domain.business.validation.local.validator;

import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.LocalEntityValidator;
import net.woodstock.rockframework.domain.business.validation.local.LocalValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateExpression;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;

public class ValidatorExpression extends AbstractValidator {

	private static final String	EXPECTED_RESULT_TYPE	= Boolean.class.getCanonicalName();

	@SuppressWarnings("unchecked")
	public ValidationResult validate(final LocalValidationContext context) {
		try {
			String value = (String) context.getValue();
			ValidateExpression annotation = (ValidateExpression) context.getAnnotation();

			if (value == null) {
				return context.getSuccessResult();
			}

			for (String expression : annotation.expression()) {
				Expression e = ExpressionFactory.createExpression(expression);
				JexlContext jc = JexlHelper.createContext();

				String eName = null;
				Object eValue = null;

				if (annotation.useParent()) {
					eName = context.getParentContext().getName();
					eValue = context.getParentContext().getValue();
				} else {
					eName = context.getName();
					eValue = context.getValue();
				}

				jc.getVars().put(eName, eValue);

				Object result = e.evaluate(jc);

				if (!(result instanceof Boolean)) {
					return context.getErrorResult(this.getExpressionResultErrorMessage(context.getCanonicalName(), ValidatorExpression.EXPECTED_RESULT_TYPE, this.getResultType(result)));
				}

				Boolean b = (Boolean) result;
				if (!b.booleanValue()) {
					return context.getErrorResult(this.getErrorMessage(context.getCanonicalName()));
				}
			}

			return context.getSuccessResult();
		} catch (Exception e) {
			this.getLogger().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getResultType(final Object o) {
		if (o == null) {
			return null;
		}
		return o.getClass().getCanonicalName();
	}

	private String getErrorMessage(final String name) {
		return this.getMessage(LocalEntityValidator.MESSAGE_FIELD_ERROR_INVALID, name);
	}

	private String getExpressionResultErrorMessage(final String name, final String expected, final String found) {
		return this.getMessage(LocalEntityValidator.MESSAGE_FIELD_ERROR_EXPRESSION_RESULT, name, expected, found);
	}

}
