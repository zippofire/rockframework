package net.woodstock.rockframework.conversion.text.converters;

import net.woodstock.rockframework.conversion.ConverterContext;
import net.woodstock.rockframework.conversion.text.Size;
import net.woodstock.rockframework.utils.StringUtils;

public abstract class TextConverterHelper {

	private TextConverterHelper() {
		//
	}

	protected static int getSize(ConverterContext context) {
		if (context.isAnnotationPresent(Size.class)) {
			return context.getAnnotation(Size.class).value();
		}
		return -1;
	}

	// Format
	protected static String lpad(String s, int size) {
		if (size < 1) {
			return s;
		}
		return StringUtils.lpad(s, size, ' ').substring(0, size);
	}

	protected static String rpad(String s, int size) {
		if (size < 1) {
			return s;
		}
		return StringUtils.rpad(s, size, ' ').substring(0, size);
	}

	protected static String trim(String s) {
		String str = s.trim();
		if (str.length() == 0) {
			return null;
		}
		return str;
	}

}
