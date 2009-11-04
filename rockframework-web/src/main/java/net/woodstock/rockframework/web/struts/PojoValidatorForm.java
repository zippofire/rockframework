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
package net.woodstock.rockframework.web.struts;

import net.woodstock.rockframework.domain.Pojo;

public abstract class PojoValidatorForm<T extends Pojo> extends BaseValidatorForm {

	private static final long	serialVersionUID	= 4444064716134178684L;

	private T					pojo;

	public PojoValidatorForm() {
		super();
	}

	public PojoValidatorForm(T pojo) {
		super();
		this.pojo = pojo;
	}

	public T getPojo() {
		return this.pojo;
	}

	public void setPojo(T pojo) {
		this.pojo = pojo;
	}

}
