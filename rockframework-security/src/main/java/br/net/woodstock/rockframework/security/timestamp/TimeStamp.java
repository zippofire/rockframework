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
package br.net.woodstock.rockframework.security.timestamp;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.cert.Certificate;
import java.util.Date;

public class TimeStamp implements Serializable {

	private static final long	serialVersionUID	= -8904344810803949405L;

	private BigInteger			nonce;

	private BigInteger			serialNumber;

	private Date				date;

	private byte[]				encoded;

	private byte[]				hash;

	private byte[]				content;

	private Certificate[]		certificates;

	public TimeStamp() {
		super();
	}

	public BigInteger getNonce() {
		return this.nonce;
	}

	public void setNonce(final BigInteger nonce) {
		this.nonce = nonce;
	}

	public BigInteger getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(final BigInteger serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public byte[] getEncoded() {
		return this.encoded;
	}

	public void setEncoded(final byte[] encoded) {
		this.encoded = encoded;
	}

	public byte[] getHash() {
		return this.hash;
	}

	public void setHash(final byte[] hash) {
		this.hash = hash;
	}

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(final byte[] content) {
		this.content = content;
	}

	public Certificate[] getCertificates() {
		return this.certificates;
	}

	public void setCertificates(final Certificate[] certificates) {
		this.certificates = certificates;
	}

}
