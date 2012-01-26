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

import br.net.woodstock.rockframework.security.timestamp.TimeStamp;

public class Signature implements Serializable {

	private static final long		serialVersionUID	= 3344909224780348665L;

	private String					reason;

	private String					location;

	private Date					date;

	private Boolean					valid;

	private byte[]					encoded;

	private TimeStamp				timeStamp;

	private Collection<Signatory>	signers;

	public Signature() {
		super();
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public Boolean getValid() {
		return this.valid;
	}

	public void setValid(final Boolean valid) {
		this.valid = valid;
	}

	public byte[] getEncoded() {
		return this.encoded;
	}

	public void setEncoded(final byte[] encoded) {
		this.encoded = encoded;
	}

	public TimeStamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(final TimeStamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Collection<Signatory> getSigners() {
		return this.signers;
	}

	public void setSigners(final Collection<Signatory> signers) {
		this.signers = signers;
	}

}
