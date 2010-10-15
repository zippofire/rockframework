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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.woodstock.rockframework.util.Assert;
import net.woodstock.rockframework.utils.FileUtils;

public class FileAttachment extends AttachmentBean {

	public FileAttachment(final File file) throws IOException {
		super();
		Assert.notNull(file, "file");

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes);

			this.setName(file.getName());
			this.setContentType(FileUtils.getType(file));
			this.setContentAsString(new String(bytes));
		} catch (IOException e) {
			throw e;
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

}
