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
package net.woodstock.rockframework.domain.validator.jee;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;

import net.woodstock.rockframework.config.CoreLog;

import org.apache.commons.logging.Log;

public abstract class AbstractValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {

	private A	annotation;

	public final void initialize(final A annotation) {
		this.annotation = annotation;
		this.initialize();
	}

	public void initialize() {
		//
	}

	public A getAnnotation() {
		return this.annotation;
	}

	protected Log getLog() {
		return CoreLog.getInstance().getLog();
	}

}
