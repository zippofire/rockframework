package br.net.woodstock.rockframework.test.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import br.net.woodstock.rockframework.security.crypt.impl.AsyncCrypterReader;
import br.net.woodstock.rockframework.security.crypt.impl.AsyncCrypterWriter;
import br.net.woodstock.rockframework.security.crypt.impl.Base64Crypter;

public class CrypterTest extends TestCase {

	public void test1() throws Exception {
		System.out.println("Gravando");
		AsyncCrypter crypter = new AsyncCrypter(KeyPairType.RSA);
		Base64Crypter crypter64 = new Base64Crypter(crypter);
		System.out.println(new String(crypter64.encrypt("Teste".getBytes())));
		OutputStream osPrivate = new FileOutputStream("/tmp/private.key");
		OutputStream osPublic = new FileOutputStream("/tmp/public.key");
		AsyncCrypterWriter writer = new AsyncCrypterWriter(crypter, osPrivate, osPublic);
		writer.write();
		osPrivate.close();
		osPublic.close();
	}

	public void xtest2() throws Exception {
		System.out.println("Lendo");
		InputStream isPrivate = new FileInputStream("/tmp/private.key");
		InputStream isPublic = new FileInputStream("/tmp/public.key");
		AsyncCrypterReader reader = new AsyncCrypterReader(isPrivate, isPublic, KeyPairType.RSA);
		AsyncCrypter crypter = reader.read();

		Base64Crypter crypter64 = new Base64Crypter(crypter);
		String str = "Teste";
		System.out.println("Original: " + str);
		byte[] bytes = crypter64.encrypt(str.getBytes());
		System.out.println("Encriptado: " + new String(bytes));
		bytes = crypter64.decrypt(bytes);
		System.out.println("Decriptado: " + new String(bytes));
		isPrivate.close();
		isPublic.close();
	}

}
