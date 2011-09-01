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
package br.net.woodstock.rockframework.domain;

public class DomainException extends RuntimeException {

	private static final long	serialVersionUID	= 5354305796553036166L;

	public DomainException(final String message) {
		super(message);
	}

	public DomainException(final Throwable cause) {
		super(cause);
	}

	public DomainException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
