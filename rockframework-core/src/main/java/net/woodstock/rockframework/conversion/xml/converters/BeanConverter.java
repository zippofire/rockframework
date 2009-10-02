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
package net.woodstock.rockframework.conversion.xml.converters;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.woodstock.rockframework.conversion.Converter;
import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.Ignore;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.AbstractConverter;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.common.PropertyConverterContext;
import net.woodstock.rockframework.conversion.common.converters.BigDecimalConverter;
import net.woodstock.rockframework.conversion.common.converters.BigIntegerConverter;
import net.woodstock.rockframework.conversion.common.converters.BooleanConverter;
import net.woodstock.rockframework.conversion.common.converters.ByteConverter;
import net.woodstock.rockframework.conversion.common.converters.CharacterConverter;
import net.woodstock.rockframework.conversion.common.converters.DateConverter;
import net.woodstock.rockframework.conversion.common.converters.DoubleConverter;
import net.woodstock.rockframework.conversion.common.converters.FloatConverter;
import net.woodstock.rockframework.conversion.common.converters.IntegerConverter;
import net.woodstock.rockframework.conversion.common.converters.LongConverter;
import net.woodstock.rockframework.conversion.common.converters.NullConverter;
import net.woodstock.rockframework.conversion.common.converters.ShortConverter;
import net.woodstock.rockframework.conversion.common.converters.StringConverter;
import net.woodstock.rockframework.reflection.BeanDescriptor;
import net.woodstock.rockframework.reflection.PropertyDescriptor;
import net.woodstock.rockframework.reflection.impl.BeanDescriptorFactory;
import net.woodstock.rockframework.utils.StringUtils;
import net.woodstock.rockframework.xml.dom.XmlDocument;
import net.woodstock.rockframework.xml.dom.XmlElement;

public class BeanConverter extends AbstractConverter<XmlDocument, Object> {

	private static final String						CLASS_ATTRIBUTE	= "class";

	private static Map<Class<?>, TextConverter<?>>	converters;

	private static TextConverter<?>					nullConverter;

	public BeanConverter() {
		super();
		if (BeanConverter.converters == null) {
			BeanConverter.converters = new HashMap<Class<?>, TextConverter<?>>();
			BeanConverter.converters.put(BigDecimal.class, new BigDecimalConverter());
			BeanConverter.converters.put(BigInteger.class, new BigIntegerConverter());
			BeanConverter.converters.put(Boolean.class, new BooleanConverter());
			BeanConverter.converters.put(Byte.class, new ByteConverter());
			BeanConverter.converters.put(Character.class, new CharacterConverter());
			BeanConverter.converters.put(Date.class, new DateConverter());
			BeanConverter.converters.put(Double.class, new DoubleConverter());
			BeanConverter.converters.put(Float.class, new FloatConverter());
			BeanConverter.converters.put(Integer.class, new IntegerConverter());
			BeanConverter.converters.put(Long.class, new LongConverter());
			BeanConverter.converters.put(Short.class, new ShortConverter());
			BeanConverter.converters.put(String.class, new StringConverter());
		}
		if (BeanConverter.nullConverter == null) {
			BeanConverter.nullConverter = new NullConverter();
		}
	}

	@Override
	public Object from(ConverterContext context, XmlDocument f) throws ConverterException {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public XmlDocument to(ConverterContext context, Object t) throws ConverterException {
		if (context == null) {
			BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(t.getClass());
			context = new BeanConverterContext(beanDescriptor, t);
		}

		BeanDescriptor beanDescriptor = BeanDescriptorFactory.getByFieldInstance().getBeanDescriptor(t.getClass());
		XmlDocument document = new XmlDocument(this.getElementName(beanDescriptor.getName()));
		XmlElement root = document.getRoot();
		root.setAttribute(BeanConverter.CLASS_ATTRIBUTE, beanDescriptor.getType().getCanonicalName());

		for (PropertyDescriptor propertyDescriptor : beanDescriptor.getProperties()) {
			if (propertyDescriptor.isAnnotationPresent(Ignore.class)) {
				continue;
			}
			String name = propertyDescriptor.getName();
			Class<?> type = propertyDescriptor.getType();
			Object value = propertyDescriptor.getValue(t);
			ConverterContext subContext = new PropertyConverterContext(propertyDescriptor, context, name, type, value);
			Converter converter = BeanConverter.nullConverter;
			if (value != null) {
				converter = this.getConverter(value.getClass());
			}
			if (converter instanceof BeanConverter) {
				XmlElement element = root.addElement(this.getElementName(name));
				XmlDocument doc = (XmlDocument) converter.to(subContext, value);
				for (XmlElement e : doc.getRoot().getElements()) {
					element.addElement(e);
				}
			} else {
				String s = (String) converter.to(subContext, value);
				if (StringUtils.isEmpty(s)) {
					root.addElement(this.getElementName(name));
				} else {
					root.addElement(this.getElementName(name), s);
				}
			}
		}
		return document;
	}

	// Util
	private Converter<?, ?> getConverter(Class<?> clazz) {
		for (Entry<Class<?>, TextConverter<?>> entry : BeanConverter.converters.entrySet()) {
			if (entry.getKey().isAssignableFrom(clazz)) {
				return entry.getValue();
			}
		}
		return this;
	}

	private String getElementName(String s) {
		StringBuilder b = new StringBuilder();
		boolean charUpper = false;
		boolean numeric = false;
		for (char c : s.toCharArray()) {
			if ((Character.isLetter(c)) && (Character.isUpperCase(c))) {
				c = Character.toLowerCase(c);
				if ((b.length() > 0) && (!charUpper)) {
					b.append('-');
				}
				charUpper = true;
				numeric = false;
			} else if (Character.isDigit(c)) {
				if ((b.length() > 0) && (!numeric)) {
					b.append('-');
				}
				charUpper = false;
				numeric = true;
			} else {
				charUpper = false;
				numeric = false;
			}
			b.append(c);
		}
		return b.toString();
	}

}
