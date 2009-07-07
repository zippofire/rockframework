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
package net.woodstock.rockframework.web.struts2.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Scanner;

/**
 * <pre>
 * &lt;result name=&quot;success&quot; type=&quot;stream&quot;&gt;
 *   &lt;param name=&quot;contentDisposition&quot;&gt;${contentDisposition}&lt;/param&gt;
 *   &lt;param name=&quot;contentType&quot;&gt;${contentType}&lt;/param&gt;
 *   &lt;param name=&quot;inputName&quot;&gt;inputStream&lt;/param&gt;
 * &lt;/result&gt;
 * </pre>
 */
public abstract class DownloadAction extends BaseAction implements DownloadableAction {

	private static final long	serialVersionUID	= 2247727504526009577L;

	private InputStream			inputStream;

	private String				contentType;

	private String				contentDisposition;

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setInputStream(Reader reader) {
		Scanner scanner = new Scanner(reader);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while (scanner.hasNextByte()) {
			output.write(scanner.nextByte());
		}
		this.inputStream = new ByteArrayInputStream(output.toByteArray());
	}

	public void setInputStream(File file) throws FileNotFoundException {
		this.inputStream = new FileInputStream(file);
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentDisposition() {
		return this.contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

}
