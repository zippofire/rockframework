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
package br.net.woodstock.rockframework.security.util;

import java.io.IOException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import br.net.woodstock.rockframework.config.CoreLog;

public abstract class BouncyCastleProviderHelper {

	public static final String	PROVIDER_NAME;

	static {
		PROVIDER_NAME = "BC";
		Provider provider = Security.getProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
		if (provider == null) {
			CoreLog.getInstance().getLog().info("Adding BouncyCastle Security Provider");
			Security.addProvider(new BouncyCastleProvider());
		}
	}

	public static X509Certificate getCertificate(final X509CertificateHolder holder) throws CertificateException {
		JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
		converter.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
		return converter.getCertificate(holder);
	}

	public static String getName(final X500Principal principal) {
		X500Name x500Name = new X500Name(principal.getName());
		return BouncyCastleProviderHelper.getName(x500Name);
	}

	public static String getName(final X509Principal principal) {
		X500Name x500Name = new X500Name(principal.getName());
		return BouncyCastleProviderHelper.getName(x500Name);
	}

	public static String getName(final X500Name name) {
		RDN[] rdns = name.getRDNs(BCStyle.CN);
		String s = null;
		if ((rdns != null) && (rdns.length > 0)) {
			s = IETFUtils.valueToString(rdns[0].getFirst().getValue());
		} else {
			s = name.toString();
		}
		return s;
	}

	public static X500Name toX500Name(final String value) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, value);
		return builder.build();
	}

	public static X500Principal toX500Principal(final String value) throws IOException {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, value);
		X500Name name = builder.build();
		return BouncyCastleProviderHelper.toX500Principal(name);
	}

	public static X500Principal toX500Principal(final X500Name name) throws IOException {
		X500Principal principal = new X500Principal(name.getEncoded());
		return principal;
	}

}
