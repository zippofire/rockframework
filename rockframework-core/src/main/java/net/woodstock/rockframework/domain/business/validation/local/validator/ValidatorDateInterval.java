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

import java.text.DateFormat;
import java.util.Date;

import net.woodstock.rockframework.domain.business.ValidationException;
import net.woodstock.rockframework.domain.business.validation.local.ObjectValidator;
import net.woodstock.rockframework.domain.business.validation.local.ValidationContext;
import net.woodstock.rockframework.domain.business.validation.local.ValidationResult;
import net.woodstock.rockframework.domain.business.validation.local.annotation.ValidateDateInterval;
import net.woodstock.rockframework.util.Calendar;

public class ValidatorDateInterval extends AbstractObjectValidator {

	private DateFormat	dateFormat;

	public ValidatorDateInterval() {
		super();
		this.dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
	}

	public ValidationResult validate(ValidationContext context) throws ValidationException {
		try {
			ValidateDateInterval annotation = (ValidateDateInterval) context.getAnnotation();
			Date value = (Date) context.getValue();

			if (value == null) {
				return context.getSuccessResult();
			}

			Calendar current = Calendar.getInstance();
			Calendar calendar = Calendar.getInstance();

			calendar.setTime(value);

			long diff = 0;

			switch (annotation.type()) {
				case DAYS:
					diff = current.diffDays(calendar);
					break;
				case MONTHS:
					diff = current.diffMonths(calendar);
					break;
				case YEARS:
					diff = current.diffYears(calendar);
					break;
				default:
					break;
			}

			if (diff > 0) {
				if (Math.abs(diff) <= Math.abs(annotation.past())) {
					return context.getSuccessResult();
				}
			}

			if (Math.abs(diff) <= Math.abs(annotation.future())) {
				return context.getSuccessResult();
			}

			return context.getErrorResult(this.getErrorMessage(annotation, current, context
					.getCanonicalName()));
		} catch (Exception e) {
			this.getLogger().info(e.getMessage(), e);
			throw new ValidationException(e);
		}
	}

	private String getErrorMessage(ValidateDateInterval annotation, Calendar calendar, String name) {
		Calendar pastCalendar = Calendar.getInstance();
		Calendar futureCalendar = Calendar.getInstance();

		pastCalendar.setTime(calendar.getTime());
		futureCalendar.setTime(calendar.getTime());

		switch (annotation.type()) {
			case DAYS:
				pastCalendar.removeDays(annotation.past());
				futureCalendar.addDays(annotation.future());
				break;
			case MONTHS:
				pastCalendar.removeMonths(annotation.past());
				futureCalendar.addMonths(annotation.future());
				break;
			case YEARS:
				pastCalendar.removeYears(annotation.past());
				futureCalendar.addYears(annotation.future());
				break;
			default:
				break;
		}

		String pastDate = this.dateFormat.format(pastCalendar.getTime());
		String futureDate = this.dateFormat.format(futureCalendar.getTime());

		return this.getMessage(ObjectValidator.MESSAGE_FIELD_ERROR_DATE_INTERVAL, name, pastDate, futureDate,
				annotation.type().name());
	}
}
