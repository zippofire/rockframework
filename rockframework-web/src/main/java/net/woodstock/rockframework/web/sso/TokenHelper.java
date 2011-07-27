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
package net.woodstock.rockframework.web.sso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.util.Base64Encoder;

public abstract class TokenHelper {

	private static final String			DATE_FORMAT				= "yyyyMMddHHmmss";

	private static final Base64Encoder	BASE64_ENCODER			= Base64Encoder.getInstance();

	private static final String			NEW_LINE				= "\n";

	private static final String			NEW_LINE_REPLACEMENT	= "__NL__";

	private TokenHelper() {
		//
	}

	public static String dateToString(final Date date) {
		Assert.notNull(date, "date");
		DateFormat dateFormat = new SimpleDateFormat(TokenHelper.DATE_FORMAT);
		String str = dateFormat.format(date);
		return str;
	}

	public static Date stringToDate(final String str) throws ParseException {
		Assert.notEmpty(str, "str");
		DateFormat dateFormat = new SimpleDateFormat(TokenHelper.DATE_FORMAT);
		Date date = dateFormat.parse(str);
		return date;
	}

	static String toHash(final String str) {
		String hash = TokenHelper.BASE64_ENCODER.encode(str);
		String newHash = hash.replaceAll(TokenHelper.NEW_LINE, TokenHelper.NEW_LINE_REPLACEMENT);
		return newHash;
	}

	static String fromHash(final String hash) {
		String str = TokenHelper.BASE64_ENCODER.decode(hash);
		String newStr = str.replaceAll(TokenHelper.NEW_LINE_REPLACEMENT, TokenHelper.NEW_LINE);
		return newStr;
	}

}
