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

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.cert.PrivateKeyHolder;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;

public class PKCS7SignatureParameters extends SignatureParameters {

	private static final long	serialVersionUID	= -6981088435915238029L;

	private String				name;

	private String				reason;

	private String				location;

	private String				contactInfo;

	private TimeStampClient		timeStampClient;

	public PKCS7SignatureParameters(final Alias alias, final Store store) {
		super(new Alias[] { alias }, store);
	}

	public PKCS7SignatureParameters(final Alias alias, final Store store, final String name, final String reason, final String location, final String contactInfo, final TimeStampClient timeStampClient) {
		super(new Alias[] { alias }, store);
		this.name = name;
		this.reason = reason;
		this.location = location;
		this.contactInfo = contactInfo;
		this.timeStampClient = timeStampClient;
	}

	public PKCS7SignatureParameters(final Alias[] aliases, final Store store) {
		super(aliases, store);
	}

	public PKCS7SignatureParameters(final Alias[] aliases, final Store store, final String name, final String reason, final String location, final String contactInfo, final TimeStampClient timeStampClient) {
		super(aliases, store);
		this.name = name;
		this.reason = reason;
		this.location = location;
		this.contactInfo = contactInfo;
		this.timeStampClient = timeStampClient;
	}

	public PKCS7SignatureParameters(final PrivateKeyHolder privateKeyHolder) {
		super(privateKeyHolder);
	}

	public PKCS7SignatureParameters(final PrivateKeyHolder privateKeyHolder, final String name, final String reason, final String location, final String contactInfo, final TimeStampClient timeStampClient) {
		super(privateKeyHolder);
		this.name = name;
		this.reason = reason;
		this.location = location;
		this.contactInfo = contactInfo;
		this.timeStampClient = timeStampClient;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
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

	public String getContactInfo() {
		return this.contactInfo;
	}

	public void setContactInfo(final String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public TimeStampClient getTimeStampClient() {
		return this.timeStampClient;
	}

	public void setTimeStampClient(final TimeStampClient timeStampClient) {
		this.timeStampClient = timeStampClient;
	}

}
