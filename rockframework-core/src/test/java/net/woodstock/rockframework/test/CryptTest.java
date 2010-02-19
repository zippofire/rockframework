package net.woodstock.rockframework.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import net.woodstock.rockframework.security.crypt.impl.Base64Crypter;
import net.woodstock.rockframework.security.crypt.impl.SyncCrypter;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypter.Algorithm;

public class CryptTest extends TestCase {

	public void xtest1() throws Exception {
		System.out.println("Test 1");
		Crypter crypter = new SyncCrypter(null, null);
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

	public void test11() throws Exception {
		System.out.println("Test 1");
		Crypter crypter = new Base64Crypter(new SyncCrypter(null, null));
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

	public void xtest2() throws Exception {
		System.out.println("Test 2");
		Crypter crypter = new AsyncCrypter((Algorithm) null, null);
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

	public void test22() throws Exception {
		System.out.println("Test 2");
		Crypter crypter = new Base64Crypter(new AsyncCrypter((Algorithm) null, null));
		String encrypt = crypter.encrypt("Lourival");
		String decript = crypter.decrypt(encrypt);
		System.out.println(encrypt);
		System.out.println(decript);
	}

	public void xtest4() throws Exception {
		SyncCrypter crypter = new SyncCrypter(null, null);
		OutputStream os = new FileOutputStream("C:/xxx.key");
		crypter.saveKey(os);
		os.close();
	}

	public void xtest5() throws Exception {
		InputStream is = new FileInputStream("C:/xxx.key");
		SyncCrypter crypter = new SyncCrypter(is);
		is.close();

		System.out.println(crypter.encrypt("Teste"));
	}

}
