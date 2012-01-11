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
package br.net.woodstock.rockframework.office.pdf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class PDFSignature implements Serializable {

	private static final long		serialVersionUID	= 3344909224780348665L;

	private String					reason;

	private String					location;

	private Date					date;

	private Collection<PDFSigner>	signers;

	public PDFSignature(final String reason, final String location, final Date date) {
		super();
		this.reason = reason;
		this.location = location;
		this.date = date;
		this.signers = new ArrayList<PDFSigner>();
	}

	public String getReason() {
		return this.reason;
	}

	public String getLocation() {
		return this.location;
	}

	public Date getDate() {
		return this.date;
	}

	public Collection<PDFSigner> getSigners() {
		return this.signers;
	}

}
