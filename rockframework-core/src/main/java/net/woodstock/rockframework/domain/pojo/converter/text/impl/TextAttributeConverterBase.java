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
package net.woodstock.rockframework.domain.pojo.converter.text.impl;

import net.woodstock.rockframework.domain.pojo.converter.text.TextCollection;
import net.woodstock.rockframework.domain.pojo.converter.text.TextField;
import net.woodstock.rockframework.util.FieldInfo;

abstract class TextAttributeConverterBase<T> implements TextAttributeConverter<T> {

	protected static final String	MESSAGE_ERROR_CONVERTER_SIZE	= "converter.exception.size";

	protected int getSize(FieldInfo f) {
		if (f.isAnnotationPresent(TextCollection.class)) {
			return f.getAnnotation(TextCollection.class).itemSize();
		}
		if (f.isAnnotationPresent(TextField.class)) {
			return f.getAnnotation(TextField.class).size();
		}
		return -1;
	}

}
