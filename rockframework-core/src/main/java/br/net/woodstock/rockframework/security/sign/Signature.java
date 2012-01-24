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
package br.net.woodstock.rockframework.security.sign;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class Signature implements Serializable {

	private static final long		serialVersionUID	= 3344909224780348665L;

	private String					reason;

	private String					location;

	private Date					date;

	private Boolean					valid;

	private Collection<Signatory>	signers;

	public Signature(final String reason, final String location, final Date date, final Boolean valid, final Collection<Signatory> signers) {
		super();
		this.reason = reason;
		this.location = location;
		this.date = date;
		this.valid = valid;
		this.signers = signers;
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

	public Boolean getValid() {
		return this.valid;
	}

	public Collection<Signatory> getSigners() {
		return this.signers;
	}

}
