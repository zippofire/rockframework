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
package br.net.woodstock.rockframework.security.timestamp.impl;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampResponseGenerator;
import org.bouncycastle.tsp.TimeStampTokenGenerator;

import br.net.woodstock.rockframework.security.Alias;
import br.net.woodstock.rockframework.security.sign.SignatureType;
import br.net.woodstock.rockframework.security.store.PrivateKeyEntry;
import br.net.woodstock.rockframework.security.store.Store;
import br.net.woodstock.rockframework.security.store.StoreEntryType;
import br.net.woodstock.rockframework.security.timestamp.TimeStampException;
import br.net.woodstock.rockframework.security.timestamp.TimeStampServer;
import br.net.woodstock.rockframework.security.util.BouncyCastleProviderHelper;
import br.net.woodstock.rockframework.text.impl.RandomGenerator;
import br.net.woodstock.rockframework.text.impl.RandomGenerator.RandomPattern;

public class BouncyCastleTimeStampServer implements TimeStampServer {

	private static final String		TSA_POLICY_ID	= "1.2";

	private TimeStampTokenGenerator	timeStampTokenGenerator;

	private RandomGenerator			randomGenerator	= new RandomGenerator(32, RandomPattern.DIGITS);

	public BouncyCastleTimeStampServer(final Store store, final Alias alias) {
		super();
		try {
			PrivateKeyEntry privateKeyEntry = (PrivateKeyEntry) store.get(alias, StoreEntryType.PRIVATE_KEY);
			PrivateKey privateKey = privateKeyEntry.getValue();
			Certificate[] chain = privateKeyEntry.getChain();
			Certificate certificate = chain[0];

			JcaContentSignerBuilder contentSignerBuilder = new JcaContentSignerBuilder(SignatureType.SHA1_RSA.getAlgorithm());
			contentSignerBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);

			ContentSigner contentSigner = contentSignerBuilder.build(privateKey);

			JcaDigestCalculatorProviderBuilder digestCalculatorProviderBuilder = new JcaDigestCalculatorProviderBuilder();
			digestCalculatorProviderBuilder.setProvider(BouncyCastleProviderHelper.PROVIDER_NAME);
			DigestCalculatorProvider digestCalculatorProvider = digestCalculatorProviderBuilder.build();

			JcaSignerInfoGeneratorBuilder signerInfoGeneratorBuilder = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider);

			SignerInfoGenerator signerInfoGenerator = signerInfoGeneratorBuilder.build(contentSigner, (X509Certificate) certificate);

			this.timeStampTokenGenerator = new TimeStampTokenGenerator(signerInfoGenerator, new ASN1ObjectIdentifier(BouncyCastleTimeStampServer.TSA_POLICY_ID));

			JcaCertStore certStore = new JcaCertStore(Arrays.asList(chain));
			this.timeStampTokenGenerator.addCertificates(certStore);
		} catch (Exception e) {
			throw new TimeStampException(e);
		}
	}

	@Override
	public byte[] getTimeStamp(final byte[] request) {
		try {
			TimeStampRequest timeStampRequest = new TimeStampRequest(request);
			TimeStampResponseGenerator timeStampResponseGenerator = new TimeStampResponseGenerator(this.timeStampTokenGenerator, TSPAlgorithms.ALLOWED);
			TimeStampResponse timeStampResponse = timeStampResponseGenerator.generate(timeStampRequest, this.getSerialNumber(), new Date());

			timeStampResponse.validate(timeStampRequest);

			return timeStampResponse.getEncoded();
		} catch (Exception e) {
			throw new TimeStampException(e);
		}
	}

	private BigInteger getSerialNumber() {
		StringBuilder builder = new StringBuilder();
		builder.append(System.currentTimeMillis());
		builder.append(this.randomGenerator.generate());
		return new BigInteger(builder.toString());
	}

}
