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
package net.woodstock.rockframework.conversion.json;

import java.util.Collection;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.ConverterException;
import net.woodstock.rockframework.conversion.TextConverter;
import net.woodstock.rockframework.conversion.common.AbstractTextConverter;
import net.woodstock.rockframework.conversion.common.BeanConverterContext;
import net.woodstock.rockframework.conversion.common.PropertyConverterContext;

@SuppressWarnings("unchecked")
public class CollectionConverter extends AbstractTextConverter<Collection> {

	private static String	BEGIN_ARRAY			= "[";

	private static String	END_ARRAY			= "]";

	private static String	ELEMENT_SEPARATOR	= ", ";

	@Override
	public Collection from(ConverterContext context, String s) throws ConverterException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String to(ConverterContext context, Collection t) throws ConverterException {
		if (context == null) {
			throw new IllegalArgumentException("Context must be not null");
		}
		if (t == null) {
			throw new IllegalArgumentException("Object must be not null");
		}
		try {
			StringBuilder builder = new StringBuilder();
			boolean first = true;

			if (!(context instanceof BeanConverterContext)) {
				context = new BeanConverterContext(context.getParent(), context.getName(), t.getClass());
			}

			builder.append(CollectionConverter.BEGIN_ARRAY);

			for (Object o : t) {
				if (o != null) {
					TextConverter converter = JsonConverterHelper.getConverter(o.getClass());
					ConverterContext subContext = null;
					if (context.getParent() != null) {
						subContext = new PropertyConverterContext(context.getParent(), context.getName(), o.getClass());
					} else {
						subContext = new PropertyConverterContext(context, context.getName(), o.getClass());
					}

					String s = (String) converter.to(subContext, o);

					if (!first) {
						builder.append(CollectionConverter.ELEMENT_SEPARATOR);
					}

					builder.append(s);

					if (first) {
						first = false;
					}
				}
			}

			builder.append(CollectionConverter.END_ARRAY);

			String s = builder.toString();
			return s;
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			throw new ConverterException(e);
		}
	}

}
