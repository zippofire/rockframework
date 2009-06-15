package net.woodstock.rockframework.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import net.woodstock.rockframework.security.crypt.impl.SyncAlgorithm;
import net.woodstock.rockframework.security.crypt.impl.SyncCrypter;
import net.woodstock.rockframework.utils.Base64Utils;

public class CryptTest extends TestCase {

	public void xtest1() throws Exception {
		System.out.println("Test 1");
		Crypter crypter = SyncCrypter.newInstance(null, null, null);
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

	public void xtest2() throws Exception {
		System.out.println("Test 2");
		Crypter crypter = AsyncCrypter.newInstance(null, null, null);
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

	public void xtest3() throws Exception {
		System.out.println(SyncAlgorithm.fromString("HmacSHA1"));
	}

	public void xtest4() throws Exception {
		SyncCrypter crypter = SyncCrypter.newInstance(null, null, null);
		OutputStream os = new FileOutputStream("D:/xxx.key");
		crypter.saveKey(os);
		os.close();
	}

	public void xtest5() throws Exception {
		InputStream is = new FileInputStream("D:/xxx.key");
		SyncCrypter crypter = SyncCrypter.getInstance(is);
		is.close();

		System.out.println(crypter.encrypt("Teste"));
	}

	public void test6() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		SecureRandom random = new SecureRandom("teste".getBytes());

		generator.initialize(1024, random);

		KeyPair keyPair = generator.generateKeyPair();

		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();

		System.out.println(Base64Utils.toBase64(privateKey.getEncoded()));
		System.out.println(Base64Utils.toBase64(publicKey.getEncoded()));

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		System.out.println(Base64Utils.toBase64(cipher.doFinal("Lourival".getBytes())));
	}

}
