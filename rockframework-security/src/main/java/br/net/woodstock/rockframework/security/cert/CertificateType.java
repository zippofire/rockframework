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
package br.net.woodstock.rockframework.security.cert;

public enum CertificateType {

	X509("X.509");

	private String	type;

	private CertificateType(final String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public static CertificateType getCertificateType(final String type) {
		for (CertificateType certificateType : CertificateType.values()) {
			if (certificateType.getType().equalsIgnoreCase(type)) {
				return certificateType;
			}
		}
		return null;
	}

}
