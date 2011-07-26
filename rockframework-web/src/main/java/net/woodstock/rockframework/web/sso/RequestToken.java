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

public class RequestToken {

	private static final String	SEPARATOR	= "|";

	private String				id;

	private Date				date;

	private String				app;

	private String				url;

	public RequestToken() {
		super();
	}

	public RequestToken(final String id, final Date date, final String app, final String url) {
		super();
		this.id = id;
		this.date = date;
		this.app = app;
		this.url = url;
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

	public String getApp() {
		return this.app;
	}

	public void setApp(final String app) {
		this.app = app;
	}

	public final String getUrl() {
		return this.url;
	}

	public final void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.id);
		builder.append(RequestToken.SEPARATOR);
		builder.append(TokenHelper.dateToString(this.date));
		builder.append(RequestToken.SEPARATOR);
		builder.append(this.app);
		builder.append(RequestToken.SEPARATOR);
		builder.append(this.url);

		String str = builder.toString();

		return TokenHelper.toHash(str);
	}

	public static RequestToken fromString(final String token) {
		try {
			String str = TokenHelper.fromHash(token);
			String[] array = str.split(RequestToken.SEPARATOR);

			String id = array[0];
			Date date = TokenHelper.stringToDate(array[1]);
			String app = array[2];
			String url = array[3];

			RequestToken rt = new RequestToken(id, date, app, url);
			return rt;
		} catch (ParseException e) {
			throw new SSOException(e);
		}
	}

}
