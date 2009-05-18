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
package net.woodstock.rockframework.security.common;

public enum Charset {

	ASCII("ASCII"), Cp1250("Cp1250"), Cp1251("Cp1251"), Cp1252("Cp1252"), Cp1253("Cp1253"), Cp1254("Cp1254"), Cp1257(
			"Cp1257"), DEFAULT("UTF8"), ISO8859_1("ISO8859_1"), ISO8859_2("ISO8859_2"), ISO8859_4("ISO8859_4"), ISO8859_5(
			"ISO8859_5"), ISO8859_7("ISO8859_7"), ISO8859_9("ISO8859_9"), ISO8859_13("ISO8859_13"), ISO8859_15(
			"ISO8859_15"), KOI8_R("KOI8_R"), UTF8("UTF8"), UTF_16("UTF-16"), UnicodeBigUnmarked(
			"UnicodeBigUnmarked"), UnicodeLittleUnmarked("UnicodeLittleUnmarked"), UnicodeBig("UnicodeBig"), UnicodeLittle(
			"UnicodeLittle");

	private String	charset;

	private Charset(String charset) {
		this.charset = charset;
	}

	public String charset() {
		return this.charset;
	}

}
