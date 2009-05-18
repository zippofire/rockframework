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

/**
 * <pre>
 * &lt;result name=&quot;success&quot; type=&quot;jasper&quot;&gt;
 *   &lt;param name=&quot;contentDisposition&quot;&gt;${contentDisposition}&lt;/param&gt;               
 *   &lt;param name=&quot;dataSource&quot;&gt;dataSource&lt;/param&gt;
 *   &lt;param name=&quot;format&quot;&gt;${format}&lt;/param&gt;
 *   &lt;param name=&quot;location&quot;&gt;${location}&lt;/param&gt;
 * &lt;/result&gt;
 * </pre>
 */
public abstract class JasperAction<T> extends BaseAction implements JasperableAction<T> {

	private static final long	serialVersionUID	= -8221347113182221227L;

	private String				location;

	private T					dataSource;

	private String				format;

	private String				name;

	private String				contentDisposition	= DownloadableAction.INLINE_CONTENT_DISPOSITION;

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public T getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(T dataSource) {
		this.dataSource = dataSource;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentDisposition() {
		return this.contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public static String getAttachmentContentDisposition(String fileName) {
		return DownloadAction.getAttachmentContentDisposition(fileName);
	}

}
