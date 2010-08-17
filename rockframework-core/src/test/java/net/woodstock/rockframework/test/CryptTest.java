package net.woodstock.rockframework.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import junit.framework.TestCase;
import net.woodstock.rockframework.security.crypt.Crypter;
import net.woodstock.rockframework.security.crypt.KeyPairType;
import net.woodstock.rockframework.security.crypt.KeyType;
import net.woodstock.rockframework.security.crypt.impl.AsyncCrypter;
import net.woodstock.rockframework.security.crypt.impl.Base64Crypter;
import net.woodstock.rockframework.security.crypt.impl.SyncCrypter;

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
		System.out.println("Test 1");
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

	public void xtest2x2() throws Exception {
		System.out.println("Test 2");
		Crypter crypter = new Base64Crypter(new AsyncCrypter(KeyPairType.RSA));
		byte[] encrypt = crypter.encrypt("Lourival".getBytes());
		byte[] decript = crypter.decrypt(encrypt);
		System.out.println(new String(encrypt));
		System.out.println(new String(decript));
	}

	public void xtest6() throws Exception {
		AsyncCrypter crypter = new AsyncCrypter(KeyPairType.RSA);
		Base64Crypter crypter64 = new Base64Crypter(crypter);
		System.out.println(new String(crypter64.encrypt("Teste".getBytes())));
		OutputStream os = new FileOutputStream("C:/Temp/xxx2.key");
		AsyncCrypter.save(crypter, os);
		os.close();
	}

	public void test7() throws Exception {
		InputStream is = new FileInputStream("C:/Temp/xxx2.key");
		AsyncCrypter crypter = AsyncCrypter.load(is, KeyPairType.RSA);
		Base64Crypter crypter64 = new Base64Crypter(crypter);
		String str = "Teste";
		System.out.println("Original: " + str);
		byte[] bytes = crypter64.encrypt(str.getBytes());
		System.out.println("Encriptado: " + new String(bytes));
		bytes = crypter64.decrypt(bytes);
		System.out.println("Decriptado: " + new String(bytes));
		is.close();
	}

}
