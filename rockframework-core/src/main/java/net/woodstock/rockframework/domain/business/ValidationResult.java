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
package net.woodstock.rockframework.domain.business;

import java.io.Serializable;

public class ValidationResult implements Serializable {

	private static final long	serialVersionUID	= 6766952296126588148L;

	private boolean				error;

	private String				message;

	public ValidationResult(final boolean error, final String message) {
		super();
		this.error = error;
		this.message = message;
	}

	public void setError(final boolean error) {
		this.error = error;
	}

	public boolean isError() {
		return this.error;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	// Others
	public boolean isSuccess() {
		return !this.error;
	}

}
