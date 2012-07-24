package br.net.woodstock.rockframework.security.cert.ext.icpbrasil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.net.woodstock.rockframework.util.Assert;

abstract class ICPBrasilHelper {

	private static final DateFormat	DATE_FORMAT	= new SimpleDateFormat("ddMMyyyy");

	private static final char		NUMBER_PAD	= '0';

	private static final char		TEXT_PAD	= ' ';

	public static String getValue(final String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	public static String getNumericValue(final String value, final int size) {
		String s = value;
		if (s == null) {
			s = "";
		}
		return ICPBrasilHelper.lpad(s, ICPBrasilHelper.NUMBER_PAD, size);
	}

	public static String getTextValue(final String value, final int size) {
		String s = value;
		if (s == null) {
			s = "";
		}
		return ICPBrasilHelper.rpad(s, ICPBrasilHelper.TEXT_PAD, size);
	}

	public static String getDateValue(final Date value) {
		Date d = value;
		if (d == null) {
			d = new Date();
		}
		return ICPBrasilHelper.DATE_FORMAT.format(d);
	}

	private static String rpad(final String value, final char pad, final int size) {
		Assert.notNull(value, "value");
		if (value.length() > size) {
			return value.substring(0, size);
		}
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		while (builder.length() < size) {
			builder.append(pad);
		}
		return builder.toString();
	}

	private static String lpad(final String value, final char pad, final int size) {
		Assert.notNull(value, "value");
		if (value.length() > size) {
			return value.substring(0, size);
		}
		String str = value;
		while (str.length() < size) {
			str = pad + str;
		}
		return str;
	}

}
