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
package br.net.woodstock.rockframework.security.cert.impl;

import java.security.GeneralSecurityException;
import java.security.Security;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathBuilderException;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.PKIXCertPathBuilderResult;
import java.security.cert.PKIXCertPathValidatorResult;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.security.cert.CertificateException;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.util.Assert;

public class PKIXCertificateVerifier implements CertificateVerifier {

	private static final String	CERTSTORE_TYPE			= "Collection";

	private static final String	CERTPATH_TYPE			= "PKIX";

	private static final String	OSCP_ENABLE_PROPERTY	= "ocsp.enable";

	private static final String	OSCP_ENABLE_VALUE		= "true";

	private static final String	OSCP_URL_PROPERTY		= "ocsp.responderURL";

	private static final String	OSCP_SUBJECT_PROPERTY	= "ocsp.responderCertSubjectName";

	private OCSP				ocsp;

	private boolean				debug;

	public PKIXCertificateVerifier() {
		super();
	}

	public PKIXCertificateVerifier(final OCSP ocsp) {
		super();
		Assert.notNull(ocsp, "ocsp");
		this.ocsp = ocsp;
	}

	public void setDebug(final boolean debug) {
		this.debug = debug;
	}

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		if (chain.length < 2) {
			CoreLog.getInstance().getLog().info("Certificate chain must be greater than 1(certificate and issuer certificate)");
			return false;
		}
		try {
			PKIXCertPathValidatorResult validatorResult = this.getValidatorResult(chain);
			if (this.debug) {
				CoreLog.getInstance().getLog().info("Result: " + validatorResult);
			}
			return true;
		} catch (CertPathBuilderException e) {
			CoreLog.getInstance().getLog().info("Validation error: " + e.getMessage());
			return false;
		} catch (Exception e) {
			throw new CertificateException(e);
		}
	}

	protected PKIXCertPathValidatorResult getValidatorResult(final Certificate[] chain) throws GeneralSecurityException {
		X509Certificate certificate = (X509Certificate) chain[0];
		X509CertSelector selector = new X509CertSelector();
		selector.setCertificate(certificate);

		Set<TrustAnchor> trustAnchors = new HashSet<TrustAnchor>();
		if (chain.length > 1) {
			for (int i = 1; i < chain.length; i++) {
				trustAnchors.add(new TrustAnchor((X509Certificate) chain[i], null));
			}
		}

		PKIXBuilderParameters pkixParameters = new PKIXBuilderParameters(trustAnchors, selector);
		pkixParameters.setRevocationEnabled(false);

		if (chain.length > 1) {
			List<Certificate> list = new ArrayList<Certificate>();
			for (int i = 1; i < chain.length; i++) {
				list.add(chain[i]);
			}
			CertStore intermediateCertStore = CertStore.getInstance(PKIXCertificateVerifier.CERTSTORE_TYPE, new CollectionCertStoreParameters(list));
			pkixParameters.addCertStore(intermediateCertStore);
		}

		if (this.ocsp != null) {
			Security.setProperty(PKIXCertificateVerifier.OSCP_ENABLE_PROPERTY, PKIXCertificateVerifier.OSCP_ENABLE_VALUE);
			Security.setProperty(PKIXCertificateVerifier.OSCP_URL_PROPERTY, this.ocsp.getUrl());
			Security.setProperty(PKIXCertificateVerifier.OSCP_SUBJECT_PROPERTY, ((X509Certificate) this.ocsp.getCertificate()).getSubjectX500Principal().getName());
		}

		CertPathBuilder builder = CertPathBuilder.getInstance(PKIXCertificateVerifier.CERTPATH_TYPE);
		PKIXCertPathBuilderResult builderResult = (PKIXCertPathBuilderResult) builder.build(pkixParameters);
		CertPathValidator validator = CertPathValidator.getInstance(PKIXCertificateVerifier.CERTPATH_TYPE);
		PKIXCertPathValidatorResult validatorResult = (PKIXCertPathValidatorResult) validator.validate(builderResult.getCertPath(), pkixParameters);
		return validatorResult;
	}

}
