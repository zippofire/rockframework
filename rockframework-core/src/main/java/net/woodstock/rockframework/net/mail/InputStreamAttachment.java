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
package net.woodstock.rockframework.net.mail;

import java.io.IOException;
import java.io.InputStream;

import net.woodstock.rockframework.util.Assert;

public class InputStreamAttachment extends AttachmentBean {

	public InputStreamAttachment(final String name, final String contentType, final InputStream inputStream) throws IOException {
		super();
		Assert.notEmpty(name, "name");
		Assert.notEmpty(contentType, "contentType");
		Assert.notNull(inputStream, "inputStream");

		byte[] bytes = new byte[inputStream.available()];
		inputStream.read(bytes);

		this.setName(name);
		this.setContentType(contentType);
		this.setContentAsString(new String(bytes));
	}

}
