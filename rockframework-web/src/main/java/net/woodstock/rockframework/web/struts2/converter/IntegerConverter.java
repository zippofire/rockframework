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
package net.woodstock.rockframework.web.struts2.converter;

import net.woodstock.rockframework.web.config.WebConfig;

public class IntegerConverter extends NumberConverter {

	private static final String	INTEGER_FORMAT_PROPERTY	= "struts2.converter.integer";

	private static final String	INTEGER_FORMAT			= WebConfig.getInstance().getValue(IntegerConverter.INTEGER_FORMAT_PROPERTY);

	public IntegerConverter() {
		super(IntegerConverter.INTEGER_FORMAT);
	}

}
