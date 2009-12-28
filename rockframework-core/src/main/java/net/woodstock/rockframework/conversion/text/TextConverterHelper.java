package net.woodstock.rockframework.conversion.text;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.utils.StringUtils;

abstract class TextConverterHelper {

	private TextConverterHelper() {
		//
	}

	protected static int getSize(final ConverterContext context) {
		if (context.isAnnotationPresent(Size.class)) {
			return context.getAnnotation(Size.class).value();
		}
		return -1;
	}

	// Format
	protected static String lpad(final String s, final int size) {
		if (size < 1) {
			return s;
		}
		return StringUtils.lpad(s, size, ' ').substring(0, size);
	}

	protected static String rpad(final String s, final int size) {
		if (size < 1) {
			return s;
		}
		return StringUtils.rpad(s, size, ' ').substring(0, size);
	}

	protected static String trim(final String s) {
		String str = s.trim();
		if (str.length() == 0) {
			return null;
		}
		return str;
	}

}
