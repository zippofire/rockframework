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

import java.text.ParseException;
import java.util.Date;

public class ResponseToken {

	private static final String	SEPARATOR	= "|";

	private String				id;

	private Date				date;

	private String				hash;

	public ResponseToken() {
		super();
	}

	public ResponseToken(final String id, final Date date, final String hash) {
		super();
		this.id = id;
		this.date = date;
		this.hash = hash;
	}

	public final String getId() {
		return this.id;
	}

	public final void setId(final String id) {
		this.id = id;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getHash() {
		return this.hash;
	}

	public void setHash(final String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.id);
		builder.append(ResponseToken.SEPARATOR);
		builder.append(TokenHelper.dateToString(this.date));
		builder.append(ResponseToken.SEPARATOR);
		builder.append(this.hash);

		String str = builder.toString();

		return TokenHelper.toHash(str);
	}

	public static ResponseToken fromString(final String token) {
		try {
			String str = TokenHelper.fromHash(token);
			String[] array = str.split(ResponseToken.SEPARATOR);

			String id = array[0];
			Date date = TokenHelper.stringToDate(array[1]);
			String hash = array[2];

			ResponseToken rt = new ResponseToken(id, date, hash);
			return rt;
		} catch (ParseException e) {
			throw new SSOException(e);
		}
	}

}
