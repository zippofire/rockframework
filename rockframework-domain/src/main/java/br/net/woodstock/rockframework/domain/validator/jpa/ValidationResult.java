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
package br.net.woodstock.rockframework.domain.validator.jpa;

import br.net.woodstock.rockframework.domain.Pojo;

public class ValidationResult implements Pojo {

	private static final long	serialVersionUID	= 6766952296126588148L;

	private boolean				error;

	private String				property;

	private String				message;

	public ValidationResult(final boolean error, final String property, final String message) {
		super();
		this.error = error;
		this.property = property;
		this.message = message;
	}

	public void setError(final boolean error) {
		this.error = error;
	}

	public boolean isError() {
		return this.error;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.property);
		builder.append("[");
		if (this.error) {
			builder.append("ERROR:" + this.message);
		} else {
			builder.append("OK");
		}
		builder.append("]");
		return builder.toString();
	}

}
