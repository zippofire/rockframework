package br.net.woodstock.rockframework.security.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import br.net.woodstock.rockframework.security.crypt.Crypter;
import br.net.woodstock.rockframework.security.crypt.KeyPairType;
import br.net.woodstock.rockframework.security.crypt.KeyType;
import br.net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import br.net.woodstock.rockframework.security.crypt.impl.AsyncCrypterReader;
import br.net.woodstock.rockframework.security.crypt.impl.AsyncCrypterWriter;
import br.net.woodstock.rockframework.security.crypt.impl.Base64Crypter;
import br.net.woodstock.rockframework.security.crypt.impl.SyncCrypter;

import junit.framework.TestCase;

public class CryptTest extends TestCase {

	public void xtest1() throws Exception {
		System.out.println("Test 1");
		Crypter crypter = new SyncCrypter(KeyType.AES, "Teste");
		byte[] encrypt = crypter.encrypt("Lourival".getBytes());
		byte[] decript = crypter.decrypt(encrypt);
		System.out.println(new String(encrypt));
		System.out.println(new String(decript));
	}

	public void xtest1x1() throws Exception {
		System.out.println("Test 1.1");
		Crypter crypter = new Base64Crypter(new SyncCrypter(KeyType.AES, "Teste"));
		byte[] encrypt = crypter.encrypt("Lourival".getBytes());
		byte[] decript = crypter.decrypt(encrypt);
		System.out.println(new String(encrypt));
		System.out.println(new String(decript));
	}

	public void xtest2() throws Exception {
		System.out.println("Test 2");
		Crypter crypter = new AsyncCrypter(KeyPairType.RSA);
		byte[] encrypt = crypter.encrypt("Lourival".getBytes());
		byte[] decript = crypter.decrypt(encrypt);
		System.out.println(new String(encrypt));
		System.out.println(new String(decript));
	}

	public void xtest2x1() throws Exception {
		System.out.println("Test 2.1");
		Crypter crypter = new Base64Crypter(new AsyncCrypter(KeyPairType.RSA));
		byte[] encrypt = crypter.encrypt("Lourival".getBytes());
		byte[] decript = crypter.decrypt(encrypt);
		System.out.println(new String(encrypt));
		System.out.println(new String(decript));
	}

	public void xtest6() throws Exception {
		System.out.println("Gravando");
		AsyncCrypter crypter = new AsyncCrypter(KeyPairType.RSA);
		Base64Crypter crypter64 = new Base64Crypter(crypter);
		System.out.println(new String(crypter64.encrypt("Teste".getBytes())));
		OutputStream osPrivate = new FileOutputStream("C:/private.key");
		OutputStream osPublic = new FileOutputStream("C:/public.key");
		AsyncCrypterWriter writer = new AsyncCrypterWriter(crypter, osPrivate, osPublic);
		writer.write();
		osPrivate.close();
		osPublic.close();
	}

	public void test7() throws Exception {
		System.out.println("Lendo");
		InputStream isPrivate = new FileInputStream("C:/private.key");
		InputStream isPublic = new FileInputStream("C:/public.key");
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
