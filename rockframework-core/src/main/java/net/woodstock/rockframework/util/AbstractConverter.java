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
package net.woodstock.rockframework.util;

import net.woodstock.rockframework.utils.StringUtils;

public abstract class AbstractConverter<T> extends StringConverter<T> {

	@Override
	public T convert(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		return this.convertInternal(value);
	}

	abstract T convertInternal(String value);

	// Types
	static class ByteConverter extends AbstractConverter<Byte> {

		@Override
		public boolean isValid(Class<?> clazz) {
			return Byte.class == clazz;
		}

		@Override
		public Byte convertInternal(String value) {
			return new Byte(value);
		}
	}

	static class CharacterConverter extends AbstractConverter<Character> {

		@Override
		public boolean isValid(Class<?> clazz) {
			return Character.class == clazz;
		}

		@Override
		public Character convertInternal(String value) {
			return new Character(value.charAt(0));
		}
	}

	static class DoubleConverter extends AbstractConverter<Double> {

		@Override
		public boolean isValid(Class<?> clazz) {
			return Double.class == clazz;
		}

		@Override
		public Double convertInternal(String value) {
			return new Double(value);
		}
	}

	static class FloatConverter extends AbstractConverter<Float> {

		@Override
		public boolean isValid(Class<?> clazz) {
			return Float.class == clazz;
		}

		@Override
		public Float convertInternal(String value) {
			return new Float(value);
		}
	}

	static class IntegerConverter extends AbstractConverter<Integer> {

		@Override
		public boolean isValid(Class<?> clazz) {
			return Integer.class == clazz;
		}

		@Override
		public Integer convertInternal(String value) {
			return new Integer(value);
		}
	}

	static class LongConverter extends AbstractConverter<Long> {

		@Override
		public boolean isValid(Class<?> clazz) {
			return Long.class == clazz;
		}

		@Override
		public Long convertInternal(String value) {
			return new Long(value);
		}
	}

	static class ShortConverter extends AbstractConverter<Short> {

		@Override
		public boolean isValid(Class<?> clazz) {
			return Short.class == clazz;
		}

		@Override
		public Short convertInternal(String value) {
			return new Short(value);
		}
	}

}
