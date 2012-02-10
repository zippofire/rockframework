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
package br.net.woodstock.rockframework.office.pdf.impl;

import java.util.Vector;

import br.net.woodstock.rockframework.security.digest.DigestType;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.utils.ConditionUtils;

abstract class AbstractITextManager extends AbstractManager {

	// private static final String CN_FIELD = "CN";

	protected static final char	PDF_SIGNATURE_VERSION	= '\0';

	protected SignatureType getSignatureType(final String signatureAlgorithm) {
		SignatureType signType = SignatureType.getSignType(signatureAlgorithm);
		if (signType == null) {
			signType = SignatureType.SHA1_RSA;
		}
		return signType;
	}

	protected DigestType getDigestTypeFromSignature(final String signatureAlgorithm) {
		SignatureType signType = this.getSignatureType(signatureAlgorithm);
		DigestType digestType = signType.getDigestType();
		return digestType;
	}

	@SuppressWarnings("rawtypes")
	protected String toString(final Vector vector) {
		StringBuilder builder = new StringBuilder();
		if (ConditionUtils.isNotEmpty(vector)) {
			for (Object o : vector) {
				builder.append(o.toString());
			}
		}
		return builder.toString();
	}

}
