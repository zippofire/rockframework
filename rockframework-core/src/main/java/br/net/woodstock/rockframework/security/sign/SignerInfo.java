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

import java.security.PrivateKey;
import java.security.cert.Certificate;

import br.net.woodstock.rockframework.security.cert.CertificateHolder;
import br.net.woodstock.rockframework.security.timestamp.TimeStampClient;

public class SignerInfo extends CertificateHolder {

	private static final long	serialVersionUID	= -4388076526792546789L;

	private TimeStampClient		timeStampClient;

	public SignerInfo(final Certificate certificate, final PrivateKey privateKey) {
		super(certificate, privateKey);
	}

	public TimeStampClient getTimeStampClient() {
		return this.timeStampClient;
	}

	public void setTimeStampClient(final TimeStampClient timeStampClient) {
		this.timeStampClient = timeStampClient;
	}

}
