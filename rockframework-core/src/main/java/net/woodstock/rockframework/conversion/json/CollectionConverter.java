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

	private static final String	BEGIN_ARRAY			= "[";

	private static final String	END_ARRAY			= "]";

	private static final String	ELEMENT_SEPARATOR	= ", ";

	@Override
	public Collection from(final ConverterContext context, final String s) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String to(final ConverterContext context, final Collection t) {
		if (context == null) {
			throw new IllegalArgumentException("Context must be not null");
		}
		if (t == null) {
			throw new IllegalArgumentException("Object must be not null");
		}
		try {
			StringBuilder builder = new StringBuilder();
			boolean first = true;
			ConverterContext currentContext = context;

			if (!(currentContext instanceof BeanConverterContext)) {
				currentContext = new BeanConverterContext(currentContext.getParent(), currentContext.getName(), t.getClass());
			}

			builder.append(CollectionConverter.BEGIN_ARRAY);

			for (Object o : t) {
				if (o != null) {
					TextConverter converter = JsonConverterHelper.getConverter(o.getClass());
					ConverterContext subContext = null;
					if (currentContext.getParent() != null) {
						subContext = new PropertyConverterContext(currentContext.getParent(), currentContext.getName(), o.getClass());
					} else {
						subContext = new PropertyConverterContext(currentContext, currentContext.getName(), o.getClass());
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
