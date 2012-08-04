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

import java.security.cert.Certificate;

import br.net.woodstock.rockframework.config.CoreLog;
import br.net.woodstock.rockframework.security.cert.CertificateVerifier;
import br.net.woodstock.rockframework.util.Assert;

public class CertificateVerifierChain implements CertificateVerifier {

	private CertificateVerifier[]	chain;

	private boolean					debug;

	public CertificateVerifierChain(final CertificateVerifier[] chain) {
		super();
		Assert.notEmpty(chain, "chain");
		this.chain = chain;
		this.debug = true;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public boolean verify(final Certificate[] chain) {
		Assert.notEmpty(chain, "chain");
		boolean result = true;
		for (CertificateVerifier verifier : this.chain) {
			CoreLog.getInstance().getLog().info("Verify using " + verifier.getClass().getCanonicalName());
			boolean status = verifier.verify(chain);
			CoreLog.getInstance().getLog().info("Status of " + verifier.getClass().getCanonicalName() + " " + (status ? "ok" : "fail"));
			if (!status) {
				result = false;
				if (!this.debug) {
					break;
				}
			}
		}
		return result;
	}

}
