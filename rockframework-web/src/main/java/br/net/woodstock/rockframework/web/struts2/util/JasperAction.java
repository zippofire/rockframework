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
package br.net.woodstock.rockframework.web.struts2.util;

import br.net.woodstock.rockframework.web.struts2.AbstractAction;
import br.net.woodstock.rockframework.web.utils.ResponseUtils;

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
public abstract class JasperAction<T> extends AbstractAction implements JasperableAction<T> {

	private static final long	serialVersionUID	= -8221347113182221227L;

	private String				location;

	private T					dataSource;

	private String				format;

	private String				name;

	private String				contentDisposition	= ResponseUtils.INLINE_CONTENT_DISPOSITION;

	@Override
	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	@Override
	public T getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(final T dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public String getFormat() {
		return this.format;
	}

	public void setFormat(final String format) {
		this.format = format;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String getContentDisposition() {
		return this.contentDisposition;
	}

	public void setContentDisposition(final String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

}
