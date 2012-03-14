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
package br.net.woodstock.rockframework.web.jsp.taglib.util;

import java.io.IOException;
import java.io.Writer;

import br.net.woodstock.rockframework.security.Encoder;
import br.net.woodstock.rockframework.security.SecurityException;
import br.net.woodstock.rockframework.security.SecurityHolder;
import br.net.woodstock.rockframework.utils.ConditionUtils;
import br.net.woodstock.rockframework.web.config.WebLog;
import br.net.woodstock.rockframework.web.jsp.taglib.AbstractTag;

public abstract class AbstractEncoderTag extends AbstractTag {

	private static final String	ERROR_VALUE	= "??ERROR??";

	private String				type;

	private Object				value;

	private String				var;

	public AbstractEncoderTag() {
		super();
	}

	@Override
	public void doTag() throws IOException {
		if (ConditionUtils.isEmpty(this.type)) {
			throw new IllegalArgumentException("type must be not null");
		}
		if (this.value == null) {
			return;
		}
		Encoder encoder = this.getEncoderFromType(this.type);

		if (encoder == null) {
			throw new IllegalArgumentException("encoder not found for type '" + this.type + "'");
		}

		String value = this.value.toString();
		String encoded = "";

		try {
			encoded = this.encode(encoder, value);
		} catch (SecurityException e) {
			WebLog.getInstance().getLog().warning("Error encoding '" + value + "'  with type '" + this.type + "'");
			encoded = AbstractEncoderTag.ERROR_VALUE;
		}

		if (ConditionUtils.isEmpty(this.var)) {
			Writer writer = this.getJspContext().getOut();
			writer.write(encoded);
		} else {
			this.getJspContext().setAttribute(this.var, encoded);
		}

	}

	protected Encoder getEncoderFromType(final String type) {
		if (type.equalsIgnoreCase(EncodeType.ASYNC.name())) {
			return SecurityHolder.getAsyncEncoder();
		}

		if (type.equalsIgnoreCase(EncodeType.SYNC.name())) {
			return SecurityHolder.getSyncEncoder();
		}

		if (type.equalsIgnoreCase(EncodeType.MD5.name())) {
			return SecurityHolder.getMd5Encoder();
		}

		if (type.equalsIgnoreCase(EncodeType.SHA1.name())) {
			return SecurityHolder.getSha1Encoder();
		}
		return null;
	}

	protected abstract String encode(Encoder encoder, String text);

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(final Object value) {
		this.value = value;
	}

	public String getVar() {
		return this.var;
	}

	public void setVar(final String var) {
		this.var = var;
	}

}
